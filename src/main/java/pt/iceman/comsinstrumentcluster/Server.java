package pt.iceman.comsinstrumentcluster;

import pt.iceman.comsinstrumentcluster.dashboard.Dashboard;
import pt.iceman.middleware.cars.BaseCommand;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server extends Thread {
    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[65535];
    private Dashboard dashboard;

    private Server() throws SocketException {
        socket = new DatagramSocket(4446);
    }

    public Server (Dashboard dashboard) throws SocketException {
        this();
        this.dashboard = dashboard;
    }

    public void run() {
        running = true;
        try {
            socket = new DatagramSocket(4444);

            DatagramPacket packet = null;
            while (running)
            {
                packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                ByteArrayInputStream bais = new ByteArrayInputStream(buf);
                ObjectInputStream ois = new ObjectInputStream(bais);

                BaseCommand baseCommand = (BaseCommand) ois.readObject();
                dashboard.applyCommand(baseCommand);

                buf = new byte[65535];
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        socket.close();
    }
}