package handlers.server.room;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;

@RequiredArgsConstructor
@Slf4j
public class PlayersRoom implements Runnable {
    private final List<Socket> players;
    private final List<PlayerHandler> playerHandlers = new LinkedList<>();
    private final ExecutorService executorService;

    @Override
    public void run() {
        log.info("Player room initialized");
        for (Socket socket : players) {
            PlayerHandler playerHandler = new PlayerHandler(socket);
            playerHandlers.add(playerHandler);
        }
        for(PlayerHandler playerHandler:playerHandlers){
            playerHandler.setCompetitors(playerHandlers);
            executorService.execute(playerHandler);
            log.info("Executor started playerHandler {}", playerHandler);
        }
    }
}
