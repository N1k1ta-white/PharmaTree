package bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug;

import java.util.Arrays;
import java.util.NoSuchElementException;

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

    public static DrugParameters parseParameterFromString(String str) {
        return Arrays.stream(DrugParameters.values())
                .filter(elem -> elem.getString().equals(str))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("You parse incorrect parameter from string: " + str));
    }
}
