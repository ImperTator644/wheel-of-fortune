package handlers.server.room;

import handlers.Handler;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

@Slf4j
public class PlayerHandler extends Handler implements Runnable {
    @Setter
    private List<PlayerHandler> competitors;

    public PlayerHandler(Socket socket) {
        super(socket);
        log.info("Player handler initialized");
    }

    @Override
    public void run() {
        String message;

        while(socket.isConnected()){
            try{
                message = this.reader.readLine();
                broadcastMessage(message);
            } catch (IOException e) {
                log.error("Error receiving message on the server");
            }
        }
    }

    private void broadcastMessage(String messageToSend){
        for(PlayerHandler player : competitors){
            try{
                player.writer.write(messageToSend);
                player.writer.newLine();
                player.writer.flush();
            } catch (IOException e) {
                log.error("Error sending message from server to player");
            }
        }
    }
}
