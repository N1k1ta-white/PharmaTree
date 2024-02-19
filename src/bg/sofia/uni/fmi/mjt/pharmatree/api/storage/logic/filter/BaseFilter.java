package bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.filter;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.Copyable;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.Identifiable;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.Nameable;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.faststorage.FastStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public abstract class BaseFilter<E extends Identifiable & Copyable<E> & Nameable> implements Filter<E> {
    protected abstract Stream<E> filterStreamByParam(Stream<E> stream, Map.Entry<String, List<String>> param)
            throws ClientException;

    public Stream<E> filterStreamByParams(FastStorage<E> storage, Map<String, List<String>> params)
            throws ClientException {
        Collection<E> res = new ArrayList<>();
        boolean used = false;
        if (params.containsKey(Identifiable.NAME_OF_ATTRIBUTE)) {
            used = true;
            res.addAll(storage.getByIdCollection(params.get(Identifiable.NAME_OF_ATTRIBUTE)));
        }
        if (params.containsKey(Nameable.NAME_OF_ATTRIBUTE)) {
            used = true;
            res.addAll(storage.getByNamesCollection(params.get(Nameable.NAME_OF_ATTRIBUTE)));
        }
        Stream<E> stream = (used ? res.stream() : storage.getStream());
        for (Map.Entry<String, List<String>> entry : params.entrySet()) {
            stream = filterStreamByParam(stream, entry);
        }
        return stream;
    }
}
