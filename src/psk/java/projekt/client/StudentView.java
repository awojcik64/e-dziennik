package psk.java.projekt.client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.TableView;
import psk.java.projekt.server.UserDatagram;

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
    UserDatagram datagram;
    ClientConnectionHandler clientConnectionHandler;
    public void solicitation()
    {
        System.out.println("REEEEEEEEEEEEEEEEEE");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        student_logout.setOnAction(actionEvent -> {
            System.out.println("Logout");
            clientConnectionHandler.logout();
            Platform.exit();
                }
        );
    }
    public StudentView(){
        clientConnectionHandler=ClientConnectionHandler.getInstance();
    }
    public void showData(UserDatagram argDatagram)
    {
        this.datagram=argDatagram;
        for(int i=1; i<=datagram.subjectList.size(); i++)
        {

        }
    }
}
