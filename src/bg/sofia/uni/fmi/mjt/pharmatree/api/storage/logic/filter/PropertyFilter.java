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
            case Id -> stream.filter(elem -> param.getValue().stream()
                    .map(Integer::parseInt).toList().contains(elem.id()));
            case Name -> stream.filter(elem -> param.getValue().contains(elem.name()));
            case Description -> stream.filter(elem -> param.getValue().contains(elem.description()));
            case Allergies -> stream.filter(elem -> new HashSet<>(param.getValue()).containsAll(elem.allergies()));
        };
    }
}
