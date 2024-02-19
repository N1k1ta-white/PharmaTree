package bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.faststorage;

import bg.sofia.uni.fmi.mjt.pharmatree.api.items.Copyable;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.Identifiable;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.Nameable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class FastStorage<E extends Copyable<E> & Identifiable & Nameable> {

    private final ConcurrentHashMap<String, List<E>> storageByName;

    private final ConcurrentHashMap<Integer, E> storageById;

    public FastStorage() {
        storageById = new ConcurrentHashMap<>();
        storageByName = new ConcurrentHashMap<>();
    }

    public void add(E elem) {
        storageById.put(elem.id(), elem);
        storageByName.putIfAbsent(elem.name(), new ArrayList<>());
        storageByName.get(elem.name()).add(elem);
    }

    public Optional<E> getById(int id) {
        if (storageById.containsKey(id)) {
            return Optional.of(storageById.get(id));
        }
        return Optional.empty();
    }

    public List<E> getByName(String name) {
        if (storageByName.containsKey(name)) {
            return storageByName.get(name);
        }
        return List.of();
    }

    public boolean remove(E elem) {
        if (storageById.containsKey(elem.id())) {
            storageById.remove(elem.id());
            storageByName.remove(elem.name());
            return true;
        }
        return false;
    }

    public boolean contains(E elem) {
        return storageById.containsKey(elem.id());
    }

    public Stream<E> getStream() {
        return storageById.values().stream();
    }

    public Collection<E> getCollection() {
        return storageById.values();
    }

    public void setNewName(E elem, String name) {
        storageByName.get(elem.name()).remove(elem);
        storageByName.putIfAbsent(name, new ArrayList<>());
        storageByName.get(name).add(elem);
    }

    public Collection<E> getByNamesCollection(List<String> names) {
        Collection<E> res = new ArrayList<>();
        for (String name : names) {
            if (storageByName.containsKey(name)) {
                res.addAll(storageByName.get(name));
            }
        }
        return res;
    }

    public Collection<E> getByIdCollection(List<String> names) {
        Collection<E> res = new ArrayList<>();
        for (String idStr : names) {
            int id = Integer.parseInt(idStr);
            if (storageById.containsKey(id)) {
                res.add(storageById.get(id));
            }
        }
        return res;
    }
}
