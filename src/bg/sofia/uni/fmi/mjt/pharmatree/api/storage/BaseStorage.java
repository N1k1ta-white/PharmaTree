package bg.sofia.uni.fmi.mjt.pharmatree.api.storage;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ServerException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.Copyable;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.parser.ItemConverter;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.editor.Editor;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.filter.Filter;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract sealed class BaseStorage<E extends Copyable<E>> implements Storage<E>
        permits DrugStorage, PropertyStorage, UserStorage {
    private final CopyOnWriteArrayList<E> storage;
    private final Filter<E> filter;
    private final Editor<E> editor;
    private final Path path;
    private final ItemConverter<E> itemConverter;

    private void getAllDataFromFile() throws ServerException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                storage.add(itemConverter.parseLine(line));
            }
        } catch (IOException e) {
            throw new ServerException(StatusCode.Service_Unavailable, e);
        }
    }

    protected BaseStorage(CopyOnWriteArrayList<E> storage, Filter<E> filter, Editor<E> editor, ItemConverter<E> parser,
                          Path path) throws ServerException {
        this.storage = storage;
        this.filter = filter;
        this.editor = editor;
        this.path = path;
        this.itemConverter = parser;
        getAllDataFromFile();
    }

    @Override
    public void edit(int id, Map<String, List<String>> edit) throws ClientException {
        Optional<E> result = filter.getElementById(storage.stream(), id);
        if (result.isEmpty()) {
            throw new ClientException(StatusCode.Not_Found);
        }
        editor.editElement(result.get(), edit);
    }

    public void replace(int id, String json) throws ClientException {
        Optional<E> objForReplace = filter.getElementById(storage.stream(), id);
        if (objForReplace.isEmpty()) {
            throw new ClientException(StatusCode.Not_Found);
        }
        E newObj = itemConverter.parseJson(json);
        objForReplace.get().copy(newObj);
    }

    @Override
    public StatusCode replaceOrAdd(int id, String json) {
        Optional<E> objForReplace = filter.getElementById(storage.stream(), id);
        if (objForReplace.isEmpty()) {
            add(json);
            return StatusCode.Created;
        }
        E newObj = itemConverter.parseJson(json);
        objForReplace.get().copy(newObj);
        return StatusCode.OK;
    }

    @Override
    public String get(Map<String, List<String>> params) {
        return itemConverter.convertListToJson(filter.filterStreamByParams(storage.stream(), params).toList());
    }

    @Override
    public StatusCode delete(int id) throws ClientException {
        Optional<E> obj = filter.getElementById(storage.stream(), id);
        if (obj.isEmpty()) {
            throw new ClientException(StatusCode.Not_Found);
        }
        synchronized (BaseStorage.class) {
            if (!storage.remove(obj.get())) {
                return StatusCode.Accepted;
            }
            return StatusCode.OK;
        }
    }

    @Override
    public StatusCode add(String json) {
        E obj = itemConverter.parseJson(json);
        synchronized (BaseStorage.class) {
            if (!storage.contains(obj)) {
                storage.add(obj);
                return StatusCode.Created;
            }
            return StatusCode.OK;
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
