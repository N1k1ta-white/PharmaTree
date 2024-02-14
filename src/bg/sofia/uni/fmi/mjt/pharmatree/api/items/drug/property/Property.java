package bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property;


// TODO: Flyweight

import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.Copyable;

import java.util.List;

public class Property implements Copyable<Property> {
    private String name;
    private String description;
    private List<String> allergies;

    @Override
    public void copy(Property elem) {
        name = elem.name;
        description = elem.description;
        allergies = elem.allergies;
    }

    public Property(String name, String description, List<String> allergies) {
        this.name = name;
        this.description = description;
        this.allergies = allergies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }
}
