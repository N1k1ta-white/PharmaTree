package bg.sofia.uni.fmi.mjt.pharmatree.api.items.converter;

import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.Drug;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DrugConverterTest {

    @Test
    void testParseJson() {
        String json = """
                         {
                            "name":"testName",
                            "company":"TESTMed",
                            "country":"Ukraine",
                            "properties":[
                               "test1",
                               "test2"
                            ],
                            "cost":5,
                            "weight":10
                         }""";
        DrugConverter converter = new DrugConverter();
        Drug test = converter.parseJson(json);
        assertEquals(new Drug(0, "testName", "TESTMed", "Ukraine", List.of("test1", "test2"), 5, 10),
                test);
    }
}
