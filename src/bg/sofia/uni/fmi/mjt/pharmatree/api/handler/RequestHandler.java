package bg.sofia.uni.fmi.mjt.pharmatree.api.handler;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ServerException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.handler.authentication.Authentication;
import bg.sofia.uni.fmi.mjt.pharmatree.api.handler.logic.Handler;
import bg.sofia.uni.fmi.mjt.pharmatree.api.handler.logic.HandlerFactory;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class RequestHandler implements HttpHandler {
    /**
     * Handle the given request and generate an appropriate response.
     * See {@link HttpExchange} for a description of the steps
     * involved in handling an exchange.
     *
     * @param exchange the exchange containing the request from the
     *                 client and used to send the response
     * @throws NullPointerException if exchange is {@code null}
     * @throws IOException          if an I/O error occurs
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            Handler handler = HandlerFactory.of(exchange.getRequestMethod());
            handler.execute(exchange, Authentication.auth(exchange));
        } catch (ClientException e) {
            Handler.writeResponse(exchange, e.getCode());
        } catch (ServerException e) {
            Handler.writeResponse(exchange, e.getCode());
        }
    }
}
