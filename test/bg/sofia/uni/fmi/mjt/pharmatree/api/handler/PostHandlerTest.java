package bg.sofia.uni.fmi.mjt.pharmatree.api.handler;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ServerException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.handler.logic.DeleteHandler;
import bg.sofia.uni.fmi.mjt.pharmatree.api.handler.logic.GetHandler;
import bg.sofia.uni.fmi.mjt.pharmatree.api.handler.logic.Handler;
import bg.sofia.uni.fmi.mjt.pharmatree.api.handler.logic.PostHandler;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.Role;
import bg.sofia.uni.fmi.mjt.pharmatree.api.testHelper.TestHelper;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PostHandlerTest {
    @BeforeAll
    static void init() {
        TestHelper.setPathsForStorages();
    }

    @Test
    void testExecute() throws ServerException, ClientException, IOException {
        String json = """
                {"name":"Steve","role":"registered"}""";
        HttpExchange exchange = Mockito.mock(HttpExchange.class);
        URI uri = mock(URI.class);
        OutputStream output = Mockito.mock(OutputStream.class);
        when(exchange.getRequestBody()).thenReturn(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)));
        when(exchange.getRequestURI()).thenReturn(uri);
        when(uri.getPath()).thenReturn("api/user");
        when(exchange.getResponseBody()).thenReturn(output);
        Handler request = new PostHandler();
        String res = "Created";
        request.execute(exchange, Role.Admin);
        verify(output).write(res.getBytes(StandardCharsets.UTF_8));
    }
}
