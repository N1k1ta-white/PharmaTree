package bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.editor;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;

import java.util.List;
import java.util.Map;

public interface Editor<E> {
    void editElement(E element, Map<String, List<String>> params) throws ClientException;

}
