package bg.sofia.uni.fmi.mjt.pharmatree.api.handler.logic;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ServerException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.ItemsType;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.StorageFactory;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public final class PostHandler extends HandlerWithInputReader {
    @Override
    public void execute(HttpExchange exchange) throws ClientException, ServerException {
        try {
            Handler.writeResponse(exchange,
                    StorageFactory.of(ItemsType.parseFromString(Handler.getType(exchange))).add(getJson(exchange)));
        } catch (IOException e) {
            throw new ServerException(StatusCode.Internal_Server_Error, e);
        }
    }
}
