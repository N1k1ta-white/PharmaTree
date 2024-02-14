package bg.sofia.uni.fmi.mjt.pharmatree.api.handler.logic;

import bg.sofia.uni.fmi.mjt.pharmatree.api.items.Item;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.Copyable;
import bg.sofia.uni.fmi.mjt.pharmatree.api.items.drug.Drug;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.Storage;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.StorageFactory;
import com.sun.net.httpserver.HttpExchange;

import java.lang.reflect.Type;

public final class DeleteHandler extends HandlerWithInputReader {
    // HERE HAVE THE PROBLEM
    // Have done only storage with drugs DrugStorage, DrugFilter, DrugParameters, DrugParser, DrugEditor, BaseStorage
    @Override
    public void execute(HttpExchange exchange) {
        Type type = Handler.getType(exchange);
        Storage<? super Item> storage = StorageFactory.of(type);
        storage.delete(super.getObject(exchange, type)); // here problem
        // I tried to do with Object
    }
}