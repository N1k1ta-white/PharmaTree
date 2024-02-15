package bg.sofia.uni.fmi.mjt.pharmatree.api.exception;

import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;

public class ClientException extends StatusBaseException {
    public ClientException(StatusCode code) {
        super(code);
    }

    public ClientException(StatusCode code, Exception e) {
        super(code, e);
    }
}
