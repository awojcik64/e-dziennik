package psk.java.projekt.server;

import com.sun.xml.internal.bind.v2.runtime.output.SAXOutput;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Klasa odpowiada za okno serwera, implementuje interfejs Initializable
 */
public class ServerWindow implements Initializable {
    @FXML
    Button serverShutdown;
    @FXML
    Label serverHowMany;

    /**
     * To przesłonięta metoda typu void z interfejsu Initializable, odpowiada za inicjalizację kontrolera
     * @param url Parametr typu URL to adres servera
     * @param resourceBundle Parametr typu ResourceBundle lokalizuje obiekt root
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        serverShutdown.setOnAction(actionEvent -> {
            System.out.println("Administrative shutdown.");
            Platform.exit();
        });
    }

    /**
     * Publiczna metoda typu void to setter który ma za zadanie zmienić licznik
     * @param number Parametr typu int przechowuje ilość połączonych klientów
     */
    public void setNewCounter(int number) {
        serverHowMany.setText(new Integer(number).toString());
    }
}
