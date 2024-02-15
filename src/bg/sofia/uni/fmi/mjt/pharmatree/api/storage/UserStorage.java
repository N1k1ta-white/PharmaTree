package bg.sofia.uni.fmi.mjt.pharmatree.api.storage;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ServerException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.converter.UserConverter;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.Role;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.User;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.editor.UserEditor;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.filter.UserFilter;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;

import java.nio.file.Path;
import java.util.concurrent.CopyOnWriteArrayList;

public final class UserStorage extends BaseStorage<User> {

    private static final Path PATH_TO_DB = Path.of("src/bg/sofia/uni/fmi/mjt/pharmatree/api/user.csv");
    private static UserStorage instance;

    static {
        instance = null;
    }

    private UserStorage() throws ServerException {
        super(new CopyOnWriteArrayList<>(), new UserFilter(), new UserEditor(), new UserConverter(), PATH_TO_DB);
        instance = this;
    }

    public static synchronized UserStorage getInstance() throws ServerException {
        if (instance == null) {
            return new UserStorage();
        }
        return instance;
    }

    public Role getRoleByUserId(String userId) throws ClientException {
        return storage.stream().filter(elem -> elem.userId().equals(userId)).findAny().orElseThrow(
                () -> new ClientException(StatusCode.Bad_Request, "Incorrect userId")
        ).role();
    }

    @Override
    public int getSecurityLevelEdit() {
        return Role.Admin.getSecurityLevel();
    }

    @Override
    public int getSecurityLevelRead() {
        return Role.Admin.getSecurityLevel();
    }
}
