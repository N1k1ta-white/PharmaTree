package bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug;

import bg.sofia.uni.fmi.mjt.pharmatree.api.items.Item;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Drug extends Item implements Copyable<Drug> {
    private static final String SEPARATOR = ";";
    private static final String SEPARATOR_ARRAY = ",";
    @SerializedName("name")
    private String name;
    @SerializedName("company")
    private String company;
    @SerializedName("country")
    private String country;
    @SerializedName("properties")
    private List<String> properties;
    @SerializedName("cost")
    private double cost;
    @SerializedName("weight")
    private double weight;

    public Drug(String name, String company, String country, List<String> properties, double cost, double weight) {
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
    public String toString() {
        return name + SEPARATOR + company + SEPARATOR + country + SEPARATOR + String.join(SEPARATOR_ARRAY, properties)
                + SEPARATOR + cost + SEPARATOR + weight;
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
