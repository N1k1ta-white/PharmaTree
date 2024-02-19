package bg.sofia.uni.fmi.mjt.pharmatree.api.items.converter;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.Drug;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.DrugDeserializer;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property.PropertyController;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.CsvSeparator;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import java.util.Arrays;
import java.util.List;

public class DrugConverter implements ItemConverter<Drug> {
    private static final int ID = 0;
    private static final int NAME = 1;
    private static final int COMP = 2;
    private static final int COUNTRY = 3;
    private static final int PROP = 4;
    private static final int COST = 5;
    private static final int WEIGHT = 6;
    private static final int COUNT = 7;
    private static final Gson GSON;

    static {
        GSON = new GsonBuilder().registerTypeAdapter(Drug.class, new DrugDeserializer()).setPrettyPrinting().create();
    }

    public Drug parseLine(String line) {
        String[] elements = line.split(CsvSeparator.getSeparator(), COUNT);
        return new Drug(Integer.parseInt(elements[ID]), elements[NAME], elements[COMP], elements[COUNTRY],
                Arrays.stream(elements[PROP].split(CsvSeparator.getArraySeparator()))
                        .map(PropertyController::getProperty).toList(),
                Double.parseDouble(elements[COST]), Double.parseDouble(elements[WEIGHT]));
    }

    public Drug parseJson(String json) throws ClientException {
        Drug drug = GSON.fromJson(JsonParser.parseString(json).getAsJsonObject(), Drug.class);
        if (drug.properties().isEmpty()) {
            throw new ClientException(StatusCode.BAD_REQUEST, "New drug hasn't properties which exist in Database");
        } else if (drug.name().isBlank() || drug.cost() == 0 || drug.company().isBlank() || drug.country().isBlank()
                    || drug.weight() == 0) {
            throw new ClientException(StatusCode.BAD_REQUEST, "Not enough data for creating a object");
        }
        return drug;
    }

    public String convertListToJson(List<Drug> drugs) {
        return GSON.toJson(drugs);
    }
}
