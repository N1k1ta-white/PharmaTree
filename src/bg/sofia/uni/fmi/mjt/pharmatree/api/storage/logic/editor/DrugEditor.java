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
            case NAME, COMPANY, COUNTRY, WEIGHT, COST, ID -> size == 1;
            case PROPERTIES -> size != 0;
        };
    }

    @Override
    protected void edit(Drug element, String param, List<String> val) throws ClientException {
        switch (DrugParameters.parseParameterFromString(param)) {
            case NAME -> element.setName(val.getFirst());
            case COMPANY -> element.setCompany(val.getFirst());
            case COUNTRY -> element.setCountry(val.getFirst());
            case PROPERTIES -> element.setProperties(val.stream()
                    .map(PropertyController::getProperty)
                    .collect(Collectors.toList()));
            case WEIGHT -> element.setWeight(Double.parseDouble(val.getFirst()));
            case COST -> element.setCost(Double.parseDouble(val.getFirst()));
            case ID -> throw new ClientException(StatusCode.BAD_REQUEST, "You can't edit id in Drug object");
        }
    }
}
