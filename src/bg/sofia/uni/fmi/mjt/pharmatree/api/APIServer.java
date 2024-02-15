package bg.sofia.uni.fmi.mjt.pharmatree.api;

import bg.sofia.uni.fmi.mjt.pharmatree.api.handler.RequestHandler;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.ItemsType;
import bg.sofia.uni.fmi.mjt.pharmatree.api.storage.StorageFactory;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class APIServer {
    private static final int PORT = 8080;

    private static void cycle(HttpServer server) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (scanner.hasNext()) {
                if (scanner.nextLine().equals("shutdown")) {
                    server.stop(0);
                    boolean exception = false;
                    for (ItemsType type : ItemsType.values()) {
                        try {
                            StorageFactory.of(type).flush();
                        } catch (Exception e) {
                            exception = true;
                        }
                    }
                    if (exception) {
                        throw new IllegalArgumentException("ItemType has redundant value!");
                    }
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        try (ExecutorService executor = Executors.newCachedThreadPool()) {
            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
            server.createContext("/", new RequestHandler());
            server.setExecutor(executor);
            server.start();
            System.out.println("Server started in port: " + PORT);
            cycle(server);
        } catch (IOException e) {
            throw new UncheckedIOException("The server has some problems in start", e);
        }
    }
}
