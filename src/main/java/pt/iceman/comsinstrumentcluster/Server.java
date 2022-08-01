package pt.iceman.comsinstrumentcluster;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pt.iceman.middleware.cars.BaseCommand;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;

public class Server extends Thread {
    private static final Logger logger = LogManager.getLogger(CommandConsumer.class);
    private BlockingQueue<BaseCommand> commandQueue;

    private Server() throws SocketException {
    }

    public Server(BlockingQueue<BaseCommand> commandQueue) throws SocketException {
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

                InputStream inputStream = socket.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(inputStream);
                try {
                    while (true) {
                        BaseCommand baseCommand = (BaseCommand) ois.readObject();
                        commandQueue.add(baseCommand);
                    }
                } catch (Exception e) {
                    logger.error("Problem reading from client!");
                    inputStream.close();
                }
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
