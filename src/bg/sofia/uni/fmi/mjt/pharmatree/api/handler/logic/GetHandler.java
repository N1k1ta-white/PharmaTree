package bg.sofia.uni.fmi.mjt.pharmatree.api.handler.logic;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ServerException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.Role;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.ItemsType;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.Storage;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.StorageFactory;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.ParserQuery;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GetHandler implements Handler {

    @Override
    public void execute(HttpExchange exchange, Role auth) throws ServerException, ClientException {
        try {
            Map<String, List<String>> params = ParserQuery.parseQuery(exchange.getRequestURI().getQuery());
            ItemsType type = ItemsType.parseFromString(Handler.getType(exchange));
            Storage storage = StorageFactory.of(type);
            if (auth.getSecurityLevel() < storage.getSecurityLevelRead()) {
                throw new ClientException(StatusCode.Forbidden, "You haven't required access level(get)!");
            }
            String res = storage.get(params);
            Handler.writeResponse(exchange, StatusCode.OK, res);
        } catch (IOException e) {
            throw new ServerException(StatusCode.Internal_Server_Error,
                    "Unexpected error in server during writing response(get)");
        }
    }
}