package bg.sofia.uni.fmi.mjt.pharmatree.api.handler.logic;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ServerException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.Role;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public interface Handler {
    int POS_OF_TYPE = 1;
    int COUNT_OF_PARTS = 3;
    String SEPARATOR = "/";
    static String getType(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        String[] arr = path.split(SEPARATOR, COUNT_OF_PARTS);
        return arr[POS_OF_TYPE];
    }

    static void writeResponse(HttpExchange exchange, StatusCode code) throws IOException {
        exchange.sendResponseHeaders(code.getStatusCode(), code.getMessage().getBytes(StandardCharsets.UTF_8).length);
        exchange.getResponseBody().write(code.getMessage().getBytes(StandardCharsets.UTF_8));
        exchange.getResponseBody().close();
    }

    static void writeResponse(HttpExchange exchange, StatusCode code, String response) throws IOException {
        exchange.sendResponseHeaders(code.getStatusCode(), response.getBytes(StandardCharsets.UTF_8).length);
        exchange.getResponseBody().write(response.getBytes(StandardCharsets.UTF_8));
        exchange.getResponseBody().close();
    }

    void execute(HttpExchange exchange, Role auth) throws ServerException, ClientException;
}
