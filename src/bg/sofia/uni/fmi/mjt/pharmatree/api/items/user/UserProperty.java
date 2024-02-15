package bg.sofia.uni.fmi.mjt.pharmatree.api.items.user;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;

import java.util.Arrays;

public enum UserProperty {
    Id("id"),
    Name("name"),
    Role("role"),
    UserId("user-id");

    private final String name;

    UserProperty(String str) {
        name = str;
    }

    public String getString() {
        return name;
    }

    public static UserProperty parseParameterFromString(String str) throws ClientException {
        return Arrays.stream(UserProperty.values())
                .filter(elem -> elem.getString().equals(str))
                .findAny()
                .orElseThrow(() -> new ClientException(StatusCode.Bad_Request));
    }
}
