package bg.sofia.uni.fmi.mjt.pharmatree.api.items;

public interface Nameable {
    String NAME_OF_ATTRIBUTE = "name";

    String name();

    void setName(String name);
}
