package bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug;

import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property.PropertyController;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DrugDeserializer implements JsonDeserializer<Drug> {
    @Override
    public Drug deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        JsonObject obj = jsonElement.getAsJsonObject();
        List<PropertyController.Property> propertyList = new ArrayList<>();
        if (obj.has(DrugParameters.PROPERTIES.getValue())) {
            for (JsonElement prop : obj.getAsJsonArray(DrugParameters.PROPERTIES.getValue())) {
                PropertyController.Property curr = PropertyController.getProperty(prop.getAsString());
                if (!curr.equals(PropertyController.getEmptyProperty())) {
                    propertyList.add(curr);
                }
            }
        }
        Map<String, String> elementMap = obj.asMap().entrySet().stream()
                .filter(entry -> !entry.getKey().equals(DrugParameters.PROPERTIES.getValue()))
                .map(entry -> Map.entry(entry.getKey(), entry.getValue().getAsString()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return new Drug(0,
                elementMap.getOrDefault(DrugParameters.NAME.getValue(), ""),
                elementMap.getOrDefault(DrugParameters.COMPANY.getValue(), ""),
                elementMap.getOrDefault(DrugParameters.COUNTRY.getValue(), ""),
                propertyList,
                Double.parseDouble(elementMap.getOrDefault(DrugParameters.COST.getValue(),  "0")),
                Double.parseDouble(elementMap.getOrDefault(DrugParameters.WEIGHT.getValue(), "0")));
    }
}
