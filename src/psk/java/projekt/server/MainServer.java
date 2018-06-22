package psk.java.projekt.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainServer extends Application {
    private static Stage serverWindow;
    private static Parent window;
    @Override
    public void start(Stage stage) throws Exception {
        serverWindow=stage;
        window=FXMLLoader.load(getClass().getClassLoader().getResource("psk/java/projekt/server/ServerWindow.fxml"));
        window.prefWidth(300);
        window.prefHeight(200);
        serverWindow.setScene(new Scene(window,300,200));
        serverWindow.show();
    }
}
