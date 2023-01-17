import handlers.server.Server;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

@Slf4j
public class ServerMain implements MainInterface{
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
            Server server = new Server(serverSocket, Executors.newFixedThreadPool(ROOM_COUNT));
            server.startServer();
        } catch (IOException e) {
            log.error("Problem creating a server {}", e.getMessage());
        }
    }
}
