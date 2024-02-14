package bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.editor;

import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.Drug;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.DrugParameters;

import java.util.List;
import java.util.Map;

public class DrugEditor implements Editor<Drug> {

    private static final int COUNT = DrugParameters.values().length;

    private boolean isValidNumberOfValues(Map<String, List<String>> params) {
        for (Map.Entry<String, List<String>> param : params.entrySet()) {
            boolean curr = switch (DrugParameters.parseParameterFromString(param.getKey())) {
                case Name, Company, Country, Weight, Cost, Id -> param.getValue().size() == 1;
                case Properties -> !param.getValue().isEmpty();
            };
            if (!curr) {
                return false;
            }
        }
        return !params.isEmpty();
    }

    public boolean isValidDescription(Map<String, List<String>> params) {
        return params.keySet().size() == COUNT && isValidNumberOfValues(params);
    }

    @Override
    public void editElement(Drug element, Map<String, List<String>> params) {
        if (isValidNumberOfValues(params)) {
            for (Map.Entry<String, List<String>> param : params.entrySet()) {
                switch (DrugParameters.parseParameterFromString(param.getKey())) {
                    case Name -> element.setName(param.getValue().getFirst());
                    case Company -> element.setCompany(param.getValue().getFirst());
                    case Country -> element.setCountry(param.getValue().getFirst());
                    case Properties -> element.setProperties(param.getValue());
                    case Weight -> element.setWeight(Double.parseDouble(param.getValue().getFirst()));
                    case Cost -> element.setCost(Double.parseDouble(param.getValue().getFirst()));
                    case Id -> throw new //TODO exception;
                }
            }
        }
        throw new IllegalArgumentException("Not valid parameters for replacement of the chosen Drug!");
    }
}
