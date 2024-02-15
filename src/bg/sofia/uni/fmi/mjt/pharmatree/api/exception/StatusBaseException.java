package bg.sofia.uni.fmi.mjt.pharmatree.api.exception;

import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;

public class StatusBaseException extends Exception {
    private final StatusCode code;
    public StatusBaseException(StatusCode code) {
        super("Status: " + code.getStatusCode() + System.lineSeparator() + "Message: " + code.getMessage());
        this.code = code;
    }

    public StatusBaseException(StatusCode code, Exception e) {
        super("Status: " + code.getStatusCode() + System.lineSeparator() + "Message: " + code.getMessage(), e);
        this.code = code;
    }

    public StatusCode getCode() {
        return code;
    }
}
