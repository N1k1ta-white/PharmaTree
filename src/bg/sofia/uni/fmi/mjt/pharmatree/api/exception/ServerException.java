package bg.sofia.uni.fmi.mjt.pharmatree.api.exception;

import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;

public class ServerException extends StatusBaseException {
    public ServerException(StatusCode code) {
        super(code);
    }

    public ServerException(StatusCode code, Exception e) {
        super(code, e);
    }
}
