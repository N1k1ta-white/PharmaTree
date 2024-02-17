package bg.sofia.uni.fmi.mjt.pharmatree.api.storage.filter;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.Role;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.User;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.UserProperty;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.filter.UserFilter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserFilterTest {
    static List<User> userList;
    static UserFilter filter;

    @BeforeAll
    static void beforeAll() {
        filter = new UserFilter();
        userList = List.of(new User(1, "nik1", Role.Unregistered, "12345"),
                new User(2, "nik2", Role.Registered, "123456"),
                new User(3, "nik3", Role.Registered, "1234567"),
                new User(4, "nikAdmin", Role.Admin, "12345678")
                );
    }

    @Test
    void testGetElementById() {
        assertEquals(filter.getElementById(userList.stream().parallel(), 1).get(),
                new User(1, "nik1", Role.Unregistered, "12345"));
        assertEquals(filter.getElementById(userList.stream().parallel(), 4).get(),
                new User(4, "nikAdmin", Role.Admin, "12345678"));
    }

    @Test
    void testFilterStreamsByParam() throws ClientException {
        Map<String, List<String>> param = new HashMap<>();
        param.put(UserProperty.Id.getValue(), List.of("3"));
        assertEquals(filter.filterStreamByParams(userList.stream().parallel(), param).toList(),
                List.of(new User(3, "nik3", Role.Registered, "1234567")));
        param.clear();
        param.put(UserProperty.Role.getValue(), List.of(Role.Registered.getValue()));
        assertEquals(List.of(
                        new User(2, "nik2", Role.Registered, "123456"),
                        new User(3, "nik3", Role.Registered, "1234567")
                ),
                filter.filterStreamByParams(userList.stream(), param).toList());
        param.put(UserProperty.Name.getValue(), List.of("nik2"));
        assertEquals(List.of(new User(2, "nik2", Role.Registered, "123456")),
                filter.filterStreamByParams(userList.stream(), param).toList());
    }

    @Test
    void testFilterStreamByParamsException() {
        Map<String, List<String>> param = new HashMap<>();
        param.put(UserProperty.Id.getValue() + "r", List.of("3"));
        assertThrows(ClientException.class,
                () -> filter.filterStreamByParams(userList.stream().parallel(), param).toList());
    }
}
