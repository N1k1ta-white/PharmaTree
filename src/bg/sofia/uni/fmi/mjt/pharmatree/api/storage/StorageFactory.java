package bg.sofia.uni.fmi.mjt.pharmatree.api.storage;

public class StorageFactory {
    public static Storage<?> of(String type) {
        return switch(ItemsType.parseFromString(type)) {
            case User -> UserStorage.getInstance();
            case Drug -> DrugStorage.getInstance();
            case Property -> PropertyStorage.getInstance();
        };
    }
}
