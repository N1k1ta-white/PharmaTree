package bg.sofia.uni.fmi.mjt.pharmatree.api.handler.logic;

import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.StorageFactory;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.ParserQuery;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class GetHandler implements Handler {
    // THAT'S OK
    @Override
    public void execute(HttpExchange exchange) {
        Type type = Handler.getType(exchange);
        Map<String, List<String>> params = ParserQuery.parseQuery(exchange.getRequestURI().getQuery());
        String res = gson.toJson(StorageFactory.of(type).get(params), type);
        try (OutputStream output = exchange.getResponseBody()) {
            exchange.sendResponseHeaders(200, res.getBytes(StandardCharsets.UTF_8).length);
            output.write(res.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new UncheckedIOException("Unchecked IOException in getHandler!", e);
        }
    }
}