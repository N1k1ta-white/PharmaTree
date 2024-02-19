package bg.sofia.uni.fmi.mjt.pharmatree.api.items.user;

import bg.sofia.uni.fmi.mjt.pharmatree.api.items.Copyable;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.Identifiable;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.Nameable;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.CsvSeparator;

import java.util.Objects;

public class User implements Copyable<User>, Identifiable, Nameable {
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
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        User user = (User) object;
        return Objects.equals(name, user.name) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, role);
    }

    public String userId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Role role() {
        return role;
    }

    @Override
    public String toString() {
        return id + CsvSeparator.getSeparator() + name
                + CsvSeparator.getSeparator() + role.getValue()
                + CsvSeparator.getSeparator() + userId;
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
