package psk.java.projekt.server;

import com.sun.xml.internal.bind.v2.runtime.output.SAXOutput;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ServerWindow implements Initializable {
    @FXML
    Button serverShutdown;
    @FXML
    Label serverHowMany;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        serverShutdown.setOnAction(actionEvent -> {
            System.out.println("Administrative shutdown.");
            Platform.exit();
        });
    }
}
