package bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.filter;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public interface Filter<E> {
    Stream<E> filterStreamByParams(Stream<E> stream, Map<String, List<String>> params) throws ClientException;

    Optional<E> getElementById(Stream<E> stream, int id);
}
