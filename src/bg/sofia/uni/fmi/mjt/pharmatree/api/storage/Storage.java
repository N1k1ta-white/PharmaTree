package bg.sofia.uni.fmi.mjt.pharmatree.api.storage;

import java.util.List;
import java.util.Map;

public sealed interface Storage<E> permits BaseStorage {
    void edit(E element, Map<String, List<String>> edit);

    void replace(E oldElem, E newElem);

    boolean isExist(E element);

    List<E> get(Map<String, List<String>> params);

    void delete(E element);

    void add(E element);

    void flush();
}
