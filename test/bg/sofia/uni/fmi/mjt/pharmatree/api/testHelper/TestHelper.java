package bg.sofia.uni.fmi.mjt.pharmatree.api.testHelper;

import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.Drug;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.DrugParameters;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property.PropertyController;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property.PropertyParameters;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.User;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.UserProperty;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.DrugStorage;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.PropertyStorage;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.UserStorage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestHelper {
    private static boolean executed = false;
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void setPathsForStorages() {
        if (!executed) {
            executed = true;
            DrugStorage.setPathToDb("drugsTest.csv");
            PropertyStorage.setPathToDb("propertiesTest.csv");
            UserStorage.setPathToDb("usersTest.csv");
        }
    }

    public static String getJsonOfObject(Object obj, Type type) {
        return gson.toJson(obj, type);
    }

    public static Map<String, List<String>> getParamsByObject(Object obj) {
        Map<String, List<String>> res = new HashMap<>();
        switch (obj) {
            case Drug dr:
                res.put(DrugParameters.NAME.getValue(), List.of(dr.name()));
                res.put(DrugParameters.COMPANY.getValue(), List.of(dr.company()));
                res.put(DrugParameters.COUNTRY.getValue(), List.of(dr.country()));
                res.put(DrugParameters.PROPERTIES.getValue(), dr.properties().stream()
                        .map(PropertyController.Property::name).toList());
                res.put(DrugParameters.WEIGHT.getValue(), List.of(String.valueOf(dr.weight())));
                res.put(DrugParameters.COST.getValue(), List.of(String.valueOf(dr.cost())));
                break;
            case PropertyController.Property prop:
                res.put(PropertyParameters.NAME.getValue(), List.of(prop.name()));
                res.put(PropertyParameters.DESCRIPTION.getValue(), List.of(prop.description()));
                res.put(PropertyParameters.ALLERGIES.getValue(), prop.allergies());
                break;
            case User user:
                res.put(UserProperty.NAME.getValue(), List.of(user.name()));
                res.put(UserProperty.ROLE.getValue(), List.of(user.name()));
                break;
            case null, default:
                break;
        }
        return res;
    }

}
