package bg.sofia.uni.fmi.mjt.pharmatree.api.handler;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ServerException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.handler.logic.GetHandler;
import bg.sofia.uni.fmi.mjt.pharmatree.api.handler.logic.Handler;
import bg.sofia.uni.fmi.mjt.pharmatree.api.handler.logic.PatchHandler;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.Role;
import bg.sofia.uni.fmi.mjt.pharmatree.api.testHelper.TestHelper;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PatchHandlerTest {
    @BeforeAll
    static void init() {
        TestHelper.setPathsForStorages();
    }
    @Test
    void testExecuteReplace() throws ServerException, ClientException, IOException {
        String json = """
                {"name":["Stevensonishe"],"role":["registered"]}""";
        HttpExchange exchange = Mockito.mock(HttpExchange.class);
        URI uri = mock(URI.class);
        OutputStream output = Mockito.mock(OutputStream.class);
        when(exchange.getRequestBody()).thenReturn(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)));
        when(exchange.getRequestURI()).thenReturn(uri);
        when(uri.getPath()).thenReturn("/api/user");
        when(uri.getQuery()).thenReturn("id=5");
        when(exchange.getResponseBody()).thenReturn(output);
        Handler request = new PatchHandler();
        String res = "OK";
        request.execute(exchange, Role.ADMIN);
        verify(output).write(res.getBytes(StandardCharsets.UTF_8));
        request = new GetHandler();
        res = """
                [
                  {
                    "id": 5,
                    "name": "Stevensonishe",
                    "role": "registered",
                    "userId": "iijifs"
                  }
                ]""";
        OutputStream output2 = Mockito.mock(OutputStream.class);
        when(uri.getPath()).thenReturn("/api/user");
        when(uri.getQuery()).thenReturn("id=5");
        when(exchange.getResponseBody()).thenReturn(output2);
        request.execute(exchange, Role.ADMIN);
        verify(output2).write(res.getBytes(StandardCharsets.UTF_8));
    }
}
