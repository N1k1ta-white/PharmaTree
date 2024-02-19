package bg.sofia.uni.fmi.mjt.pharmatree.api.handler.logic;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;

public class HandlerFactory {
    public static Handler of(String method) throws ClientException {
        return switch (TypeHandler.parseMethodType(method)) {
            case GET -> new GetHandler();
            case POST -> new PostHandler();
            case PUT -> new PutHandler();
            case DELETE -> new DeleteHandler();
            case PATCH -> new PatchHandler();
        };
    }
}
