package bg.sofia.uni.fmi.mjt.pharmatree.api.storage;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ServerException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.Copyable;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.Nameable;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.converter.ItemConverter;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.editor.Editor;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.faststorage.FastStorage;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.filter.Filter;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.Identifiable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public abstract sealed class BaseStorage<E extends Copyable<E> & Identifiable & Nameable> implements Storage
        permits DrugStorage, PropertyStorage, UserStorage {
    protected FastStorage<E> storage;

    protected final Filter<E> filter;
    private final Editor<E> editor;
    private final Path path;
    private final ItemConverter<E> itemConverter;
    private final AtomicInteger id;
    protected final ConcurrentHashMap<String, String> cache;

    private void reading(File file) throws ServerException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            reader.readLine();
            int currId;
            while ((line = reader.readLine()) != null) {
                try {
                    E item = itemConverter.parseLine(line);
                    storage.add(item);
                    currId = item.id();
                } catch (ClientException e) {
                    throw new ServerException(StatusCode.INTERNAL_SERVER_ERROR,
                            "Error in server during of starting of Storage");
                }
                if (id.get() < currId) {
                    id.set(currId);
                }
            }
        } catch (IOException e) {
            throw new ServerException(StatusCode.SERVICE_UNAVAILABLE,
                    "Unexpected error during of starting of storage", e);
        }
        id.incrementAndGet();
    }

    private void getAllDataFromFile() throws ServerException {
        File file = path.toFile();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new ServerException(StatusCode.SERVICE_UNAVAILABLE, "Error during of creating of storage", e);
            }
        }
        reading(file);
    }

    private String paramsToString(Map<String, List<String>> params) {
        return params.entrySet().stream()
                .map(entry -> entry.getKey() + ":" + String.join(",", entry.getValue()))
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.joining(System.lineSeparator()));
    }

    protected BaseStorage(FastStorage<E> storage, Filter<E> filter, Editor<E> editor, ItemConverter<E> parser,
                          Path path) throws ServerException {
        this.storage = storage;
        this.filter = filter;
        this.editor = editor;
        this.path = path;
        this.itemConverter = parser;
        id = new AtomicInteger(0);
        cache = new ConcurrentHashMap<>();
        getAllDataFromFile();
    }

    protected abstract String getFirstLine();

    @Override
    public void edit(int id, Map<String, List<String>> edit) throws ClientException, ServerException {
        Optional<E> result = storage.getById(id);
        if (result.isEmpty()) {
            throw new ClientException(StatusCode.NOT_FOUND, "Object for editing hasn't found");
        }
        if (edit.containsKey(Nameable.NAME_OF_ATTRIBUTE)) {
            if (edit.get(Nameable.NAME_OF_ATTRIBUTE).size() != 1) {
                throw new ClientException(StatusCode.BAD_REQUEST, "So many new names!");
            }
            storage.setNewName(result.get(), edit.get(Nameable.NAME_OF_ATTRIBUTE).getFirst());
        }
        synchronized (result.get()) {
            editor.editElement(result.get(), edit);
        }
        cache.clear();
    }

    public void replace(int id, String json) throws ClientException, ServerException {
        Optional<E> objForReplace = storage.getById(id);
        if (objForReplace.isEmpty()) {
            throw new ClientException(StatusCode.NOT_FOUND, "Object for replacement hasn't been found");
        }
        E newObj = itemConverter.parseJson(json);
        storage.setNewName(objForReplace.get(), newObj.name());
        synchronized (objForReplace.get()) {
            objForReplace.get().copy(newObj);
        }
        cache.clear();
    }

    @Override
    public StatusCode replaceOrAdd(int id, String json) throws ClientException, ServerException {
        Optional<E> objForReplace = storage.getById(id);
        cache.clear();
        if (objForReplace.isEmpty()) {
            add(json);
            return StatusCode.CREATED;
        }
        E newObj = itemConverter.parseJson(json);
        storage.setNewName(objForReplace.get(), newObj.name());
        synchronized (objForReplace.get()) {
            objForReplace.get().copy(newObj);
        }
        return StatusCode.OK;
    }

    @Override
    public String get(Map<String, List<String>> params) throws ClientException {
        String request = paramsToString(params);
        if (!cache.containsKey(request)) {
            List<E> list = filter.filterStreamByParams(storage, params).toList();
            if (list.isEmpty()) {
                throw new ClientException(StatusCode.NOT_FOUND, "Item no found");
            }
            String res = itemConverter.convertListToJson(list);
            cache.put(request, res);
            return res;
        }
        return cache.get(request);
    }

    @Override
    public void delete(int id) throws ClientException, ServerException {
        Optional<E> obj = storage.getById(id);
        if (obj.isEmpty()) {
            throw new ClientException(StatusCode.NOT_FOUND, "Object for deleting hasn't been found");
        }
        cache.clear();
        synchronized (BaseStorage.class) {
            if (!storage.remove(obj.get())) {
                throw new ClientException(StatusCode.NOT_FOUND, "Object almost has been deleted");
            }
        }
    }

    @Override
    public void add(String json) throws ClientException, ServerException {
        E obj = itemConverter.parseJson(json);
        synchronized (BaseStorage.class) {
            if (!storage.contains(obj)) {
                cache.clear();
                obj.setId(id.getAndIncrement());
                storage.add(obj);
            } else {
                throw new ClientException(StatusCode.CONFLICT, "Object already exist in storage");
            }
        }
    }

    @Override
    public synchronized void flush() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), false))) {
            writer.write(getFirstLine());
            writer.newLine();
            for (E elem : storage.getCollection()) {
                writer.write(elem.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new UncheckedIOException("The file of drugs has been damaged or not found", e);
        }
    }
}
