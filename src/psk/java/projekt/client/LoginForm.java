package psk.java.projekt.client;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;

import java.beans.EventHandler;


public class LoginForm {
    @FXML
    TextField login_username;
    @FXML
    PasswordField login_passwd;
    @FXML
    Button login_submit;
    {
        login_submit.setOnAction(event->
        {
            //Obsluga logowania
        });
    }
}
