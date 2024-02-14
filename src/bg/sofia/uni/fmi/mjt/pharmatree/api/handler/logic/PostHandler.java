package bg.sofia.uni.fmi.mjt.pharmatree.api.handler.logic;

import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.StorageFactory;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.UncheckedIOException;

public final class PostHandler extends HandlerWithInputReader {
    @Override
    public void execute(HttpExchange exchange) {
        try {
            StorageFactory.of(Handler.getType(exchange)).add(getJson(exchange));
            Handler.writeResponse(exchange, ACCEPT_CODE, ACCEPT);
        } catch (IOException e) {
            throw new UncheckedIOException("Unchecked IOException in PostHandler!", e);
        }
    }
}
