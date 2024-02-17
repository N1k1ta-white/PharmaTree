package bg.sofia.uni.fmi.mjt.pharmatree.api.items.user;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.StatusCode;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public enum Role {
    @SerializedName("unregistered")
    Unregistered(0, "unregistered"),
    @SerializedName("registered")
    Registered(1, "registered"),
    @SerializedName("admin")
    Admin(2, "admin");

    private final int securityLevel;
    private final String name;

    Role(int level, String name) {
        securityLevel = level;
        this.name = name;
    }

    public int getSecurityLevel() {
        return securityLevel;
    }

    public String getValue() {
        return name;
    }

    public static Role parseParameterFromString(String str) throws ClientException {
        return Arrays.stream(Role.values())
                .filter(elem -> elem.getValue().equals(str))
                .findAny()
                .orElseThrow(() -> new ClientException(StatusCode.Bad_Request, "Invalid value of Role"));
    }
}
