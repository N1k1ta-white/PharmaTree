package bg.sofia.uni.fmi.mjt.pharmatree.api.storage;

import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.Copyable;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.parser.ItemConverter;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.editor.Editor;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.filter.Filter;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

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

//    private static final int PAIR = 2;

    private void getAllDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                storage.add(itemConverter.parseLine(line));
            }
        } catch (IOException e) {
            throw new UncheckedIOException("The file of DB hasn't been found or something else", e);
        }
    }

//    private JsonArray parseJsonArray(String json) {
//        JsonElement elem = JsonParser.parseString(json);
//        if (!elem.isJsonArray()) {
//            // TODO: exception
//        }
//        return elem.getAsJsonArray();
//    }

    protected BaseStorage(CopyOnWriteArrayList<E> storage, Filter<E> filter, Editor<E> editor, ItemConverter<E> parser,
                          Path path) {
        this.storage = storage;
        this.filter = filter;
        this.editor = editor;
        this.path = path;
        this.itemConverter = parser;
        getAllDataFromFile();
    }

    @Override
    public void edit(int id, Map<String, List<String>> edit) {
        Optional<E> result = filter.getElementById(storage.stream(), id);
        if (result.isEmpty()) {
            // TODO exception
        }
        editor.editElement(result.get(), edit);
    }

    public void replace(int id, String json) {
        Optional<E> objForReplace = filter.getElementById(storage.stream(), id);
        if (objForReplace.isEmpty()) {
            // TODO: exception
        }
        E newObj = itemConverter.parseJson(json);
        objForReplace.get().copy(newObj);
    }

    @Override
    public void replaceOrAdd(int id, String json) {
        Optional<E> objForReplace = filter.getElementById(storage.stream(), id);
        if (objForReplace.isEmpty()) {
            add(json);
        } else {
            E newObj = itemConverter.parseJson(json);
            objForReplace.get().copy(newObj);
        }
    }

    @Override
    public String get(Map<String, List<String>> params) {
        return itemConverter.convertListToJson(filter.filterStreamByParams(storage.stream(), params).toList());
    }

    @Override
    public void delete(int id) {
        Optional<E> obj = filter.getElementById(storage.stream(), id);
        if (obj.isEmpty()) {
            // TODO: exception
        }
        synchronized (BaseStorage.class) {
            if (!storage.remove(obj)) {
                // TODO: exception
            }
        }
    }

    @Override
    public void add(String json) {
        E obj = itemConverter.parseJson(json);
        synchronized (BaseStorage.class) {
            if (!storage.contains(obj)) {
                storage.add(obj);
            }
        }
        // TODO: exception
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
