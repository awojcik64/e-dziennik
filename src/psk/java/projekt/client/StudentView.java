package psk.java.projekt.client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

final public class StudentView implements Initializable {
    @FXML
    TableView student_table;
    @FXML
    Button student_logout;
    @FXML
    Label student_name;
    @FXML
    Label student_surname;

    public void solicitation()
    {
        System.out.println("REEEEEEEEEEEEEEEEEE");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        student_logout.setOnAction(actionEvent -> {
            System.out.println("Logout");
            Platform.exit();
                }
        );
    }
}
