package bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.editor;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property.PropertyController;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property.PropertyParameters;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;

import java.util.List;

public class PropertyEditor extends BaseEditor<PropertyController.Property> {
    @Override
    protected boolean isValidNumberOfValue(String param, int size) throws ClientException {
        return switch (PropertyParameters.parseParameterFromString(param)) {
            case NAME, ID, DESCRIPTION -> size == 1;
            case ALLERGIES -> size != 0;
        };
    }

    @Override
    protected void edit(PropertyController.Property element, String param, List<String> val) throws ClientException {
        switch (PropertyParameters.parseParameterFromString(param)) {
            case NAME -> element.setName(val.getFirst());
            case DESCRIPTION -> element.setDescription(val.getFirst());
            case ALLERGIES -> element.setAllergies(val);
            case ID -> throw new ClientException(StatusCode.BAD_REQUEST, "You can't edit Property's id!");
        }
    }
}
