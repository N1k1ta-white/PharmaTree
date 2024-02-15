package bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;

import java.util.Arrays;

public enum PropertyParameters {
    Id("id"),
    Name("name"),
    Description("description"),
    Allergies("allergies");

    private final String name;

    PropertyParameters(String name) {
        this.name = name;
    }

    public String getString() {
        return name;
    }

    public static PropertyParameters parseParameterFromString(String str) throws ClientException {
        return Arrays.stream(PropertyParameters.values())
                .filter(elem -> elem.getString().equals(str))
                .findAny()
                .orElseThrow(() -> new ClientException(StatusCode.Bad_Request, "Incorrect parameter for Property"));
    }
}
