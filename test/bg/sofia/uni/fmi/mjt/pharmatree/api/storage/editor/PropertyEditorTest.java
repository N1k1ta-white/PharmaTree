package bg.sofia.uni.fmi.mjt.pharmatree.api.storage.editor;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property.PropertyController;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property.PropertyParameters;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.editor.PropertyEditor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PropertyEditorTest {
    public static PropertyController.Property  propExample;
    public static PropertyEditor editor;
    @BeforeAll
    static void beforeAll() {
        editor = new PropertyEditor();
        propExample = PropertyController.createProperty(6, "prop2", "prop2", List.of());
    }

    @Test
    void testEditElement() throws ClientException {
        PropertyController.Property test = propExample;
        Map<String, List<String>> params = new HashMap<>();
        params.put(PropertyParameters.NAME.getValue(), List.of("new_name"));
        params.put(PropertyParameters.DESCRIPTION.getValue(), List.of("descr"));
        params.put(PropertyParameters.ALLERGIES.getValue(), List.of("allerg2"));
        editor.editElement(test, params);
        assertEquals(test.name(), "new_name");
        assertEquals(test.description(), "descr");
        assertEquals(test.allergies(), List.of("allerg2"));
    }

    @Test
    void testEditElementExceptionNull() throws ClientException {
        PropertyController.Property test = propExample;
        Map<String, List<String>> params = new HashMap<>();
        params.put(PropertyParameters.NAME.getValue(), null);
        assertThrows(ClientException.class, () -> editor.editElement(test, params));
        params.clear();
        params.put(null, List.of("something"));
        assertThrows(ClientException.class, () -> editor.editElement(test, params));
        assertThrows(ClientException.class, () -> editor.editElement(test,null));
    }

    @Test
    void testEditElementExceptionInvalidArgs() throws ClientException {
        PropertyController.Property test = propExample;
        Map<String, List<String>> params = new HashMap<>();
        params.put(PropertyParameters.NAME.getValue() + "p", List.of("test"));
        assertThrows(ClientException.class, () -> editor.editElement(test, params));
        params.clear();
        params.put(PropertyParameters.ALLERGIES.name() + "e", List.of("something"));
        assertThrows(ClientException.class, () -> editor.editElement(test, params));
    }

    @Test
    void testEditElementExceptionIdEdit() throws ClientException {
        PropertyController.Property test = propExample;
        Map<String, List<String>> params = new HashMap<>();
        params.put(PropertyParameters.ID.getValue(), List.of("test"));
        assertThrows(ClientException.class, () -> editor.editElement(test, params));
    }
}
