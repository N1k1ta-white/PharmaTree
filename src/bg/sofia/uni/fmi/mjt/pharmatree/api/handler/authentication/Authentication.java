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
        if (exchange.getRequestHeaders().get(UserProperty.USER_ID.getValue()) == null ||
                exchange.getRequestHeaders().get(UserProperty.USER_ID.getValue()).isEmpty()) {
            return Role.UNREGISTERED;
        } else if (exchange.getRequestHeaders().get(UserProperty.USER_ID.getValue()).size() > 1) {
            throw new ClientException(StatusCode.BAD_REQUEST, "Requires only one userId");
        }
        return UserStorage.getInstance().getRoleByUserId(exchange.getRequestHeaders()
                .get(UserProperty.USER_ID.getValue()).getFirst());
    }
}
