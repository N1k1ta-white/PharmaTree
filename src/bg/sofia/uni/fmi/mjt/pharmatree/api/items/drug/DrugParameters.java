package bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;

import java.util.Arrays;

public enum DrugParameters {
    Id("id"),
    Name("name"),
    Company("company"),
    Country("country"),
    Properties("properties"),
    Cost("cost"),
    Weight("weight");

    private final String name;

    DrugParameters(String str) {
        name = str;
    }

    public String getString() {
        return name;
    }

    public static DrugParameters parseParameterFromString(String str) throws ClientException {
        return Arrays.stream(DrugParameters.values())
                .filter(elem -> elem.getString().equals(str))
                .findAny()
                .orElseThrow(() -> new ClientException(StatusCode.Bad_Request, "Incorrect drug parameter"));
    }
}
