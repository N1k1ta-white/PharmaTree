package bg.sofia.uni.fmi.mjt.pharmatree.api;

import bg.sofia.uni.fmi.mjt.pharmatree.api.handler.RequestHandler;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class APIServer {
    private static final int PORT = 8080;
    public static void main(String[] args) {
        try (ExecutorService executor = Executors.newCachedThreadPool()) {
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
            server.createContext("/", new RequestHandler());
            server.setExecutor(executor);
            server.start();
            System.out.println("Server started in port: " + PORT);
        } catch (IOException e) {
            throw new UncheckedIOException("The server has some problems in start", e);
        }
    }
}
