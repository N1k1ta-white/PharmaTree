package bg.sofia.uni.fmi.mjt.pharmatree.api.storage.editor;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.Role;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.User;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.UserProperty;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.editor.UserEditor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserEditorTest {
    public static User userExample;
    public static UserEditor editor;
    @BeforeAll
    static void beforeAll() {
        editor = new UserEditor();
        userExample = new User(7, "John", Role.REGISTERED, "324324324sfsf");
    }

    @Test
    void testEditElement() throws ClientException {
        User test = userExample;
        Map<String, List<String>> params = new HashMap<>();
        params.put(UserProperty.NAME.getValue(), List.of("new_name"));
        params.put(UserProperty.ROLE.getValue(), List.of("admin"));
        params.put(UserProperty.USER_ID.getValue(), List.of("qwerty"));
        editor.editElement(test, params);
        assertEquals(test.name(), "new_name");
        assertEquals(test.role(), Role.ADMIN);
        assertEquals(test.userId(), "qwerty");
    }

    @Test
    void testEditElementExceptionNull() throws ClientException {
        User test = userExample;
        Map<String, List<String>> params = new HashMap<>();
        params.put(UserProperty.NAME.getValue(), null);
        assertThrows(ClientException.class, () -> editor.editElement(test, params));
        params.clear();
        params.put(null, List.of("something"));
        assertThrows(ClientException.class, () -> editor.editElement(test, params));
        assertThrows(ClientException.class, () -> editor.editElement(test,null));
        assertThrows(ClientException.class, () -> editor.editElement(null,params));
    }

    @Test
    void testEditElementExceptionInvalidArgs() throws ClientException {
        User test = userExample;
        Map<String, List<String>> params = new HashMap<>();
        params.put(UserProperty.NAME.getValue() + "p", List.of("test"));
        assertThrows(ClientException.class, () -> editor.editElement(test, params));
        params.clear();
        params.put(UserProperty.ROLE.getValue() + "e", List.of("something"));
        assertThrows(ClientException.class, () -> editor.editElement(test, params));
    }

    @Test
    void testEditElementExceptionIdEdit() throws ClientException {
        User test = userExample;
        Map<String, List<String>> params = new HashMap<>();
        params.put(UserProperty.ID.getValue(), List.of("test"));
        assertThrows(ClientException.class, () -> editor.editElement(test, params));
    }
}
