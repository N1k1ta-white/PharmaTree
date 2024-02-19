package bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;

import java.util.Arrays;

public enum PropertyParameters {
    ID("id"),
    NAME("name"),
    DESCRIPTION("description"),
    ALLERGIES("allergies");

    private final String name;

    PropertyParameters(String name) {
        this.name = name;
    }

    public String getValue() {
        return name;
    }

    public static PropertyParameters parseParameterFromString(String str) throws ClientException {
        return Arrays.stream(PropertyParameters.values())
                .filter(elem -> elem.getValue().equals(str))
                .findAny()
                .orElseThrow(() -> new ClientException(StatusCode.BAD_REQUEST, "Incorrect parameter for Property"));
    }
}
