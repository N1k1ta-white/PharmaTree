package bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.filter;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.Copyable;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.Identifiable;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.Nameable;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.faststorage.FastStorage;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public interface Filter<E extends Copyable<E> & Identifiable & Nameable> {
    Stream<E> filterStreamByParams(FastStorage<E> storage, Map<String, List<String>> params) throws ClientException;
}
