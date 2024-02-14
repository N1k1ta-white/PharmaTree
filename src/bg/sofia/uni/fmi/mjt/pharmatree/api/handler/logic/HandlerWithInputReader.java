package bg.sofia.uni.fmi.mjt.pharmatree.api.handler.logic;

import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;

public abstract sealed class HandlerWithInputReader implements Handler
            permits PatchHandler, PostHandler, PutHandler, DeleteHandler {
    protected static final String QUERY_ID = "id";
    protected String getJson(HttpExchange exchange) {
        StringBuilder strBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))) {
            strBuilder.append(reader.readLine());
        } catch (IOException e) {
            throw new UncheckedIOException("Have some problems with reading from InputStream in HttpRequest: "
                    + exchange.getRequestURI(), e);
        }
        return strBuilder.toString();
    }
}
