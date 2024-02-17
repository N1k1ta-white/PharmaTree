package bg.sofia.uni.fmi.mjt.pharmatree.api.items.converter;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property.PropertyController;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.CsvSeparator;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class PropertyConverter implements ItemConverter<PropertyController.Property> {
    private static final int ID = 0;
    private static final int NAME = 1;
    private static final int DESCR = 2;
    private static final int ALLERG = 3;
    private static final int COUNT = 4;
    private static final Gson GSON;

    static {
        GSON = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public PropertyController.Property parseLine(String line) {
        String[] data = line.split(CsvSeparator.getSeparator(), COUNT);
        return PropertyController.createProperty(Integer.parseInt(data[ID]), data[NAME], data[DESCR],
                List.of(data[ALLERG].split(CsvSeparator.getArraySeparator())));
    }

    @Override
    public PropertyController.Property parseJson(String json) throws ClientException {
        PropertyController.Property property = GSON.fromJson(json, PropertyController.Property.class);
        if (property.name() == null || property.allergies() == null || property.description() == null) {
            throw new ClientException(StatusCode.Bad_Request, "Request hasn't enough data for creating a Property");
        }
        return property;
    }

    @Override
    public String convertListToJson(List<PropertyController.Property> obj) {
        return GSON.toJson(obj);
    }
}
