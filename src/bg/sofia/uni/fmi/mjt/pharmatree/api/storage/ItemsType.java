package bg.sofia.uni.fmi.mjt.pharmatree.api.storage;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;

import java.util.Arrays;

public enum ItemsType {
    USER("user"),
    PROPERTY("property"),
    DRUG("drug");

    private final String name;

    ItemsType(String name) {
        this.name = name;
    }

    public static ItemsType parseFromString(String type) throws ClientException {
        return Arrays.stream(ItemsType.values()).filter(elem -> elem.name.equals(type)).findAny().orElseThrow(
                () -> new ClientException(StatusCode.NOT_FOUND, "Incorrect item type"));
    }
}
