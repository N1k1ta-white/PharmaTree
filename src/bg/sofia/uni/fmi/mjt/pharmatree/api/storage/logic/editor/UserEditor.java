package bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.editor;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ServerException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.Role;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.User;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.UserProperty;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;

import java.util.List;
import java.util.Map;

public class UserEditor implements Editor<User> {

    private boolean isValidNumberOfValues(Map<String, List<String>> params) throws ClientException {
        for (Map.Entry<String, List<String>> param : params.entrySet()) {
            boolean curr = switch (UserProperty.parseParameterFromString(param.getKey())) {
                case Name, Id, Role, UserId  -> param.getValue().size() == 1;
            };
            if (!curr) {
                return false;
            }
        }
        return !params.isEmpty();
    }

    @Override
    public void editElement(User element, Map<String, List<String>> params) throws ClientException, ServerException {
        if (isValidNumberOfValues(params)) {
            for (Map.Entry<String, List<String>> param : params.entrySet()) {
                switch (UserProperty.parseParameterFromString(param.getKey())) {
                    case Name -> element.setName(param.getValue().getFirst());
                    case Role -> element.setRole(Role.parseParameterFromString(param.getValue().getFirst()));
                    case UserId -> element.serUserId(param.getValue().getFirst());
                    case Id -> throw new ClientException(StatusCode.Bad_Request);
                }
            }
        }
        throw new ClientException(StatusCode.Bad_Request);
    }
}
