package bg.sofia.uni.fmi.mjt.pharmatree.api.storage;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ServerException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.Role;
import bg.sofia.uni.fmi.mjt.pharmatree.api.testHelper.TestHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserStorageTest {
    @BeforeAll
    static void init() throws ServerException {
        TestHelper.setPathsForStorages();
    }
    @Test
    void testIsSingleton() throws ServerException {
        PropertyStorage test = PropertyStorage.getInstance();
        assertEquals(test.hashCode(), PropertyStorage.getInstance().hashCode());
    }

    @Test
    void testGetRoleByUserId() throws ServerException, ClientException {
        assertEquals(Role.ADMIN, UserStorage.getInstance().getRoleByUserId("admin"));
    }

    @Test
    void tesGetRoleByUserId() {
        assertThrows(ClientException.class, () -> UserStorage.getInstance().getRoleByUserId(""));
    }
}
