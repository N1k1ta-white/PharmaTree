package bg.sofia.uni.fmi.mjt.pharmatree.api.storage;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ServerException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DrugStorageTest {
    @Test
    void testIsSingleton() throws ServerException {
        DrugStorage test = DrugStorage.getInstance();
        assertEquals(test.hashCode(), DrugStorage.getInstance().hashCode());
    }

    @Test
    void testAdd() throws ServerException, ClientException {
        Storage storage = DrugStorage.getInstance();
        storage.add("""
                         {
                            "name":"testName",
                            "company":"TESTMed",
                            "country":"Ukraine",
                            "properties":[
                               "test1",
                               "test2"
                            ],
                            "cost":5,
                            "weight":10
                         }""");
    }

    @Test
    void testDelete() throws ServerException, ClientException {
        Storage storage = DrugStorage.getInstance();
        storage.delete(1);
        storage.flush();
    }
}
