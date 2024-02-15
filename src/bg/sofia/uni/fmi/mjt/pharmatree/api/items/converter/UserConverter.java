package bg.sofia.uni.fmi.mjt.pharmatree.api.items.converter;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.Role;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.User;
import com.google.gson.JsonParser;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class UserConverter implements ItemConverter<User> {
    private static final AtomicInteger NUMBER;
    private static final int ID = 0;
    private static final int NAME = 1;
    private static final int ROLE = 2;
    private static final int USER_ID = 3;
    private static final String SECRET = "/,?q1w2e3|r4t5y6!./";

    static {
        NUMBER = new AtomicInteger(0);
    }

    @Override
    public User parseLine(String line) throws ClientException {
        String[] data = line.split(SEPARATOR);
        return new User(Integer.parseInt(data[ID]), data[NAME], Role.parseParameterFromString(data[ROLE]),
                data[USER_ID]);
    }

    @Override
    public User parseJson(String json) {
        User user = GSON.fromJson(JsonParser.parseString(json).getAsJsonObject(), User.class);
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            user.serUserId(Arrays.toString(digest.digest((SECRET + NUMBER.incrementAndGet() + SECRET)
                    .getBytes(StandardCharsets.UTF_8))));
            return user;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Unexpected exception with MessageDigest in UserConverter", e);
        }
    }

    @Override
    public String convertListToJson(List<User> obj) {
        return GSON.toJson(obj);
    }
}
