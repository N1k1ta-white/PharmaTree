package bg.sofia.uni.fmi.mjt.pharmatree.api.storage;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ServerException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.converter.DrugConverter;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.Drug;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.DrugParameters;
import bg.sofia.uni.fmi.mjt.pharmatree.api.testHelper.TestHelper;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DrugStorageTest {
    private static Gson gson = new Gson();

    @BeforeAll
    static void init() throws ServerException {
        TestHelper.setPathsForStorages();
    }

    @Test
    void testIsSingleton() throws ServerException {
        DrugStorage test = DrugStorage.getInstance();
        assertEquals(test.hashCode(), DrugStorage.getInstance().hashCode());
    }

    @Test
    void testAdd() throws ServerException, ClientException {
        Storage storage = DrugStorage.getInstance();
        DrugConverter conv = new DrugConverter();
        String json = """
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
                         }""";
        storage.add(json);
        String resJson = storage.get(TestHelper.getParamsByObject(conv.parseJson(json)));
        assertEquals(gson.fromJson(resJson, new TypeToken<List<Drug>>() {}.getType()), List.of(conv.parseJson(json)));
    }

    @Test
    void testEdit() throws ServerException, ClientException {
        Storage storage = DrugStorage.getInstance();
        DrugConverter conv = new DrugConverter();
        String json = """
                         {
                            "name":"testName",
                            "company":"TESTMed",
                            "country":"Ukraine",
                            "properties":[
                               "test1",
                               "test2"
                            ],
                            "cost":7,
                            "weight":10
                         }""";
        storage.add(json);
        String resJson = storage.get(TestHelper.getParamsByObject(conv.parseJson(json)));
        List<Drug> drug = gson.fromJson(resJson, new TypeToken<List<Drug>>() {}.getType());
        int id = drug.getFirst().id();
        Map<String, List<String>> param = new HashMap<>();
        param.put(DrugParameters.Cost.getValue(), List.of("500"));
        param.put(DrugParameters.Properties.getValue(), List.of("test1"));
        param.put(DrugParameters.Name.getValue(), List.of("tet"));
        storage.edit(id, param);
        Map<String, List<String>> search = new HashMap<>();
        search.put(DrugParameters.Id.getValue(), List.of(String.valueOf(id)));
        String json2 =  """
                         {
                            "name":"tet",
                            "company":"TESTMed",
                            "country":"Ukraine",
                            "properties":[
                               "test1"
                            ],
                            "cost":500,
                            "weight":10
                         }""";
        assertIterableEquals(gson.fromJson(storage.get(search), new TypeToken<List<Drug>>() {}.getType()),
                List.of(conv.parseJson(json2)));
    }

    @Test
    void testEditException() throws ServerException {
        Storage storage = DrugStorage.getInstance();
        assertThrows(ClientException.class, () -> storage.edit(-1, new HashMap<>()));
    }

    @Test
    void testDeleteException() throws ServerException {
        Storage storage = DrugStorage.getInstance();
        assertThrows(ClientException.class, () -> storage.delete(-1));
    }

    @Test
    void testDeleteProp() throws ServerException, ClientException {
        PropertyStorage propertyStorage = PropertyStorage.getInstance();
        DrugStorage drugStorage = DrugStorage.getInstance();
        propertyStorage.delete(2);
        Map<String, List<String>> param = new HashMap<>();
        param.put(DrugParameters.Properties.getValue(), List.of("test2"));
        assertThrows(ClientException.class, () -> drugStorage.get(param));
    }

    @Test
    void testDelete() throws ServerException, ClientException {
        Storage storage = DrugStorage.getInstance();
        DrugConverter conv = new DrugConverter();
        DrugConverter converter = new DrugConverter();
        String json = """
                         {
                            "name":"testName100",
                            "company":"TESTMed",
                            "country":"Bulgaria",
                            "properties":[
                               "test1",
                               "test2"
                            ],
                            "cost":7,
                            "weight":10
                         }""";
        storage.add(json);
        Map<String, List<String>> param = TestHelper.getParamsByObject(converter.parseJson(json));
        storage.get(param);
        List<Drug> res = gson.fromJson(storage.get(param), new TypeToken<List<Drug>>() {}.getType());
        assertEquals(1, res.size());
        assertEquals(conv.parseJson(json), res.getFirst());
        int id = res.getFirst().id();
        storage.delete(id);
        param.clear();
        param.put(DrugParameters.Id.getValue(), List.of(String.valueOf(id)));
        assertThrows(ClientException.class, () -> storage.get(param));
    }

    @Test
    void testReplace() throws ServerException, ClientException {
        Storage storage = DrugStorage.getInstance();
        DrugConverter conv = new DrugConverter();
        String json = """
                         {
                            "name":"testName1000",
                            "company":"TESTMed",
                            "country":"Bulgaria",
                            "properties":[
                               "test1",
                               "test2"
                            ],
                            "cost":7,
                            "weight":10
                         }""";
        storage.replace(4, json);
        Map<String, List<String>> param = new HashMap<>();
        param.put(DrugParameters.Id.getValue(), List.of(String.valueOf(4)));
        List<Drug> dr = gson.fromJson(storage.get(param), new TypeToken<List<Drug>>() {}.getType());
        assertEquals(dr.size(), 1);
        assertEquals(conv.parseJson(json), dr.getFirst());
    }

    @Test
    void testReplaceException() {
        assertThrows(ClientException.class, () -> DrugStorage.getInstance().replace(-1, ""));
    }

    @Test
    void testReplaceOrAdd() throws ServerException, ClientException {
        Storage storage = DrugStorage.getInstance();
        DrugConverter conv = new DrugConverter();
        String json = """
                         {
                            "name":"testName1000",
                            "company":"TESTMed3000",
                            "country":"Bulgaria",
                            "properties":[
                               "test1",
                               "test2"
                            ],
                            "cost":7,
                            "weight":10
                         }""";
        StatusCode res = storage.replaceOrAdd(-1, json);
        assertEquals(res, StatusCode.Created);
        Map<String, List<String>> param = TestHelper.getParamsByObject(conv.parseJson(json));
        List<Drug> drugList = gson.fromJson(storage.get(param), new TypeToken<List<Drug>>() {}.getType());
        int id = drugList.getFirst().id();
        param.clear();
        param.put(DrugParameters.Id.getValue(), List.of(String.valueOf(id)));
        List<Drug> dr = gson.fromJson(storage.get(param), new TypeToken<List<Drug>>() {}.getType());
        assertEquals(dr.size(), 1);
        assertEquals(conv.parseJson(json), dr.getFirst());

        String json2 = """
                         {
                            "name":"testName1000",
                            "company":"TESTMed3000",
                            "country":"Ukraine",
                            "properties":[
                               "test1"
                            ],
                            "cost":7,
                            "weight":10
                         }""";
        StatusCode res2 = storage.replaceOrAdd(id, json2);
        assertEquals(res2, StatusCode.OK);
    }
}
