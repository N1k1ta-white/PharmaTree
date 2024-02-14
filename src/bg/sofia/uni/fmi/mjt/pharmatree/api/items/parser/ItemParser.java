package bg.sofia.uni.fmi.mjt.pharmatree.api.items.parser;

public interface ItemParser<E> {
    E of(String line);
}
