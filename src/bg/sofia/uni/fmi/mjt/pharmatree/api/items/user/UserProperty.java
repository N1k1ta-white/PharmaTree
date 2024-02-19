package bg.sofia.uni.fmi.mjt.pharmatree.api.items.user;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;

import java.util.Arrays;

public enum UserProperty {
    ID("id"),
    NAME("name"),
    ROLE("role"),
    USER_ID("user-id");

    private final String name;

    UserProperty(String str) {
        name = str;
    }

    public String getValue() {
        return name;
    }

    public static UserProperty parseParameterFromString(String str) throws ClientException {
        return Arrays.stream(UserProperty.values())
                .filter(elem -> elem.getValue().equals(str))
                .findAny()
                .orElseThrow(() -> new ClientException(StatusCode.BAD_REQUEST, "Incorrect user parameter"));
    }
}
