package psk.java.projekt.client;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import psk.java.projekt.server.LoginCredentials;

import java.beans.EventHandler;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginForm implements Initializable {
    @FXML
    TextField login_username;
    @FXML
    PasswordField login_passwd;
    @FXML
    Button login_submit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login_submit.setOnAction(event->
        {
            Main.loginProcedure(new LoginCredentials(login_username.getText(),login_passwd.getText()));
            System.out.println();
        });
    }
}
