package bg.sofia.uni.fmi.mjt.pharmatree.api.storage;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ServerException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.testHelper.TestHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PropertyStorageTest {
    @BeforeAll
    static void init() {
        TestHelper.setPathsForStorages();
    }
    @Test
    void testIsSingleton() throws ServerException {
        PropertyStorage test = PropertyStorage.getInstance();
        assertEquals(test.hashCode(), PropertyStorage.getInstance().hashCode());
    }
}
