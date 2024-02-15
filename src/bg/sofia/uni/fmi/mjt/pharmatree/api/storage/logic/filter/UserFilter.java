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
            case Id -> stream.filter(elem -> param.getValue().stream()
                    .map(Integer::parseInt).toList().contains(elem.id()));
            case Name -> stream.filter(elem -> param.getValue().contains(elem.name()));
            case Role -> stream.filter(elem -> param.getValue().contains(elem.role().getName()));
            case UserId -> stream.filter(elem -> param.getValue().contains(elem.userId()));
        };
    }
}
