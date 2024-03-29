package bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.filter;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.Drug;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.DrugParameters;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property.PropertyController;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class DrugFilter extends BaseFilter<Drug> {
    protected Stream<Drug> filterStreamByParam(Stream<Drug> stream, Map.Entry<String, List<String>> param)
            throws ClientException {
        return switch (DrugParameters.parseParameterFromString(param.getKey())) {
            case ID, NAME -> stream;
            case COMPANY -> stream.filter(elem -> param.getValue().contains(elem.company()));
            case COUNTRY -> stream.filter(elem -> param.getValue().contains(elem.country()));
            case PROPERTIES -> stream.filter(elem -> new HashSet<>(elem.properties().stream()
                    .map(PropertyController.Property::name).toList())
                    .containsAll(param.getValue()));
            case WEIGHT -> stream.filter(elem -> param.getValue().stream()
                    .map(Double::parseDouble).toList().contains(elem.weight()));
            case COST -> stream.filter(elem -> param.getValue().stream()
                    .map(Double::parseDouble).toList().contains(elem.cost()));
        };
    }
}
