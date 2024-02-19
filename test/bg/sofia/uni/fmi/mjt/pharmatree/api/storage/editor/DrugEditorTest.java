package bg.sofia.uni.fmi.mjt.pharmatree.api.storage.editor;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.Drug;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.DrugParameters;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property.PropertyController;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.editor.DrugEditor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DrugEditorTest {
    public static Drug drugExample;
    public static DrugEditor editor;
    @BeforeAll
    static void beforeAll() {
        editor = new DrugEditor();
        PropertyController.createProperty(6, "prop2", "prop2", List.of());
        drugExample = new Drug(5, "Test", "UkrMed", "Ukr",
                List.of(PropertyController.createProperty(2, "prop1", "prop1", List.of("alerg1"))),
                10, 10);
    }

    @Test
    void testEditElement() throws ClientException {
        Drug test = drugExample;
        Map<String, List<String>> params = new HashMap<>();
        params.put(DrugParameters.NAME.getValue(), List.of("new_name"));
        params.put(DrugParameters.COUNTRY.getValue(), List.of("Ger"));
        params.put(DrugParameters.PROPERTIES.getValue(), List.of("prop2"));
        editor.editElement(test, params);
        assertEquals(test.name(), "new_name");
        assertEquals(test.country(), "Ger");
        assertEquals(test.properties(), List.of(PropertyController.getProperty("prop2")));
    }

    @Test
    void testEditElementExceptionNull() throws ClientException {
        Drug test = drugExample;
        Map<String, List<String>> params = new HashMap<>();
        params.put(DrugParameters.NAME.getValue(), null);
        assertThrows(ClientException.class, () -> editor.editElement(test, params));
        params.clear();
        params.put(null, List.of("something"));
        assertThrows(ClientException.class, () -> editor.editElement(test, params));
    }

    @Test
    void testEditElementExceptionInvalidArgs() throws ClientException {
        Drug test = drugExample;
        Map<String, List<String>> params = new HashMap<>();
        params.put(DrugParameters.NAME.getValue() + "p", List.of("test"));
        assertThrows(ClientException.class, () -> editor.editElement(test, params));
        params.clear();
        params.put(DrugParameters.NAME.name() + "e", List.of("something"));
        assertThrows(ClientException.class, () -> editor.editElement(test, params));
    }

    @Test
    void testEditElementExceptionIdEdit() throws ClientException {
        Drug test = drugExample;
        Map<String, List<String>> params = new HashMap<>();
        params.put(DrugParameters.ID.getValue(), List.of("test"));
        assertThrows(ClientException.class, () -> editor.editElement(test, params));
    }
}