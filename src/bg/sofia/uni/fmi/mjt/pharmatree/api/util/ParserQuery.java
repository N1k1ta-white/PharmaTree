package bg.sofia.uni.fmi.mjt.pharmatree.api.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParserQuery {
    private static final String SEPARATOR = "&";
    private static final String SEPARATOR_NAME_VALUE = "=";
    private static final int MAX_SIZE = 2;

    public static Map<String, List<String>> parseQuery(String query) {
        String[] elements = query.split(SEPARATOR);
        Map<String, List<String>> params = new HashMap<>();
        Arrays.stream(elements).forEach(element -> {
            String[] res = element.split(SEPARATOR_NAME_VALUE, MAX_SIZE);
            params.putIfAbsent(res[0], new ArrayList<>());
            params.get(res[0]).add(res[1]);
        });
        return params;
    }
}
