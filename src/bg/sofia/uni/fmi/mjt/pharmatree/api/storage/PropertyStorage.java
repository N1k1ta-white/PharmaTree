package bg.sofia.uni.fmi.mjt.pharmatree.api.storage;

import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property.Property;

import java.nio.file.Path;
import java.util.concurrent.CopyOnWriteArrayList;

public final class PropertyStorage extends BaseStorage<Property> {
    private static final Path PATH_TO_DB = Path.of("src/bg/sofia/uni/fmi/mjt/pharmatree/api/properties.csv");
    private static PropertyStorage instance;

    static {
        instance = null;
    }

    private PropertyStorage() {
        super(new CopyOnWriteArrayList<>(), new PropertyFilter(), new PropertyEditor(), new PropertyParser(), PATH_TO_DB);
        instance = this;
    }

    public static synchronized PropertyStorage getInstance() {
        if (instance == null) {
            return new PropertyStorage();
        }
        return instance;
    }
}
