package pt.iceman.comsinstrumentcluster;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pt.iceman.comsinstrumentcluster.dashboard.KadettDashboard;
import pt.iceman.comsinstrumentcluster.screen.ScreenLoader;

import java.net.SocketException;

public class MainFX extends Application {

    public MainFX() {
    }

    public void start(Stage stage) throws InterruptedException, SocketException {
        AnchorPane root = new AnchorPane();
        root.setStyle("-fx-background-color: #000000;");

        Scene scene = new Scene(root, 1920, 720, true);
        scene.setFill(Color.BLACK);
        scene.getStylesheets().add("/style.css");

        KadettDashboard kadettDashboard = new KadettDashboard();
        ScreenLoader.load(root, kadettDashboard);

        Server server = new Server(kadettDashboard);
        server.start();

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
