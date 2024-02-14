package bg.sofia.uni.fmi.mjt.pharmatree.api.handler.logic;

public class HandlerFactory {
    public static Handler of(String method) {
        return switch (TypeHandler.parseMethodType(method)) {
            case Get -> new GetHandler();
            case Post -> new PostHandler();
            case Put -> new PutHandler();
            case Delete -> new DeleteHandler();
            case Patch -> new PatchHandler();
        };
    }
}
