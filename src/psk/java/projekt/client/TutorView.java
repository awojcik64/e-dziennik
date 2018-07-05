package psk.java.projekt.client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Klasa odpowiada za wyświetlanie widoku wykładowcy, implementuje interfejs Initializable
 */
final public class TutorView implements Initializable {
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

    /**
     * To przesłonięta metoda typu void z interfejsu Initializable, odpowiada za inicjalizację kontrolera
     * @param url Parametr typu URL przechowuje adres servera
     * @param resourceBundle Parametr typu ResourceBundle lokalizuje obiekt root
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tutor_selectSubject.setOnAction(event->
        {
            //TODO: Ustawienie listy grup do danego przedmiotu
        });
        tutor_selectGroup.setOnScrollTo(event->
        {
            //TODO:Aktualizacja tabeli studentow przy wyborze grupy.
        });
        tutor_logout.setOnAction(actionEvent -> {
            System.out.println("Tutor logout");
            Platform.exit();
        });
    }
}
