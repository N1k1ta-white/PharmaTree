package bg.sofia.uni.fmi.mjt.pharmatree.api.storage;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.DatabaseException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ServerException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.converter.UserConverter;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.Role;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.User;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.UserProperty;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.editor.UserEditor;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.faststorage.FastStorage;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.logic.filter.UserFilter;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.CsvSeparator;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public final class UserStorage extends BaseStorage<User> {

    private static String defaultPath = "users.csv";
    private static UserStorage instance;

    private Map<String, Role> userIdStorage;

    static {
        instance = null;
    }

    private UserStorage() throws ServerException {
        super(new FastStorage<>(), new UserFilter(), new UserEditor(), new UserConverter(),
                Path.of(defaultPath));
        instance = this;
    }

    @Override
    protected String getFirstLine() {
        return Arrays.stream(UserProperty.values()).map(UserProperty::getValue)
                .collect(Collectors.joining(CsvSeparator.getSeparator()));
    }

    public static synchronized UserStorage getInstance() throws ServerException {
        if (instance == null) {
            return new UserStorage();
        }
        return instance;
    }

    public static void setPathToDb(String newPathToDb) {
        defaultPath = newPathToDb;
        if (instance != null) {
            throw new DatabaseException("You can't edit path during of working of DB");
        }
    }

    public Role getRoleByUserId(String userId) throws ClientException {
        return storage.getStream().parallel().filter(elem -> elem.userId().equals(userId)).findAny().orElseThrow(
                () -> new ClientException(StatusCode.BAD_REQUEST, "Incorrect userId")
        ).role();
    }

    public boolean isUserIdExist(String userId) {
        return storage.getStream().parallel().anyMatch(user -> user.userId().equals(userId));
    }

    @Override
    public int getSecurityLevelEdit() {
        return Role.ADMIN.getSecurityLevel();
    }

    @Override
    public int getSecurityLevelRead() {
        return Role.ADMIN.getSecurityLevel();
    }
}
