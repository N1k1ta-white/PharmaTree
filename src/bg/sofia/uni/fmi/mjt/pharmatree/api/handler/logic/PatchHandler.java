package bg.sofia.uni.fmi.mjt.pharmatree.api.handler.logic;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ServerException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.Role;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.ItemsType;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.Storage;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.StorageFactory;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public final class PatchHandler extends HandlerEditor {
    @Override
    public void execute(HttpExchange exchange, Role auth) throws ClientException, ServerException {
        try {
            ItemsType type = ItemsType.parseFromString(Handler.getType(exchange));
            Storage storage = StorageFactory.of(type);
            if (auth.getSecurityLevel() < storage.getSecurityLevelEdit()) {
                throw new ClientException(StatusCode.FORBIDDEN, "You haven't required access level(patch)!");
            }
            Map<String, List<String>> id = getAndCheckParameters(exchange);
            Gson gson = new Gson();
            Map<String, List<String>> params = gson.fromJson(getJson(exchange),
                    new TypeToken<Map<String, List<String>>>() { }.getType());
            storage.edit(Integer.parseInt(id.get(QUERY_ID).getFirst()), params);
            Handler.writeResponse(exchange, StatusCode.OK);
        } catch (IOException e) {
            throw new ServerException(StatusCode.INTERNAL_SERVER_ERROR,
                    "Unexpected error in server during writing response(patch)", e);
        }
    }
}