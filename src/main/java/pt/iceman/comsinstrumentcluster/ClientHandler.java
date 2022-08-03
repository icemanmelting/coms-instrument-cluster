package pt.iceman.comsinstrumentcluster;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pt.iceman.middleware.cars.SimpleCommand;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class ClientHandler extends Thread {
    private static final Logger logger = LogManager.getLogger(ClientHandler.class);
    private final Socket socket;
    private final BlockingQueue<SimpleCommand> commandQueue;

    public ClientHandler(Socket socket, BlockingQueue<SimpleCommand> commandQueue) {
        this.socket = socket;
        this.commandQueue = commandQueue;
    }

    @Override
    public void run() {
        super.run();

        InputStream inputStream = null;

        try {
            inputStream = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(inputStream);

            while (!socket.isClosed() && socket.isConnected()) {
                commandQueue.add((SimpleCommand) ois.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Problem reading from client!");
            try {
                if (inputStream != null) {
                    inputStream.close();
                }

                socket.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            this.stop();
        }
    }
}
