package bg.sofia.uni.fmi.mjt.pharmatree.api.items.parser;

import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.Drug;
import com.google.gson.JsonParser;

import java.util.List;

public class DrugConverter implements ItemConverter<Drug> {
    private static final int ID = 0;
    private static final int NAME = 1;
    private static final int COMP = 2;
    private static final int COUNTRY = 3;
    private static final int PROP = 4;
    private static final int COST = 5;
    private static final int WEIGHT = 6;
    private static final String SEPARATOR = ";";
    private static final String SEPARATOR_ARRAY = ",";
    public Drug parseLine(String line) {
        String[] elements = line.split(SEPARATOR);
        return new Drug(Integer.parseInt(elements[ID]), elements[NAME], elements[COMP], elements[COUNTRY],
                List.of(elements[PROP].split(SEPARATOR_ARRAY)), Double.parseDouble(elements[COST]),
                Double.parseDouble(elements[WEIGHT]));
    }

    public Drug parseJson(String json) {
        return GSON.fromJson(JsonParser.parseString(json).getAsJsonObject(), Drug.class);
    }

    public String convertListToJson(List<Drug> drugs) {
        return GSON.toJson(drugs);
    }
}
