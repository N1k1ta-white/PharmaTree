package bg.sofia.uni.fmi.mjt.pharmatree.api.util;

public enum StatusCode {
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    CONFLICT(409, "Conflict"),
    CREATED(201, "Created"),
    OK(200, "OK"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),
    ACCEPTED(202, "Accepted");

    private final int statusCode;
    private final String message;

    StatusCode(int code, String message) {
        statusCode = code;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
