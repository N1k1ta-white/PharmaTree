package bg.sofia.uni.fmi.mjt.pharmatree.api.items.user;

import bg.sofia.uni.fmi.mjt.pharmatree.api.items.Copyable;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.Identifiable;

public class User implements Copyable<User>, Identifiable {
    private int id;
    private String name;
    private Role role;
    private String userId;

    public User(int id, String name, Role role, String userID) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.userId = userID;
    }

    @Override
    public void copy(User elem) {
        name = elem.name;
        role = elem.role;
        userId = elem.userId;
    }

    @Override
    public int id() {
        return id;
    }

    public String userId() {
        return userId;
    }

    public void serUserId(String userId) {
        this.userId = userId;
    }

    public Role role() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setId(int newId) {
        id = newId;
    }

}
