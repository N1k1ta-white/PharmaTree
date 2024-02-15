package bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.editor;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ServerException;

import java.util.List;
import java.util.Map;

public interface Editor<E> {
    // boolean isValidDescription(Map<String, List<String>> params);

    void editElement(E element, Map<String, List<String>> params) throws ClientException, ServerException;

}
