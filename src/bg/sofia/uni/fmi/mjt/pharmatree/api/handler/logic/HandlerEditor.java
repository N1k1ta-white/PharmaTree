package bg.sofia.uni.fmi.mjt.pharmatree.api.handler.logic;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.ParserQuery;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Map;

public abstract sealed class HandlerEditor implements Handler
            permits PatchHandler, PostHandler, PutHandler, DeleteHandler {
    protected static final String QUERY_ID = "id";
    protected String getJson(HttpExchange exchange) {
        StringBuilder strBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                strBuilder.append(line);
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Have some problems with reading from InputStream in HttpRequest: "
                    + exchange.getRequestURI(), e);
        }
        return strBuilder.toString();
    }

    protected static Map<String, List<String>> getAndCheckParameters(HttpExchange exchange) throws ClientException {
        Map<String, List<String>> params = ParserQuery.parseQuery(exchange.getRequestURI().getQuery());
        if (!params.containsKey(QUERY_ID) && params.get(QUERY_ID).size() == 1) {
            throw new ClientException(StatusCode.Bad_Request, "You can't send more than one id for methods!");
        }
        return params;
    }
}
