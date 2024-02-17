package bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.editor;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.Drug;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.DrugParameters;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property.PropertyController;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;

import java.util.List;
import java.util.stream.Collectors;

public class DrugEditor extends BaseEditor<Drug> {
    @Override
    protected boolean isValidNumberOfValue(String param, int size) throws ClientException {
        return switch (DrugParameters.parseParameterFromString(param)) {
            case Name, Company, Country, Weight, Cost, Id -> size == 1;
            case Properties -> size != 0;
        };
    }

    @Override
    protected void edit(Drug element, String param, List<String> val) throws ClientException {
        switch (DrugParameters.parseParameterFromString(param)) {
            case Name -> element.setName(val.getFirst());
            case Company -> element.setCompany(val.getFirst());
            case Country -> element.setCountry(val.getFirst());
            case Properties -> element.setProperties(val.stream()
                    .map(PropertyController::getProperty)
                    .collect(Collectors.toList()));
            case Weight -> element.setWeight(Double.parseDouble(val.getFirst()));
            case Cost -> element.setCost(Double.parseDouble(val.getFirst()));
            case Id -> throw new ClientException(StatusCode.Bad_Request, "You can't edit id in Drug object");
        }
    }
}
