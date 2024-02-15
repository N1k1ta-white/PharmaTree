package bg.sofia.uni.fmi.mjt.pharmatree.api.storage;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ServerException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.Copyable;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.converter.ItemConverter;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.editor.Editor;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.filter.Filter;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.Identifiable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public abstract sealed class BaseStorage<E extends Copyable<E> & Identifiable> implements Storage
        permits DrugStorage, PropertyStorage, UserStorage {
    protected final CopyOnWriteArrayList<E> storage;
    private final Filter<E> filter;
    private final Editor<E> editor;
    private final Path path;
    private final ItemConverter<E> itemConverter;
    private final AtomicInteger id;
    private final Map<String, String> cache;

    private void getAllDataFromFile() throws ServerException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    storage.add(itemConverter.parseLine(line));
                } catch (ClientException e) {
                    throw new ServerException(StatusCode.Internal_Server_Error,
                            "Error in server during of starting of Storage");
                }
                if (id.get() < storage.getLast().id()) {
                    id.set(storage.getLast().id());
                }
            }
        } catch (IOException e) {
            throw new ServerException(StatusCode.Service_Unavailable,
                    "Unexpected error during of starting of storage", e);
        }
        id.incrementAndGet();
    }

    private String paramsToString(Map<String, List<String>> params) {
        return params.entrySet().stream()
                .map(entry -> entry.getKey() + ":" + String.join(",", entry.getValue()))
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.joining(System.lineSeparator()));
    }

    protected BaseStorage(CopyOnWriteArrayList<E> storage, Filter<E> filter, Editor<E> editor, ItemConverter<E> parser,
                          Path path) throws ServerException {
        this.storage = storage;
        this.filter = filter;
        this.editor = editor;
        this.path = path;
        this.itemConverter = parser;
        id = new AtomicInteger(0);
        cache = new HashMap<>();
        getAllDataFromFile();
    }

    @Override
    public void edit(int id, Map<String, List<String>> edit) throws ClientException {
        Optional<E> result = filter.getElementById(storage.stream(), id);
        if (result.isEmpty()) {
            throw new ClientException(StatusCode.Not_Found, "Object for editing hasn't found");
        }
        editor.editElement(result.get(), edit);
        cache.clear();
    }

    public void replace(int id, String json) throws ClientException {
        Optional<E> objForReplace = filter.getElementById(storage.stream(), id);
        if (objForReplace.isEmpty()) {
            throw new ClientException(StatusCode.Not_Found, "Object for replacement hasn't found");
        }
        E newObj = itemConverter.parseJson(json);
        objForReplace.get().copy(newObj);
        cache.clear();
    }

    @Override
    public StatusCode replaceOrAdd(int id, String json) throws ClientException {
        Optional<E> objForReplace = filter.getElementById(storage.stream(), id);
        cache.clear();
        if (objForReplace.isEmpty()) {
            add(json);
            return StatusCode.Created;
        }
        E newObj = itemConverter.parseJson(json);
        objForReplace.get().copy(newObj);
        return StatusCode.OK;
    }

    @Override
    public String get(Map<String, List<String>> params) throws ClientException {
        String request = paramsToString(params);
        if (!cache.containsKey(request)) {
            String res = itemConverter.convertListToJson(filter
                    .filterStreamByParams(storage.stream(), params).toList());
            cache.put(request, res);
            return res;
        }
        return cache.get(request);
    }

    @Override
    public void delete(int id) throws ClientException {
        Optional<E> obj = filter.getElementById(storage.stream(), id);
        if (obj.isEmpty()) {
            throw new ClientException(StatusCode.Not_Found, "Object for deleting hasn't found");
        }
        cache.clear();
        synchronized (BaseStorage.class) {
            if (!storage.remove(obj.get())) {
                throw new ClientException(StatusCode.Not_Found, "Object almost has deleted");
            }
        }
    }

    @Override
    public void add(String json) throws ClientException {
        E obj = itemConverter.parseJson(json);
        synchronized (BaseStorage.class) {
            if (!storage.contains(obj)) {
                cache.clear();
                obj.setId(id.getAndIncrement());
                storage.add(obj);
            } else {
                throw new ClientException(StatusCode.Conflict, "Object already exist in storage");
            }
        }
    }

    @Override
    public synchronized void flush() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), false))) {
            for (E elem : storage) {
                writer.write(elem.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new UncheckedIOException("The file of drugs hasn't been found or something else", e);
        }
    }
}
