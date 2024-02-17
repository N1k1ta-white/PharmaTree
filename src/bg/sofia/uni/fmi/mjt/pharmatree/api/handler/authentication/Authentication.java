package bg.sofia.uni.fmi.mjt.pharmatree.api.handler.authentication;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ServerException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.Role;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.UserProperty;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.UserStorage;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;
import com.sun.net.httpserver.HttpExchange;

public class Authentication {
    public static Role auth(HttpExchange exchange) throws ServerException, ClientException {
        if (exchange.getRequestHeaders().get(UserProperty.UserId.getValue()) == null ||
                exchange.getRequestHeaders().get(UserProperty.UserId.getValue()).isEmpty()) {
            return Role.Unregistered;
        } else if (exchange.getRequestHeaders().get(UserProperty.UserId.getValue()).size() > 1) {
            throw new ClientException(StatusCode.Bad_Request, "Requires only one userId");
        }
        return UserStorage.getInstance().getRoleByUserId(exchange.getRequestHeaders()
                .get(UserProperty.UserId.getValue()).getFirst());
    }
}
