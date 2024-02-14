package bg.sofia.uni.fmi.mjt.pharmatree.api.items.parser;

import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.Drug;

import java.util.List;

public class DrugParser implements ItemParser<Drug> {
    private static final int NAME = 0;
    private static final int COMP = 1;
    private static final int COUNTRY = 2;
    private static final int PROP = 3;
    private static final int COST = 4;
    private static final int WEIGHT = 5;
    private static final String SEPARATOR = ";";
    private static final String SEPARATOR_ARRAY = ",";
    public Drug of(String line) {
        String[] elements = line.split(SEPARATOR);
        return new Drug(elements[NAME], elements[COMP], elements[COUNTRY],
                List.of(elements[PROP].split(SEPARATOR_ARRAY)), Double.parseDouble(elements[COST]),
                Double.parseDouble(elements[WEIGHT]));
    }
}
