package bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property;

import bg.sofia.uni.fmi.mjt.pharmatree.api.items.Copyable;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.Identifiable;

import java.util.List;
import java.util.Map;

public class PropertyController {
    private static Map<String, Property> storage;

    public static synchronized PropertyController.Property createProperty(int id, String name, String description,
                                                                          List<String> allergies) {
        if (!storage.containsKey(name)) {
            storage.put(name, new Property(id, name, description, allergies));
            return storage.get(name);
        }
        // TODO: EXception
        return null;
    }

    public static Property getProperty(String name) {
        if (storage.containsKey(name)) {
            return storage.get(name);
        }
        // TODO: Exception
        return null;
    }

    public static class Property implements Copyable<Property>, Identifiable {
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

        private Property(int id, String name, String description, List<String> allergies) {
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
}
