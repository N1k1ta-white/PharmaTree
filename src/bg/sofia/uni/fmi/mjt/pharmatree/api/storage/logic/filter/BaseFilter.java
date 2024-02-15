package bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.filter;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.Identifiable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class BaseFilter<E extends Identifiable> implements Filter<E> {
    protected abstract Stream<E> filterStreamByParam(Stream<E> stream, Map.Entry<String, List<String>> param)
            throws ClientException;

    public Stream<E> filterStreamByParams(Stream<E> stream, Map<String, List<String>> params)
            throws ClientException {
        for (Map.Entry<String, List<String>> entry : params.entrySet()) {
            stream = filterStreamByParam(stream, entry);
        }
        return stream;
    }

    @Override
    public Optional<E> getElementById(Stream<E> stream, int id) {
        return stream.parallel().filter(el -> el.id() == id).findAny();
    }
}
