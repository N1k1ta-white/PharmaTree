package bg.sofia.uni.fmi.mjt.pharmatree.api.handler.logic;

import bg.sofia.uni.fmi.mjt.pharmatree.api.items.Item;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.Copyable;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.Drug;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.property.Property;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.user.User;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.ItemsType;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.lang.reflect.Type;

public sealed abstract class HandlerWithInputReader implements Handler
            permits PatchHandler, PostHandler, PutHandler, DeleteHandler {

    public Item getObject(HttpExchange exchange, Type type) {
        StringBuilder strBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))) {
            strBuilder.append(reader.readLine());
        } catch (IOException e) {
            throw new UncheckedIOException("Have some problems with reading from InputStream in HttpRequest: "
                    + exchange.getRequestURI(), e);
        }
        return gson.fromJson(strBuilder.toString(), type);
    }
}
