package bg.sofia.uni.fmi.mjt.pharmatree.api.exception;

import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;

public class ServerException extends StatusBaseException {
    public ServerException(StatusCode code, String description) {
        super(code, description);
    }

    public ServerException(StatusCode code, String description, Exception e) {
        super(code, description, e);
    }
}
