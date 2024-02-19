package bg.sofia.uni.fmi.mjt.pharmatree.api.storage;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.DatabaseException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ServerException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.converter.PropertyConverter;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property.PropertyController;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property.PropertyParameters;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.Role;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.editor.PropertyEditor;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.faststorage.FastStorage;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.filter.PropertyFilter;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.CsvSeparator;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public final class PropertyStorage extends BaseStorage<PropertyController.Property> {
    private static String defaultPath = "properties.csv";
    private static PropertyStorage instance;

    static {
        instance = null;
    }

    private PropertyStorage() throws ServerException {
        super(new FastStorage<>(), new PropertyFilter(), new PropertyEditor(),
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
        return Role.REGISTERED.getSecurityLevel();
    }

    @Override
    public int getSecurityLevelEdit() {
        return Role.ADMIN.getSecurityLevel();
    }

    @Override
    protected String getFirstLine() {
        return Arrays.stream(PropertyParameters.values()).map(PropertyParameters::getValue)
                .collect(Collectors.joining(CsvSeparator.getSeparator()));
    }

    @Override
    public synchronized void delete(int id) throws ClientException, ServerException {
        Optional<PropertyController.Property> prop = storage.getById(id);
        if (prop.isEmpty()) {
            throw new ClientException(StatusCode.NOT_FOUND, "Object for delete hasn't found");
        }
        DrugStorage.getInstance().deletePropertyFromAllDrugs(prop.get());
        DrugStorage.getInstance().cache.clear();
        synchronized (PropertyStorage.class) {
            if (!storage.remove(prop.get())) {
                throw new ClientException(StatusCode.NOT_FOUND, "Object almost has been deleted");
            }
        }
    }

    @Override
    public void edit(int id, Map<String, List<String>> edit) throws ClientException, ServerException {
        super.edit(id, edit);
        DrugStorage.getInstance().cache.clear();
    }

    @Override
    public StatusCode replaceOrAdd(int id, String json) throws ClientException, ServerException {
        StatusCode res = super.replaceOrAdd(id, json);
        DrugStorage.getInstance().cache.clear();
        return res;
    }

    @Override
    public void replace(int id, String json) throws ClientException, ServerException {
        super.replace(id, json);
        DrugStorage.getInstance().cache.clear();
    }
}
