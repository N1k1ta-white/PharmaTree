//package bg.sofia.uni.fmi.mjt.pharmatree.api.storage.filter;
//
//import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
//import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property.PropertyController;
//import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property.PropertyParameters;
//import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.filter.PropertyFilter;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//public class PropertyFilterTest {
//    static List<PropertyController.Property> propertyList;
//    static PropertyFilter filter;
//
//    @BeforeAll
//    static void beforeAll() {
//        filter = new PropertyFilter();
//        propertyList = List.of(PropertyController.createProperty(1, "test1", "test1", List.of()),
//                PropertyController.createProperty(2, "test2", "test2", List.of("all1")),
//                PropertyController.createProperty(3, "test3", "test3", List.of()),
//                PropertyController.createProperty(4, "test4", "test4", List.of("all1", "all2")));
//    }
//
//    @Test
//    void testGetElementById() {
//        assertEquals(filter.getElementById(propertyList.stream().parallel(), 1).get(), PropertyController.getProperty("test1"));
//        assertEquals(filter.getElementById(propertyList.stream().parallel(), 4).get(), PropertyController.getProperty("test4"));
//    }
//
//    @Test
//    void testFilterStreamsByParam() throws ClientException {
//        Map<String, List<String>> param = new HashMap<>();
//        param.put(PropertyParameters.ID.getValue(), List.of("3"));
//        assertEquals(filter.filterStreamByParams(propertyList.stream().parallel(), param).toList(),
//                List.of(PropertyController.getProperty("test3")));
//        param.clear();
//        param.put(PropertyParameters.ALLERGIES.getValue(), List.of("all1"));
//        assertEquals(List.of(PropertyController.getProperty("test2"), PropertyController.getProperty("test4")),
//                filter.filterStreamByParams(propertyList.stream(), param).toList());
//        param.put(PropertyParameters.ALLERGIES.getValue(), List.of("all2"));
//        assertEquals(List.of(PropertyController.getProperty("test4")),
//                filter.filterStreamByParams(propertyList.stream(), param).toList());
//    }
//
//    @Test
//    void testFilterStreamByParamsException() {
//        Map<String, List<String>> param = new HashMap<>();
//        param.put(PropertyParameters.ID.getValue() + "r", List.of("3"));
//        assertThrows(ClientException.class,
//                () -> filter.filterStreamByParams(propertyList.stream().parallel(), param).toList());
//    }
//}
