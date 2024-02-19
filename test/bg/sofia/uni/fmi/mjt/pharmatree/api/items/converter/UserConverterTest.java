package bg.sofia.uni.fmi.mjt.pharmatree.api.items.converter;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ServerException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.Role;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.User;
import bg.sofia.uni.fmi.mjt.pharmatree.api.testHelper.TestHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserConverterTest {
    private static UserConverter converter;

    @BeforeAll
    static void init() {
        converter = new UserConverter();
    }

    @Test
    void testParseJson() throws ClientException, ServerException {
        String json = TestHelper.getJsonOfObject(new User(1, "nik1", Role.UNREGISTERED, "aboba"), User.class);
        User test = converter.parseJson(json);
        assertEquals(test, new User(1, "nik1", Role.UNREGISTERED, "aboba"));
    }

    @Test
    void testParseJsonWithoutElementException() throws ClientException {
        String json = """
                {
                   "role":"unregistered"
                 }""";
        assertThrows(ClientException.class, () -> converter.parseJson(json));
        String json1 = """
                {
                   "name":"nik1"
                 }""";
        assertThrows(ClientException.class, () -> converter.parseJson(json1));
    }

    @Test
    void testParseLine() throws ClientException {
        String line = "2;nik1;registered;aboba";
        assertEquals(new User(1, "nik1", Role.REGISTERED, "aboba"), converter.parseLine(line));
    }
}
