package bg.sofia.uni.fmi.mjt.pharmatree.api.storage;

import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.Drug;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.parser.DrugParser;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.editor.DrugEditor;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.filter.DrugFilter;

import java.nio.file.Path;
import java.util.concurrent.CopyOnWriteArrayList;

public final class DrugStorage extends BaseStorage<Drug> {

    private static final Path PATH_TO_DB = Path.of("src/bg/sofia/uni/fmi/mjt/pharmatree/api/drugs.csv");
    private static DrugStorage instance;

    static {
        instance = null;
    }

    private DrugStorage() {
        super(new CopyOnWriteArrayList<>(), new DrugFilter(), new DrugEditor(), new DrugParser(), PATH_TO_DB);
        instance = this;
    }

    public static synchronized DrugStorage getInstance() {
        if (instance == null) {
            return new DrugStorage();
        }
        return instance;
    }
}
