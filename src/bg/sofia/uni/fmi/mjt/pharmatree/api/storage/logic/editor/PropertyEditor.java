package bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.editor;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property.PropertyController;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property.PropertyParameters;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;

import java.util.List;
import java.util.Map;

public class PropertyEditor implements Editor<PropertyController.Property> {
    private boolean isValidNumberOfValues(Map<String, List<String>> params) throws ClientException {
        for (Map.Entry<String, List<String>> param : params.entrySet()) {
            boolean curr = switch (PropertyParameters.parseParameterFromString(param.getKey())) {
                case Name, Id, Description -> param.getValue().size() == 1;
                case Allergies -> !param.getValue().isEmpty();
            };
            if (!curr) {
                return false;
            }
        }
        return !params.isEmpty();
    }

    @Override
    public void editElement(PropertyController.Property element, Map<String, List<String>> params)
            throws ClientException {
        if (isValidNumberOfValues(params)) {
            for (Map.Entry<String, List<String>> param : params.entrySet()) {
                switch (PropertyParameters.parseParameterFromString(param.getKey())) {
                    case Name -> element.setName(param.getValue().getFirst());
                    case Description -> element.setDescription(param.getValue().getFirst());
                    case Allergies -> element.setAllergies(param.getValue());
                    case Id -> throw new ClientException(StatusCode.Bad_Request, "You can't edit Property's id!");
                }
            }
        }
        throw new ClientException(StatusCode.Bad_Request, "Not valid parameters for editing of Property!");
    }
}
