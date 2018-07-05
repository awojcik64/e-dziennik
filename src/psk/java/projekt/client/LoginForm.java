package psk.java.projekt.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import psk.java.projekt.server.LoginCredentials;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Klasa odpowiada za formularz logowania
 */
final public class LoginForm implements Initializable {
    @FXML
    TextField login_username;
    @FXML
    PasswordField login_passwd;
    @FXML
    Button login_submit;

    /**
     * To przesłonięta metoda typu void z interfejsu Initializable, odpowiada za inicjalizację kontrolera
     * @param url Parametr typu URL to adres servera
     * @param resourceBundle Parametr typu ResourceBundle lokalizuje obiekt root
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login_submit.setOnAction(event -> {
            Main.loginProcedure(new LoginCredentials(login_username.getText(),login_passwd.getText()));
            System.out.println();
        });
    }
}
