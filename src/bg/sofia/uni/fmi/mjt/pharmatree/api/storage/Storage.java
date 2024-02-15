package bg.sofia.uni.fmi.mjt.pharmatree.api.storage;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ServerException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;

import java.util.List;
import java.util.Map;

public sealed interface Storage permits BaseStorage {
    int getSecurityLevelEdit();

    int getSecurityLevelRead();

    void edit(int id, Map<String, List<String>> edit) throws ClientException, ServerException;

    StatusCode replaceOrAdd(int id, String json) throws ClientException;

    void replace(int id, String json) throws ClientException;

    String get(Map<String, List<String>> params) throws ClientException;

    void delete(int id) throws ClientException;

    void add(String element) throws ClientException;

    void flush();
}
