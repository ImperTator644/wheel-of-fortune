package handlers.server.room;

import handlers.Handler;
import handlers.players.PlayerModel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;

@Slf4j
public class PlayerHandler extends Handler implements Runnable {
    @Getter
    private PlayerModel playerModel;

    public PlayerHandler(Socket socket) {
        super(socket);
        playerModel = new PlayerModel(getUserName());
        log.info("Player handler initialized");
    }

    @Override
    public void run() {
        while (socket.isConnected()) {

        }
        closeEverything();
    }

    private String getUserName() {
        try {
            return this.reader.readLine();
        } catch (IOException e) {
            log.error("Error receiving message on the server");
            closeEverything();
        }
        return "default";
    }

    public String getMessageFromClient() {
        try {
            return this.reader.readLine();
        } catch (IOException e) {
            log.error("Error receiving message on the server");
            closeEverything();
        }
        return null;
    }

    public void sendMessageToClient(String message) {
        try {
            this.getWriter().write(message);
            this.getWriter().newLine();
            this.getWriter().flush();
        } catch (IOException e) {
            log.error("Error sending message from server to player");
            closeEverything();
        }
    }
}
