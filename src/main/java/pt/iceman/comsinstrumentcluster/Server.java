package pt.iceman.comsinstrumentcluster;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pt.iceman.middleware.cars.SimpleCommand;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class Server extends Thread {
    private static final Logger logger = LogManager.getLogger(CommandConsumer.class);
    private BlockingQueue<SimpleCommand> commandQueue;

    private Server() {
    }

    public Server(BlockingQueue<SimpleCommand> commandQueue) {
        this();
        this.commandQueue = commandQueue;
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(4444)) {
            logger.info("Server is listening on port 4444");
            while (true) {
                Socket socket = serverSocket.accept();
                socket.setTcpNoDelay(true);
                socket.setKeepAlive(true);
                socket.setReuseAddress(true);
                logger.info("Client connected");

                ClientHandler clientHandler = new ClientHandler(socket, commandQueue);
                clientHandler.start();
            }
        } catch (IOException ex) {
            logger.error("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
