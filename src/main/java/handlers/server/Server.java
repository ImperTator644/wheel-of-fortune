package handlers.server;

import handlers.server.room.PlayersRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
@Slf4j
public class Server {
    private static final int PLAYERS_COUNT = 3;
    private final ServerSocket serverSocket;
    private final ExecutorService executorService;

    public void startServer(){
        log.info("Server started");
        try {
            while (!serverSocket.isClosed()) {
                List<Socket> playersList = new ArrayList<>(3);
                while(playersList.size() < 3) {
                    Socket socket = serverSocket.accept();
                    playersList.add(socket);
                    log.info("New player has connected");
                }

                PlayersRoom playersRoom = new PlayersRoom(playersList, Executors.newFixedThreadPool(PLAYERS_COUNT));
                executorService.execute(playersRoom);
                log.info("Executor started room {}", playersRoom);
            }
        }
        catch (IOException ioException){
            log.error("Error creating a connection with client {}", ioException.getMessage());
            closeEverything();
        }
    }

    private void closeEverything() {
        executorService.shutdown();
        try{
            serverSocket.close();
        }
        catch (IOException ioException){
            log.error("Error closing server socket {}", ioException.getMessage());
        }
    }
}
