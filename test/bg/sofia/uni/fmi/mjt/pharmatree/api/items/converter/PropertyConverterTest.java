package bg.sofia.uni.fmi.mjt.pharmatree.api.items.converter;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property.PropertyController;
import bg.sofia.uni.fmi.mjt.pharmatree.api.testHelper.TestHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PropertyConverterTest {
    private static PropertyConverter converter;

    @BeforeAll
    static void init() {
        converter = new PropertyConverter();
        PropertyController.createProperty(2, "test1", "test1", List.of());
        PropertyController.createProperty(1, "test2", "test2", List.of());
    }

    @Test
    void testParseJson() throws ClientException {
        String json = TestHelper.getJsonOfObject(PropertyController.getProperty("test1") ,PropertyController.Property.class);
        PropertyController.Property test = converter.parseJson(json);
        assertEquals(test, PropertyController.getProperty("test1"));
    }

    @Test
    void testParseJsonWithoutElementException() throws ClientException {
        PropertyController.createProperty(2, "test1", "test1", List.of());
        String json = """
                {
                   "id": 2,
                   "description": "test1",
                   "allergies": []
                 }""";
        assertThrows(ClientException.class, () -> converter.parseJson(json));
        String json1 = """
                {
                   "id": 2,
                   "name": "test1",
                   "allergies": []
                 }""";
        assertThrows(ClientException.class, () -> converter.parseJson(json1));
        String json2 = """
                {
                   "id": 2,
                   "name": "test1",
                   "description": "test1"
                 }""";
        assertThrows(ClientException.class, () -> converter.parseJson(json2));
    }

    @Test
    void testParseLine() {
        String line = "2;test1;test1;";
        assertEquals(PropertyController.getProperty("test1"), converter.parseLine(line));
    }
}
