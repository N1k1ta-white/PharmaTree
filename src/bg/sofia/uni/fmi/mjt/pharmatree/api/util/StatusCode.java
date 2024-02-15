package bg.sofia.uni.fmi.mjt.pharmatree.api.util;

public enum StatusCode {
    Bad_Request(400, "Bad Request"),
    Unauthorized(401, "Unauthorized"),
    Forbidden(403, "Forbidden"),
    Not_Found(404, "Not Found"),
    Created(201, "Created"),
    OK(200, "OK"),
    Internal_Server_Error(500, "Internal Server Error"),
    Service_Unavailable(503, "Service Unavailable"),
    Accepted(202, "Accepted");

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
