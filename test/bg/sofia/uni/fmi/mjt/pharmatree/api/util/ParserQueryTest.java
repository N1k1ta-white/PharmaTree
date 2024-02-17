package bg.sofia.uni.fmi.mjt.pharmatree.api.util;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class ParserQueryTest {

    @Test
    void testParseQuery() {
        String query = "name=hello&name2=world";
        Map<String, List<String>> param = ParserQuery.parseQuery(query);
        assertIterableEquals(param.get("name"), List.of("hello"));
        assertIterableEquals(param.get("name2"), List.of("world"));
    }

    @Test
    void testParseQueryOneEmptyValue() {
        String query = "name=hello&name2=";
        Map<String, List<String>> param = ParserQuery.parseQuery(query);
        assertIterableEquals(param.get("name"), List.of("hello"));
        assertIterableEquals(param.get("name2"), List.of(""));
    }

    @Test
    void testParseQueryOneEmptyKey() {
        String query = "name=hello&=world";
        Map<String, List<String>> param = ParserQuery.parseQuery(query);
        assertIterableEquals(param.get("name"), List.of("hello"));
        assertIterableEquals(param.get(""), List.of("world"));
    }
}
