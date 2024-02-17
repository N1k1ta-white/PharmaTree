package bg.sofia.uni.fmi.mjt.pharmatree.api.handler.authentication;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ServerException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.Role;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.UserProperty;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.UserStorage;
import bg.sofia.uni.fmi.mjt.pharmatree.api.testHelper.TestHelper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AuthenticationTest {

    @BeforeAll
    static void init() {
        TestHelper.setPathsForStorages();
    }

    @Test
    void testAuth() throws ServerException, ClientException {
        HttpExchange exchange = Mockito.mock(HttpExchange.class);
        HashMap<String, List<String>> param = new HashMap<>();
        param.put(UserProperty.UserId.getValue(), List.of("admin"));
        Headers headers = new Headers(param);
        Mockito.when(exchange.getRequestHeaders()).thenReturn(headers);
        assertEquals(Role.Admin, Authentication.auth(exchange));
    }

    @Test
    void testAuthMultiplyId() {
        HttpExchange exchange = Mockito.mock(HttpExchange.class);
        HashMap<String, List<String>> param = new HashMap<>();
        param.put(UserProperty.UserId.getValue(), List.of("admin", "skdmd"));
        Headers headers = new Headers(param);
        Mockito.when(exchange.getRequestHeaders()).thenReturn(headers);
        assertThrows(ClientException.class, () -> Authentication.auth(exchange));
    }

    @Test
    void testAuthUnregistered() throws ServerException, ClientException {
        HttpExchange exchange = Mockito.mock(HttpExchange.class);
        HashMap<String, List<String>> param = new HashMap<>();
        Headers headers = new Headers(param);
        Mockito.when(exchange.getRequestHeaders()).thenReturn(headers);
        assertEquals(Role.Unregistered, Authentication.auth(exchange));
    }

    @Test
    void testAuthUnregisteredNull() throws ServerException, ClientException {
        HttpExchange exchange = Mockito.mock(HttpExchange.class);
        HashMap<String, List<String>> param = new HashMap<>();
        param.put(UserProperty.UserId.getValue(), List.of());
        Headers headers = new Headers(param);
        Mockito.when(exchange.getRequestHeaders()).thenReturn(headers);
        assertEquals(Role.Unregistered, Authentication.auth(exchange));
    }
}
