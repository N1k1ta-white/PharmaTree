package bg.sofia.uni.fmi.mjt.pharmatree.api.storage;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ServerException;

public class StorageFactory {
    public static Storage of(ItemsType type) throws ServerException {
        return switch(type) {
            case USER -> UserStorage.getInstance();
            case DRUG -> DrugStorage.getInstance();
            case PROPERTY -> PropertyStorage.getInstance();
        };
    }
}
