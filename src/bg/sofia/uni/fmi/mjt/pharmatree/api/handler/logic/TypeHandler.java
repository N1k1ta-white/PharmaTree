package bg.sofia.uni.fmi.mjt.pharmatree.api.handler.logic;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;

import java.util.Arrays;

public enum TypeHandler {
    Post("POST"),
     Get("GET"),
    Put("PUT"),
    Delete("DELETE"),
    Patch("PATCH");

    private final String name;

    TypeHandler(String name) {
        this.name = name;
    }

    public static TypeHandler parseMethodType(String type) throws ClientException {
        return Arrays.stream(TypeHandler.values()).filter(elem -> elem.name.equals(type)).findAny().orElseThrow(
                () -> new ClientException(StatusCode.Bad_Request, "Invalid type of method!")
        );
    }
}
