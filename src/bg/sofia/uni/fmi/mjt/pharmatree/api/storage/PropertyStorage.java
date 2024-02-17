package bg.sofia.uni.fmi.mjt.pharmatree.api.storage;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.DatabaseException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ServerException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.converter.PropertyConverter;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property.PropertyController;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property.PropertyParameters;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.Role;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.editor.PropertyEditor;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.filter.PropertyFilter;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.CsvSeparator;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public final class PropertyStorage extends BaseStorage<PropertyController.Property> {
    private static String defaultPath = "properties.csv";
    private static PropertyStorage instance;

    static {
        instance = null;
    }

    private PropertyStorage() throws ServerException {
        super(new CopyOnWriteArrayList<>(), new PropertyFilter(), new PropertyEditor(),
                new PropertyConverter(), Path.of(defaultPath));
        instance = this;
    }

    public static synchronized PropertyStorage getInstance() throws ServerException {
        if (instance == null) {
            return new PropertyStorage();
        }
        return instance;
    }

    public static void setPathToDb(String newPathToDb) {
        defaultPath = newPathToDb;
        if (instance != null) {
            throw new DatabaseException("You can't edit path during of working of DB");
        }
    }

    @Override
    public int getSecurityLevelRead() {
        return Role.Registered.getSecurityLevel();
    }

    @Override
    public int getSecurityLevelEdit() {
        return Role.Admin.getSecurityLevel();
    }

    @Override
    protected String getFirstLine() {
        return Arrays.stream(PropertyParameters.values()).map(PropertyParameters::getValue)
                .collect(Collectors.joining(CsvSeparator.getSeparator()));
    }

    @Override
    public synchronized void delete(int id) throws ClientException, ServerException {
        Optional<PropertyController.Property> prop = filter.getElementById(storage.stream(), id);
        if (prop.isEmpty()) {
            throw new ClientException(StatusCode.Not_Found, "Object for editing hasn't found");
        }
        DrugStorage.getInstance().deletePropertyFromAllDrugs(prop.get());
    }
}
