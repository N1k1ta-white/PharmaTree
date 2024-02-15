package bg.sofia.uni.fmi.mjt.pharmatree.api.exception;

import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;

public class ClientException extends StatusBaseException {
    public ClientException(StatusCode code, String description) {
        super(code, description);
    }

    public ClientException(StatusCode code, String description, Exception e) {
        super(code, description, e);
    }
}
