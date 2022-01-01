package pt.iceman.comsinstrumentcluster;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pt.iceman.comsinstrumentcluster.dashboard.KadettDashboard;
import pt.iceman.comsinstrumentcluster.screen.ScreenLoader;
import pt.iceman.middleware.cars.BaseCommand;

import java.net.SocketException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MainFX extends Application {
    private static final Logger logger = LogManager.getLogger(MainFX.class);

    public MainFX() {
    }

    public void start(Stage stage) throws InterruptedException, SocketException {
        AnchorPane root = new AnchorPane();
        root.setStyle("-fx-background-color: #000000;");

        Scene scene = new Scene(root, 1920, 720, true);
        scene.setFill(Color.BLACK);
        scene.getStylesheets().add("/style.css");

        logger.info("Starting Dashboard");
        KadettDashboard kadettDashboard = new KadettDashboard();

        logger.info("Screen loading");
        ScreenLoader.load(root, kadettDashboard);

        logger.info("Initializing command queue");
        BlockingQueue<BaseCommand> commandQueue = new ArrayBlockingQueue<>(100);

        logger.info("Starting server on port 4444");
        Server server = new Server(commandQueue);
        server.start();
        logger.info("Server started");

        logger.info("Starting command consumer");
        CommandConsumer commandConsumer = new CommandConsumer(kadettDashboard, commandQueue);
        commandConsumer.start();
        logger.info("Command consumer started");

        stage.sizeToScene();
        stage.setScene(scene);
        stage.setMaximized(false);
        stage.setFullScreen(false);
        stage.requestFocus();
        stage.show();
    }

    public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
