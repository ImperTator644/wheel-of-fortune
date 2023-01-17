package handlers.players;

import handlers.Handler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

@Slf4j
public class Player extends Handler {

    private static final String START_GAME = "start_game";
    public Player(Socket socket) {
        super(socket);
        log.info("Connected to server");
        this.listenForMessage();
        this.test();
    }

    private void test(){
        Scanner scanner = new Scanner(System.in);
        while(socket.isConnected()){
            try{
                this.writer.write(scanner.nextLine());
                this.writer.newLine();
                this.writer.flush();
                log.info("Question received");
            }
            catch (IOException e){
                log.error("Error reading question {}", e.getMessage());
                closeEverything();
            }
        }
    }

    private void listenForMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msg;

                while(socket.isConnected()){
                    try {
                        msg = reader.readLine();
                        System.out.println(msg);
                    } catch (IOException e) {
                        log.error("Error receiving message from other clients");
                    }
                }
            }
        }).start();
    }
}
