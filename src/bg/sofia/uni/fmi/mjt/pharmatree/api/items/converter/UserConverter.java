package bg.sofia.uni.fmi.mjt.pharmatree.api.items.converter;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ServerException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.Role;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.User;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.UserStorage;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.CsvSeparator;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import java.security.SecureRandom;
import java.util.List;

public class UserConverter implements ItemConverter<User> {
    private static final int SIZE_USER_ID = 35;
    private static final int ID = 0;
    private static final int NAME = 1;
    private static final int ROLE = 2;
    private static final int USER_ID = 3;
    private static final int COUNT = 4;
    private static final Gson GSON;

    static {
        GSON = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public User parseLine(String line) throws ClientException {
        String[] data = line.split(CsvSeparator.getSeparator(), COUNT);
        return new User(Integer.parseInt(data[ID]), data[NAME], Role.parseParameterFromString(data[ROLE]),
                data[USER_ID]);
    }

    @Override
    public User parseJson(String json) throws ClientException, ServerException {
        User user = GSON.fromJson(JsonParser.parseString(json).getAsJsonObject(), User.class);
        if (user.role() == null || user.name() == null) {
            throw new ClientException(StatusCode.BAD_REQUEST, "Request hasn't enough data for creating a user");
        }
        StringBuilder userID = new StringBuilder();
        do {
            userID.delete(0, userID.capacity());
            new SecureRandom().ints(SIZE_USER_ID, 'a', 'z')
                    .mapToObj(Character::toChars).forEach(userID::append);
        } while (UserStorage.getInstance().isUserIdExist(userID.toString()));
        user.setUserId(userID.toString());
        return user;
    }

    @Override
    public String convertListToJson(List<User> obj) {
        return GSON.toJson(obj);
    }
}
