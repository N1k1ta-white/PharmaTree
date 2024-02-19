package bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;

import java.util.Arrays;

public enum DrugParameters {
    ID("id"),
    NAME("name"),
    COMPANY("company"),
    COUNTRY("country"),
    PROPERTIES("properties"),
    COST("cost"),
    WEIGHT("weight");

    private final String name;

    DrugParameters(String str) {
        name = str;
    }

    public String getValue() {
        return name;
    }

    public static DrugParameters parseParameterFromString(String str) throws ClientException {
        return Arrays.stream(DrugParameters.values())
                .filter(elem -> elem.getValue().equals(str))
                .findAny()
                .orElseThrow(() -> new ClientException(StatusCode.BAD_REQUEST, "Incorrect drug parameter"));
    }
}
