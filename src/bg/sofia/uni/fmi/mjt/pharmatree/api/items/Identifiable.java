package bg.sofia.uni.fmi.mjt.pharmatree.api.items;

public interface Identifiable {

    String NAME_OF_ATTRIBUTE = "id";

    int id();

    void setId(int newId);
}
