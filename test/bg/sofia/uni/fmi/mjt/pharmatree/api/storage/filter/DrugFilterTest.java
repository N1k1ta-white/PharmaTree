package bg.sofia.uni.fmi.mjt.pharmatree.api.storage.filter;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.Drug;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.DrugParameters;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property.PropertyController;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.filter.DrugFilter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DrugFilterTest {
    static List<Drug> drugList;
    static DrugFilter filter;

    @BeforeAll
    static void beforeAll() {
        filter = new DrugFilter();
        PropertyController.createProperty(1, "test1", "test1", List.of());
        PropertyController.createProperty(2, "test2", "test2", List.of("all1"));

        drugList = List.of(new Drug(1, "test1", "medTest", "Ukr",
                List.of(PropertyController.getProperty("test1")), 1, 2),
                new Drug(2, "test2", "medTest2", "Ger",
                        List.of(PropertyController.getProperty("test2")), 6, 2),
                new Drug(3, "test3", "medTest", "Ukr",
                    List.of(PropertyController.getProperty("test2")), 10, 1)
                );
    }

    @Test
    void testGetElementById() {
        assertEquals(filter.getElementById(drugList.stream().parallel(), 1).get(),
                new Drug(1, "test1", "medTest", "Ukr",
                        List.of(PropertyController.getProperty("test1")), 1, 2));
        assertEquals(filter.getElementById(drugList.stream().parallel(), 3).get(),
                new Drug(3, "test3", "medTest", "Ukr",
                        List.of(PropertyController.getProperty("test2")), 10, 1)
        );
    }

    @Test
    void testFilterStreamsByParam() throws ClientException {
        Map<String, List<String>> param = new HashMap<>();
        param.put(DrugParameters.Id.getValue(), List.of("3"));
        assertEquals(filter.filterStreamByParams(drugList.stream().parallel(), param).toList(),
                List.of(new Drug(3, "test3", "medTest", "Ukr",
                        List.of(PropertyController.getProperty("test2")), 10, 1)));
        param.clear();
        param.put(DrugParameters.Country.getValue(), List.of("Ukr"));
        assertEquals(List.of(new Drug(1, "test1", "medTest", "Ukr",
                        List.of(PropertyController.getProperty("test1")), 1, 2),
                new Drug(3, "test3", "medTest", "Ukr",
                        List.of(PropertyController.getProperty("test2")), 10, 1)),
                filter.filterStreamByParams(drugList.stream(), param).toList());
        param.put(DrugParameters.Country.getValue(), List.of("Ukr", "Ger"));
        assertEquals(List.of(
                        new Drug(1, "test1", "medTest", "Ukr",
                                List.of(PropertyController.getProperty("test1")), 1, 2),
                        new Drug(2, "test2", "medTest2", "Ger",
                                List.of(PropertyController.getProperty("test2")), 6, 2),
                        new Drug(3, "test3", "medTest", "Ukr",
                                List.of(PropertyController.getProperty("test2")), 10, 1)
                ),
                filter.filterStreamByParams(drugList.stream(), param).toList());
    }

    @Test
    void testFilterStreamByParamsException() {
        Map<String, List<String>> param = new HashMap<>();
        param.put(DrugParameters.Id.getValue() + "r", List.of("3"));
        assertThrows(ClientException.class,
                () -> filter.filterStreamByParams(drugList.stream().parallel(), param).toList());
    }
}
