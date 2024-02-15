package bg.sofia.uni.fmi.mjt.pharmatree.api.items.converter;

import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property.Property;

import java.util.List;

public class PropertyConverter implements ItemConverter<Property> {
    private static final int ID = 0;
    private static final int NAME = 1;
    private static final int DESCR = 2;
    private static final int ALLERG = 3;

    @Override
    public Property parseLine(String line) {
        String[] data = line.split(SEPARATOR);
        return new Property(Integer.parseInt(data[ID]), data[NAME], data[DESCR],
                List.of(data[ALLERG].split(SEPARATOR_ARRAY)));
    }

    @Override
    public Property parseJson(String json) {
        return GSON.fromJson(json, Property.class);
    }

    @Override
    public String convertListToJson(List<Property> obj) {
        return GSON.toJson(obj);
    }
}
