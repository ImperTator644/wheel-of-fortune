package handlers.server.room;

import lombok.extern.slf4j.Slf4j;

import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;

@Slf4j
public class PlayersRoom implements Runnable {
    private final Map<String, PlayerHandler> playerHandlers = new TreeMap();
    private final ExecutorService executorService;

    public PlayersRoom(List<Socket> players, ExecutorService executorService) {
        initializePlayerHandlers(players);
        this.executorService = executorService;
    }

    private void initializePlayerHandlers(List<Socket> players) {
        for (Socket socket : players) {
            PlayerHandler playerHandler = new PlayerHandler(socket);
            playerHandlers.put(playerHandler.getPlayerModel().getUsername(), playerHandler);
        }
    }

    @Override
    public void run() {
        log.info("Player room initialized");
        for (PlayerHandler playerHandler : playerHandlers.values()) {
            executorService.execute(playerHandler);
            log.info("Executor started playerHandler {}", playerHandler);
        }
        broadcastMessage("Siema witamy w gierce");
        for (PlayerHandler player : playerHandlers.values()) {
            broadcastMessage(player.getMessageFromClient());
        }
    }

    private void broadcastMessage(String messageToSend) {
        for (PlayerHandler player : playerHandlers.values()) {
            player.sendMessageToClient(messageToSend);
        }
    }
}
