package psk.java.projekt.client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javax.xml.soap.Text;
import java.net.URL;
import java.util.ResourceBundle;

public class TutorView implements Initializable {
    @FXML
    ComboBox<String> tutor_selectSubject;
    @FXML
    TableView<String> tutor_selectGroup;
    @FXML
    TableView<String> tutor_selectStudent;
    @FXML
    TableView<String> tutor_selectMark;
    @FXML
    Label tutor_degree;
    @FXML
    Label tutor_name;
    @FXML
    Label tutor_surname;
    @FXML
    Button tutor_logout;
    @FXML
    TextField tutor_showName;
    @FXML
    TextField tutor_showSurname;
    @FXML
    ComboBox<String> tutor_markSelect;
    @FXML
    TextField tutor_markDesc;
    @FXML
    DatePicker tutor_markDate;
    @FXML
    Button tutor_markSubmit;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String subjects[] = new String[7];
        subjects[0] = "Programowanie w języku Java";
        subjects[1] = "Systemy operacyjne 2";
        subjects[2] = "Systemy inteligentne 1";
        subjects[3] = "Sieci komputerowe";
        subjects[4] = "Ochrona własności intelektualnej";
        subjects[5] = "Bazy danych 2";
        subjects[6] = "Język obcy 2";
        tutor_selectSubject.getItems().setAll(subjects);
        tutor_selectSubject.setOnAction(event-> {
            //TODO: Ustawienie listy grup do danego przedmiotu
        });
        tutor_selectGroup.setOnScrollTo(event-> {
            //TODO:Aktualizacja tabeli studentow przy wyborze grupy.
        });
        tutor_logout.setOnAction(actionEvent -> {
            System.out.println("Tutor logout");
            Platform.exit();
        });
    }
}
