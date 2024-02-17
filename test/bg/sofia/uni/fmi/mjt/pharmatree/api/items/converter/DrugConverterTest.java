package bg.sofia.uni.fmi.mjt.pharmatree.api.items.converter;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.Drug;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property.PropertyController;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.DrugStorage;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.PropertyStorage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DrugConverterTest {
    private static DrugConverter converter;

    @BeforeAll
    static void init() {
        converter = new DrugConverter();
        PropertyController.createProperty(2, "test1", "test1", List.of());
        PropertyController.createProperty(1, "test2", "test2", List.of());
    }

    @Test
    void testParseJson() throws ClientException {
        String json = """
                         {
                            "name":"testName",
                            "company":"TESTMed",
                            "country":"Ukraine",
                            "properties":["test1"],
                            "cost":5,
                            "weight":10
                         }""";
        DrugConverter converter = new DrugConverter();
        Drug test = converter.parseJson(json);
        assertEquals(new Drug(0, "testName", "TESTMed", "Ukraine",
                        List.of(PropertyController.getProperty("test1")), 5, 10), test);
    }

    @Test
    void testParseJsonWithoutElementException() {
        PropertyController.createProperty(2, "test1", "test1", List.of());
        String json = """
                         {
                            "company":"TESTMed",
                            "country":"Ukraine",
                            "properties":["test1"],
                            "cost":5,
                            "weight":10
                         }""";
        assertThrows(ClientException.class, () -> converter.parseJson(json));
        String json1 = """
                         {
                            "name":"testName",
                            "company":"TESTMed",
                            "country":"Ukraine",
                            "cost":5,
                            "weight":10
                         }""";
        assertThrows(ClientException.class, () -> converter.parseJson(json1));
        String json2 = """
                         {
                            "name":"testName",
                            "country":"Ukraine",
                            "properties":["test1"],
                            "cost":5,
                            "weight":10
                         }""";
        assertThrows(ClientException.class, () -> converter.parseJson(json2));
        String json3 = """
                         {
                            "name":"testName",
                            "company":"TESTMed",
                            "country":"Ukraine",
                            "properties":["test1"],
                            "cost":5
                         }""";
        assertThrows(ClientException.class, () -> converter.parseJson(json3));
    }

    @Test
    void testParseLine() {
        String line = "1;testName;TESTMed;Ukraine;test1,test2;5;10";
        assertEquals(new Drug(0, "testName", "TESTMed", "Ukraine",
                List.of(PropertyController.getProperty("test1"), PropertyController.getProperty("test2")),
                5, 10), converter.parseLine(line));
    }
}
