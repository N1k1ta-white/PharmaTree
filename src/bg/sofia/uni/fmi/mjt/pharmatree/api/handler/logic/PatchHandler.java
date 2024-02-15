package bg.sofia.uni.fmi.mjt.pharmatree.api.handler.logic;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ServerException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.ItemsType;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.StorageFactory;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.ParserQuery;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public final class PatchHandler extends HandlerWithInputReader {
    @Override
    public void execute(HttpExchange exchange) throws ClientException, ServerException {
        try {
            Map<String, List<String>> params = ParserQuery.parseQuery(exchange.getRequestURI().getQuery());
            if (!params.containsKey(QUERY_ID) && params.get(QUERY_ID).size() == 1) {
                throw new ClientException(StatusCode.Bad_Request);
            }
            StorageFactory.of(ItemsType.parseFromString(Handler.getType(exchange)))
                    .edit(Integer.parseInt(params.get(QUERY_ID).getFirst()), params);
            Handler.writeResponse(exchange, StatusCode.OK);
        } catch (IOException e) {
            throw new ServerException(StatusCode.Internal_Server_Error, e);
        }
    }
}