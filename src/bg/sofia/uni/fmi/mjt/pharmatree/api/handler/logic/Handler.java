package bg.sofia.uni.fmi.mjt.pharmatree.api.handler.logic;

import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.Drug;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property.Property;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.User;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.ItemsType;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.lang.reflect.Type;

public interface Handler {
    int POS_OF_TYPE = 1;
    int COUNT_OF_PARTS = 3;
    String SEPARATOR = "/";
    Gson gson = new Gson();
    static Type getType(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        String[] arr = path.split(SEPARATOR, COUNT_OF_PARTS);
        return switch (ItemsType.parseFromString(arr[POS_OF_TYPE])) {
            case User -> User.class;
            case Property -> Property.class;
            case Drug -> Drug.class;
            case null -> throw new IllegalArgumentException("Invalid type of Object: " + arr[POS_OF_TYPE]);
        };
    }

    void execute(HttpExchange exchange);
}
