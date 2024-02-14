package bg.sofia.uni.fmi.mjt.pharmatree.api.handler.logic;

import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.Drug;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property.Property;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.User;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.ItemsType;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

public interface Handler {
    String ACCEPT = "OK";
    int ACCEPT_CODE = 200;
    int POS_OF_TYPE = 1;
    int COUNT_OF_PARTS = 3;
    String SEPARATOR = "/";
    static String getType(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        String[] arr = path.split(SEPARATOR, COUNT_OF_PARTS);
        return arr[POS_OF_TYPE];
    }

    static void writeResponse(HttpExchange exchange, int code, String text) throws IOException {
        exchange.sendResponseHeaders(code, text.getBytes(StandardCharsets.UTF_8).length);
        exchange.getResponseBody().write(text.getBytes(StandardCharsets.UTF_8));
        exchange.getResponseBody().close();
    }

    void execute(HttpExchange exchange);
}
