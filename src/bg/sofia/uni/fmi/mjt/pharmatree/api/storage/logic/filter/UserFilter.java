package bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.filter;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.User;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.UserProperty;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class UserFilter extends BaseFilter<User> {
    @Override
    protected Stream<User> filterStreamByParam(Stream<User> stream, Map.Entry<String, List<String>> param)
            throws ClientException {
        return switch (UserProperty.parseParameterFromString(param.getKey())) {
            case ID, NAME -> stream;
            case ROLE -> stream.filter(elem -> param.getValue().contains(elem.role().getValue()));
            case USER_ID -> stream.filter(elem -> param.getValue().contains(elem.userId()));
        };
    }
}
