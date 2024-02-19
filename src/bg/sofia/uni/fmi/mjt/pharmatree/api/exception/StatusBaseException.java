package bg.sofia.uni.fmi.mjt.pharmatree.api.exception;

import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;

public class StatusBaseException extends Exception {
    private final StatusCode code;
    private final String description;
    public StatusBaseException(StatusCode code, String description) {
        super("Status: " + code.getStatusCode() + System.lineSeparator() + "Message: " + description);
        this.code = code;
        this.description = description;
    }

    public StatusBaseException(StatusCode code, String description, Exception e) {
        super("Status: " + code.getStatusCode() + System.lineSeparator() + "Message: " + description, e);
        this.code = code;
        this.description = description;
    }

    public String getJsonMessage() {
        return "{" + System.lineSeparator() + "\t\"error\":\"error\"" + System.lineSeparator()
                + "\t\"message\":\"" + description + "\"" + System.lineSeparator() + "}";
    }

    public StatusCode getCode() {
        return code;
    }
}
