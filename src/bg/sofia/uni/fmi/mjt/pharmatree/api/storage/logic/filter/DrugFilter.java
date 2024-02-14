package bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.filter;

import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.Drug;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.DrugParameters;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class DrugFilter implements Filter<Drug> {
    private Stream<Drug> filterStreamByParam(Stream<Drug> stream, Map.Entry<String, List<String>> param) {
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

    public Stream<Drug> filterStreamByParams(Stream<Drug> stream, Map<String, List<String>> params) {
        for (Map.Entry<String, List<String>> entry : params.entrySet()) {
            stream = filterStreamByParam(stream, entry);
        }
        return stream;
    }

    @Override
    public Optional<Drug> getElementById(Stream<Drug> stream, int id) {
        return stream.parallel().filter(el -> el.id() == id).findAny();
    }
}
