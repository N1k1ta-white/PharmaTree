package bg.sofia.uni.fmi.mjt.pharmatree.api.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParserQuery {
    public static Map<String, List<String>> parseQuery(String query) {
        String[] elements = query.split("&");
        Map<String, List<String>> params = new HashMap<>();
        Arrays.stream(elements).forEach(element -> {
            String[] res = element.split("=", 2);
            params.putIfAbsent(res[0], new ArrayList<>());
            params.get(res[0]).add(res[1]);
        });
        return params;
    }
}
