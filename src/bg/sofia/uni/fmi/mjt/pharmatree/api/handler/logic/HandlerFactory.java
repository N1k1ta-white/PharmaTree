package bg.sofia.uni.fmi.mjt.pharmatree.api.handler.logic;

public class HandlerFactory {
    public static Handler of(String method) {
        return switch (method) {
            case "GET" -> new GetHandler();
            case "POST" -> new PostHandler();
            case "PUT" -> new PutHandler();
            case "DELETE" -> new DeleteHandler();
            case "PATCH" -> new PatchHandler();
            default -> throw new IllegalStateException("Unexpected value: " + method);
        };
    }
}
