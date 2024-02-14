package bg.sofia.uni.fmi.mjt.pharmatree.api.handler.logic;

import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.StorageFactory;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.ParserQuery;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Map;

public class GetHandler implements Handler {

    @Override
    public void execute(HttpExchange exchange) {
        try {
            Map<String, List<String>> params = ParserQuery.parseQuery(exchange.getRequestURI().getQuery());
            String res = StorageFactory.of(Handler.getType(exchange)).get(params);
            Handler.writeResponse(exchange, ACCEPT_CODE, res);
            // TODO: exceptions
        } catch (IOException e) {
            throw new UncheckedIOException("Unchecked IOException in getHandler!", e);
        }
    }
}