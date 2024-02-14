package bg.sofia.uni.fmi.mjt.pharmatree.api.items.parser;

import com.google.gson.Gson;

import java.util.List;

public interface ItemConverter<E> {
    Gson GSON = new Gson();
    E parseLine(String line);

    E parseJson(String json);

    String convertListToJson(List<E> obj);
}
