package psk.java.projekt.server;

import com.sun.corba.se.impl.activation.ServerMain;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Klasa odpowiada za serwer, dziedziczy po klasie Application
 */
public class MainServer extends Application {
    private static Stage serverWindow;
    private static Parent window;
    private Thread server;
    private ServerWindow serverController;

    /**
     * Publiczna metoda typu void to przeslonięta metoda z klasy Application
     * @param stage Parametr typu Stage to scena
     * @throws Exception Może wystapic wyjatek typu Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        serverWindow = stage;
        FXMLLoader loader = new FXMLLoader();
        window = loader.load(getClass().getClassLoader().getResource("psk/java/projekt/server/ServerWindow.fxml"));
        window.prefWidth(300);
        window.prefHeight(200);
        serverWindow.setScene(new Scene(window,300,200));
        serverWindow.setTitle("e-dziennik server");
        serverWindow.show();
        server = new Thread(new ServerMainThread());
        server.start();

        //serverController=loader.getController();
        //loader.setController(serverController);
        //serverController.setNewCounter(0);
    }
}
