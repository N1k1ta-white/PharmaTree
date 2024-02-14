package bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.filter;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public interface Filter<E> {
    Stream<E> filterStreamByParams(Stream<E> stream, Map<String, List<String>> params);

    Optional<E> getElement(Stream<E> stream, E element);
}
