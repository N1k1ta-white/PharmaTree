package bg.sofia.uni.fmi.mjt.pharmatree.api.storage;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.DatabaseException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ServerException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.Drug;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.converter.DrugConverter;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.DrugParameters;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property.PropertyController;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.Role;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.editor.DrugEditor;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.filter.DrugFilter;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.CsvSeparator;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public final class DrugStorage extends BaseStorage<Drug> {

    private static String defaultPath = "drugs.csv";
    private static DrugStorage instance;

    static {
        instance = null;
    }

    private void checkProperties() {
        storage.stream().parallel().forEach(drug -> {
            if (drug.properties().isEmpty()) {
                storage.remove(drug);
            }
        });
    }

    private DrugStorage() throws ServerException {
        super(new CopyOnWriteArrayList<>(), new DrugFilter(), new DrugEditor(), new DrugConverter(),
                Path.of(defaultPath));
        instance = this;
        checkProperties();
    }

    @Override
    protected String getFirstLine() {
        return Arrays.stream(DrugParameters.values()).map(DrugParameters::getValue)
                .collect(Collectors.joining(CsvSeparator.getSeparator()));
    }

    public static synchronized DrugStorage getInstance() throws ServerException {
        if (instance == null) {
            PropertyStorage.getInstance();
            return new DrugStorage();
        }
        return instance;
    }

    public static void setPathToDb(String newPathToDb) {
        defaultPath = newPathToDb;
        if (instance != null) {
            throw new DatabaseException("You can't edit path during of working of DB");
        }
    }

    public synchronized void deletePropertyFromAllDrugs(PropertyController.Property property) {
        storage.forEach(drug -> {
            drug.properties().remove(property);
            if (drug.properties().isEmpty()) {
                storage.remove(drug);
            }
        });
    }

    @Override
    public int getSecurityLevelEdit() {
        return Role.Admin.getSecurityLevel();
    }

    @Override
    public int getSecurityLevelRead() {
        return Role.Registered.getSecurityLevel();
    }
}
