package pt.iceman.comsinstrumentcluster;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pt.iceman.middleware.cars.BaseCommand;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;

public class Server extends Thread {
    private static final Logger logger = LogManager.getLogger(CommandConsumer.class);
    private DatagramSocket socket;
    private byte[] buf = new byte[65535];
    private BlockingQueue<BaseCommand> commandQueue;

    private Server() throws SocketException {
        socket = new DatagramSocket(4446);
    }

    public Server (BlockingQueue<BaseCommand> commandQueue) throws SocketException {
        this();
        this.commandQueue = commandQueue;
    }

    public void run() {
        try {
            socket = new DatagramSocket(4444);

            DatagramPacket packet = null;

            while (true)
            {
                packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                ByteArrayInputStream bais = new ByteArrayInputStream(buf);
                ObjectInputStream ois = new ObjectInputStream(bais);

                BaseCommand baseCommand = (BaseCommand) ois.readObject();

                commandQueue.add(baseCommand);

                buf = new byte[65535];
            }
        } catch (SocketException e) {
           logger.error("Problem initializing socket on port 4444", e);
        } catch (IOException e) {
            logger.error("Problem receiving data on port 4444", e);
        } catch (ClassNotFoundException e) {
            logger.error("Received object isn't BaseCommand", e);
        }

        socket.close();
    }
}
