package bg.sofia.uni.fmi.mjt.pharmatree.api.storage;

import java.util.List;
import java.util.Map;

public sealed interface Storage<E> permits BaseStorage {
    void edit(int id, Map<String, List<String>> edit);

    void replaceOrAdd(int id, String json);

    void replace(int id, String json);

    String get(Map<String, List<String>> params);

    void delete(int id);

    void add(String element);

    void flush();
}
