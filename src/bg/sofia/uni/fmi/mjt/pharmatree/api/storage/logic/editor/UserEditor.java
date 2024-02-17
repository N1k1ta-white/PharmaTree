package bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.editor;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.Role;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.User;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.UserProperty;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;

import java.util.List;

public class UserEditor extends BaseEditor<User> {

    @Override
    protected boolean isValidNumberOfValue(String param, int size) throws ClientException {
        return switch (UserProperty.parseParameterFromString(param)) {
            case Name, Id, Role, UserId  -> size == 1;
        };
    }

    @Override
    protected void edit(User element, String param, List<String> val) throws ClientException {
        switch (UserProperty.parseParameterFromString(param)) {
            case Name -> element.setName(val.getFirst());
            case Role -> element.setRole(Role.parseParameterFromString(val.getFirst()));
            case UserId -> element.setUserId(val.getFirst());
            case Id -> throw new ClientException(StatusCode.Bad_Request, "You can't edit id of User object");
        }
    }
}
