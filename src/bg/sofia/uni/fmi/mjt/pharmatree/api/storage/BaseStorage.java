package bg.sofia.uni.fmi.mjt.pharmatree.api.storage;

import bg.sofia.uni.fmi.mjt.pharmatree.api.items.Item;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.Copyable;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.parser.ItemParser;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.editor.Editor;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.filter.Filter;

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

public abstract sealed class BaseStorage<E extends Copyable<E>> implements Storage<E> permits DrugStorage, PropertyStorage, UserStorage {
    private final CopyOnWriteArrayList<E> storage;
    private final Filter<E> filter;
    private final Editor<E> editor;
    private final Path path;
    private final ItemParser<E> itemParser;

    private void getAllDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                storage.add(itemParser.of(line));
            }
        } catch (IOException e) {
            throw new UncheckedIOException("The file of DB hasn't been found or something else", e);
        }
    }

    protected BaseStorage(CopyOnWriteArrayList<E> storage, Filter<E> filter, Editor<E> editor, ItemParser<E> parser,
                          Path path) {
        this.storage = storage;
        this.filter = filter;
        this.editor = editor;
        this.path = path;
        this.itemParser = parser;
        getAllDataFromFile();
    }

    @Override
    public void edit(E element, Map<String, List<String>> edit) {
        Optional<E> result = filter.getElement(storage.stream(), element);
        if (result.isEmpty()) {
            // TODO exception
        }
        editor.editElement(result.get(), edit);
    }

    public void replace(E oldElem, E newElem) {
        Optional<E> elemInStorage = filter.getElement(storage.stream(), oldElem);
        if (elemInStorage.isEmpty()) {
            // TODO exception
        }
        elemInStorage.get().copy(newElem);
    }

    @Override
    public boolean isExist(E element) {
        return filter.getElement(storage.stream(), element).isPresent();
    }

    @Override
    public List<E> get(Map<String, List<String>> params) {
        return filter.filterStreamByParams(storage.stream(), params).toList();
    }

    @Override
    public synchronized void delete(E element) {
        if (!storage.remove(element)) {
            // TODO: exception
        }
    }

    @Override
    public synchronized void add(E element) {
        if (!isExist(element)) {
            storage.add(element);
        }
        // TODO: exception
    }

    @Override
    public void flush() {
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
