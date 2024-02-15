package bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug;

import bg.sofia.uni.fmi.mjt.pharmatree.api.items.Copyable;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.Identifiable;

import java.util.List;
import java.util.Objects;

public class Drug implements Copyable<Drug>, Identifiable {
    private static final String SEPARATOR = ";";
    private static final String SEPARATOR_ARRAY = ",";
    private String name;
    private String company;
    private String country;
    private List<String> properties;
    private double cost;
    private double weight;
    private int id;

    public Drug(int id, String name, String company, String country, List<String> properties, double cost,
                double weight) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.country = country;
        this.properties = properties;
        this.cost = cost;
        this.weight = weight;
    }

    public void copy(Drug drug) {
        name = drug.name;
        company = drug.company;
        country = drug.country;
        properties = drug.properties;
        cost = drug.cost;
        weight = drug.weight;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Drug drug = (Drug) object;
        return Double.compare(cost, drug.cost) == 0 && Double.compare(weight, drug.weight) == 0
                && Objects.equals(name, drug.name) && Objects.equals(company, drug.company)
                && Objects.equals(country, drug.country) && Objects.equals(properties, drug.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, company, country, properties, cost, weight);
    }

    @Override
    public String toString() {
        return id + SEPARATOR + name + SEPARATOR + company + SEPARATOR + country + SEPARATOR
                + String.join(SEPARATOR_ARRAY, properties) + SEPARATOR + cost + SEPARATOR + weight;
    }

    public int id() {
        return id;
    }

    @Override
    public void setId(int newId) {
        id = newId;
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String company() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String country() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<String> properties() {
        return properties;
    }

    public void setProperties(List<String> properties) {
        this.properties = properties;
    }

    public double cost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double weight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
