package psk.java.projekt.client;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.xml.soap.Text;

public class TutorView {
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
    {
        tutor_selectSubject.setOnAction(event->
        {
            //TODO: Ustawienie listy grup do danego przedmiotu
        });
        tutor_selectGroup.setOnScrollTo(event->
        {
            //TODO:Aktualizacja tabeli studentow przy wyborze grupy.
        });
    }
}
