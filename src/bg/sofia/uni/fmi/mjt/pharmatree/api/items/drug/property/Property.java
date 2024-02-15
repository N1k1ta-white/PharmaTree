package bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property;

import bg.sofia.uni.fmi.mjt.pharmatree.api.items.Copyable;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.Identifiable;

import java.util.List;

public class Property implements Copyable<Property>, Identifiable {
    private int id;
    private String name;
    private String description;
    private List<String> allergies;

    @Override
    public void copy(Property elem) {
        name = elem.name;
        description = elem.description;
        allergies = elem.allergies;
    }

    public Property(int id, String name, String description, List<String> allergies) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.allergies = allergies;
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String description() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> allergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public void setId(int newId) {
        id = newId;
    }
}
