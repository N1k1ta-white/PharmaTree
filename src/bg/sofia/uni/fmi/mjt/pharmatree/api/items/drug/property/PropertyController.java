package bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property;

import bg.sofia.uni.fmi.mjt.pharmatree.api.items.Copyable;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.Identifiable;
import bg.sofia.uni.fmi.mjt.pharmatree.api.util.CsvSeparator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PropertyController {
    private static Map<String, Property> storage;
    private static Property none;

    static {
        none = new Property(-1, "none", "none", List.of());
        storage = new HashMap<>();
    }

    public static Property getEmptyProperty() {
        return none;
    }

    public static synchronized PropertyController.Property createProperty(int id, String name, String description,
                                                                          List<String> allergies) {
        if (!storage.containsKey(name)) {
            storage.put(name, new Property(id, name, description, allergies));
        }
        return storage.get(name);
    }

    public static Property getProperty(String name) {
        if (storage.containsKey(name)) {
            return storage.get(name);
        }
        return none;
    }

    public static class Property implements Copyable<Property>, Identifiable {
        private int id;
        private String name;
        private String description;
        private List<String> allergies;

        private Property(int id, String name, String description, List<String> allergies) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.allergies = allergies;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null || getClass() != object.getClass()) return false;
            Property property = (Property) object;
            return Objects.equals(name, property.name);
        }

        @Override
        public String toString() {
            return id + CsvSeparator.getSeparator() + name + CsvSeparator.getSeparator() + description
                    + CsvSeparator.getSeparator() + String.join(CsvSeparator.getArraySeparator(), allergies);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }

        @Override
        public void copy(Property elem) {
            name = elem.name;
            description = elem.description;
            allergies = elem.allergies;
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
}
