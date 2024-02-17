package bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.editor;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;

import java.util.List;
import java.util.Map;

public abstract class BaseEditor<E> implements Editor<E> {

    protected abstract boolean isValidNumberOfValue(String param, int size) throws ClientException;

    protected abstract void edit(E elem, String param, List<String> val) throws ClientException;

    private boolean isValidNumberOfValues(Map<String, List<String>> params) throws ClientException {
        for (Map.Entry<String, List<String>> param : params.entrySet()) {
            if (param.getValue() == null || param.getKey() == null) {
                throw new ClientException(StatusCode.Bad_Request, "Null parameter");
            }
            boolean curr = isValidNumberOfValue(param.getKey(), param.getValue().size());
            if (!curr) {
                return false;
            }
        }
        return !params.isEmpty();
    }

    @Override
    public void editElement(E element, Map<String, List<String>> params) throws ClientException {
        if (params == null) {
            throw new ClientException(StatusCode.Bad_Request, "Null parameters");
        } else if (element == null) {
            throw new ClientException(StatusCode.Bad_Request, "Empty object");
        }
        if (isValidNumberOfValues(params)) {
            for (Map.Entry<String, List<String>> param : params.entrySet()) {
                edit(element, param.getKey(), param.getValue());
            }
        } else {
            throw new ClientException(StatusCode.Bad_Request, "Invalid number of values of parameters");
        }
    }
}
