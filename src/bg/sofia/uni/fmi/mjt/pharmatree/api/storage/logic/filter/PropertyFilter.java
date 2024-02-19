package bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.filter;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property.PropertyController;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property.PropertyParameters;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class PropertyFilter extends BaseFilter<PropertyController.Property> {
    protected Stream<PropertyController.Property> filterStreamByParam(Stream<PropertyController.Property> stream,
                                                                      Map.Entry<String, List<String>> param)
            throws ClientException {
        return switch (PropertyParameters.parseParameterFromString(param.getKey())) {
            case ID, NAME -> stream;
            case DESCRIPTION -> stream.filter(elem -> param.getValue().contains(elem.description()));
            case ALLERGIES -> stream.filter(elem -> new HashSet<>(elem.allergies()).containsAll(param.getValue()));
        };
    }
}
