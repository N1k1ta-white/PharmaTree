package bg.sofia.uni.fmi.mjt.pharmatree.api.handler.logic;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;

import java.util.Arrays;

public enum TypeHandler {
    POST("POST"),
    GET("GET"),
    PUT("PUT"),
    DELETE("DELETE"),
    PATCH("PATCH");

    private final String name;

    TypeHandler(String name) {
        this.name = name;
    }

    public static TypeHandler parseMethodType(String type) throws ClientException {
        return Arrays.stream(TypeHandler.values()).filter(elem -> elem.name.equals(type)).findAny().orElseThrow(
                () -> new ClientException(StatusCode.BAD_REQUEST, "Invalid type of method!")
        );
    }
}
