package bg.sofia.uni.fmi.mjt.pharmatree.api.storage;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum ItemsType {
    User("user"),
    Property("property"),
    Drug("drug");

    private final String name;

    ItemsType(String name) {
        this.name = name;
    }

    public static ItemsType parseFromString(String type) {
        return Arrays.stream(ItemsType.values()).filter(elem -> elem.name.equals(type)).findAny().orElseThrow(
                () -> new NoSuchElementException("You give incorrect type in parser of storage types: " + type));
    }
}
