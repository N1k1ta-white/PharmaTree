package bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.filter;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.Drug;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.DrugParameters;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class DrugFilter extends BaseFilter<Drug> {
    protected Stream<Drug> filterStreamByParam(Stream<Drug> stream, Map.Entry<String, List<String>> param)
            throws ClientException {
        return switch (DrugParameters.parseParameterFromString(param.getKey())) {
            case Id -> stream.filter(elem -> param.getValue().stream()
                    .map(Integer::parseInt).toList().contains(elem.id()));
            case Name -> stream.filter(elem -> param.getValue().contains(elem.name()));
            case Company -> stream.filter(elem -> param.getValue().contains(elem.company()));
            case Country -> stream.filter(elem -> param.getValue().contains(elem.country()));
            case Properties -> stream.filter(elem -> new HashSet<>(param.getValue()).containsAll(elem.properties()));
            case Weight -> stream.filter(elem -> param.getValue().stream()
                    .map(Double::parseDouble).toList().contains(elem.weight()));
            case Cost -> stream.filter(elem -> param.getValue().stream()
                    .map(Double::parseDouble).toList().contains(elem.cost()));
        };
    }
}
