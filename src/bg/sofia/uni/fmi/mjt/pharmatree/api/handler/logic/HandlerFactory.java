package bg.sofia.uni.fmi.mjt.pharmatree.api.handler.logic;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;

public class HandlerFactory {
    public static Handler of(String method) throws ClientException {
        return switch (TypeHandler.parseMethodType(method)) {
            case Get -> new GetHandler();
            case Post -> new PostHandler();
            case Put -> new PutHandler();
            case Delete -> new DeleteHandler();
            case Patch -> new PatchHandler();
        };
    }
}
