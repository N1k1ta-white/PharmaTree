package bg.sofia.uni.fmi.mjt.pharmatree.api.storage;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ServerException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.converter.PropertyConverter;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property.PropertyController;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.Role;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.editor.PropertyEditor;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.filter.PropertyFilter;

import java.nio.file.Path;
import java.util.concurrent.CopyOnWriteArrayList;

public final class PropertyStorage extends BaseStorage<PropertyController.Property> {
    private static final Path PATH_TO_DB = Path.of("src/bg/sofia/uni/fmi/mjt/pharmatree/api/properties.csv");
    private static PropertyStorage instance;

    static {
        instance = null;
    }

    private PropertyStorage() throws ServerException {
        super(new CopyOnWriteArrayList<>(), new PropertyFilter(), new PropertyEditor(),
                new PropertyConverter(), PATH_TO_DB);
        instance = this;
    }

    public static synchronized PropertyStorage getInstance() throws ServerException {
        if (instance == null) {
            return new PropertyStorage();
        }
        return instance;
    }

    @Override
    public int getSecurityLevelRead() {
        return Role.Registered.getSecurityLevel();
    }

    @Override
    public int getSecurityLevelEdit() {
        return Role.Admin.getSecurityLevel();
    }
}
