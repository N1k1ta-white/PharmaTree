package bg.sofia.uni.fmi.mjt.pharmatree.api.storage;

import bg.sofia.uni.fmi.mjt.pharmatree.api.items.Item;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.Copyable;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.Drug;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property.Property;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.User;

import java.lang.reflect.Type;

public class StorageFactory {
    public static Storage of(String type) {
        return switch(ItemsType.parseFromString(type)) {
            case User -> UserStorage.getInstance();
            case Drug -> DrugStorage.getInstance();
            case Property -> PropertyStorage.getInstance();
        };
    }

    public static Storage of(Type type) {
        return switch(type.getTypeName()) {
            case "User" -> UserStorage.getInstance();
            case "Drug" -> DrugStorage.getInstance();
            case "Property" -> PropertyStorage.getInstance();
            default -> throw new IllegalStateException("Unexpected value in StorageFactory: " + type.getTypeName());
        };
    }
}
