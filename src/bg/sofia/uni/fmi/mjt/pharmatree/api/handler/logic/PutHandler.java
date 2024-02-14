package bg.sofia.uni.fmi.mjt.pharmatree.api.handler.logic;

import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.StorageFactory;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.ParserQuery;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Map;

public final class PutHandler extends HandlerWithInputReader {
    @Override
    public void execute(HttpExchange exchange) {
        try {
            Map<String, List<String>> params = ParserQuery.parseQuery(exchange.getRequestURI().getQuery());
            if (!params.containsKey(QUERY_ID) && params.get(QUERY_ID).size() == 1) {
                // TODO: exception
            }
            StorageFactory.of(Handler.getType(exchange)).replaceOrAdd(Integer.parseInt(params.get(QUERY_ID).getFirst()),
                    getJson(exchange));
            Handler.writeResponse(exchange, ACCEPT_CODE, ACCEPT);
        } catch (IOException e) {
            throw new UncheckedIOException("Unchecked IOException in PutHandler!", e);
        }
    }
}