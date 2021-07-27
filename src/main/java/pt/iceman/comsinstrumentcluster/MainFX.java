package pt.iceman.comsinstrumentcluster;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import pt.iceman.comsinstrumentcluster.dashboard.KadettDashboard;
import pt.iceman.comsinstrumentcluster.screen.ScreenLoader;

public class MainFX extends Application {

    public MainFX() {
    }

    public void start(Stage stage) {
        AnchorPane root = new AnchorPane();
        root.setStyle("-fx-background-color: #000000;");

        Scene scene = new Scene(root, 1920, 720, true);
        scene.setFill(Color.BLACK);

        KadettDashboard kadettDashboard = new KadettDashboard();
        ScreenLoader.load(root, kadettDashboard);

        stage.setScene(scene);
        stage.show();

        Notifications notificationBuilder = Notifications.create()
                .title("Fuel Warning")
                .text("Fuel low level detected!")
                .hideAfter(Duration.seconds(20))
                .position(Pos.BOTTOM_CENTER)
                .darkStyle()
                .graphic(root);

        notificationBuilder.showWarning();

    }

    public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
