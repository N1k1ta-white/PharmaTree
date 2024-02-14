package bg.sofia.uni.fmi.mjt.pharmatree.api.storage;

import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.User;

import java.nio.file.Path;
import java.util.concurrent.CopyOnWriteArrayList;

public final class UserStorage extends BaseStorage<User> {

    private static final Path PATH_TO_DB = Path.of("src/bg/sofia/uni/fmi/mjt/pharmatree/api/user.csv");
    private static UserStorage instance;

    static {
        instance = null;
    }

    private UserStorage() {
        super(new CopyOnWriteArrayList<>(), new UserFilter(), new UserEditor(), new UserParser(), PATH_TO_DB);
        instance = this;
    }

    public static synchronized UserStorage getInstance() {
        if (instance == null) {
            return new UserStorage();
        }
        return instance;
    }
}
