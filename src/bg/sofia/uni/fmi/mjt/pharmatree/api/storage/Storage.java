package bg.sofia.uni.fmi.mjt.pharmatree.api.storage;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;

import java.util.List;
import java.util.Map;

public sealed interface Storage<E> permits BaseStorage {
    void edit(int id, Map<String, List<String>> edit) throws ClientException;

    StatusCode replaceOrAdd(int id, String json);

    void replace(int id, String json) throws ClientException;

    String get(Map<String, List<String>> params);

    StatusCode delete(int id) throws ClientException;

    StatusCode add(String element);

    void flush();
}
