package bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.editor;

import java.util.List;
import java.util.Map;

public interface Editor<E> {
//    boolean isValidDescription(Map<String, List<String>> params);

    E editElement(E element, Map<String, List<String>> params);

//    E createElementByDescription(Map<String, List<String>> params);
}
