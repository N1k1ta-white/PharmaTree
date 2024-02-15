package bg.sofia.uni.fmi.mjt.pharmatree.api.items.converter;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ServerException;
import com.google.gson.Gson;

import java.util.List;

public interface ItemConverter<E> {

    String SEPARATOR = ";";
    String SEPARATOR_ARRAY = ",";
    Gson GSON = new Gson();
    E parseLine(String line) throws ServerException;

    E parseJson(String json);

    String convertListToJson(List<E> obj);
}