package bg.sofia.uni.fmi.mjt.pharmatree.api.items.converter;

import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ClientException;
import bg.sofia.uni.fmi.mjt.pharmatree.api.exception.ServerException;

import java.util.List;

public interface ItemConverter<E> {
    E parseLine(String line) throws ClientException;

    E parseJson(String json) throws ClientException, ServerException;

    String convertListToJson(List<E> obj);
}
