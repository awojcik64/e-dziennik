package psk.java.projekt.client;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import psk.java.projekt.server.TutorStudentsTableRow;
import psk.java.projekt.server.UserDatagram;

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
    Button tutor_markSubmit;
    private ObservableList<TutorGroupTableRow> tutorGroupTableModel;
    private ObservableList<TutorStudentsTableRow> tutorStudentsTableModel;
    private ObservableList<String> tutorSubjectSelectionItems;
    @FXML
    TableColumn<TutorGroupTableRow, String> tutorGroupColumn;
    @FXML
    TableColumn<TutorStudentsTableRow, String> tutorNameColumn;
    @FXML
    TableColumn<TutorStudentsTableRow, String> tutorSurnameColumn;
    @FXML
    TableColumn<TutorMarkTableRow, String> tutorMarkColumn;

    /**
     * To przesłonięta metoda typu void z interfejsu Initializable, odpowiada za inicjalizację kontrolera
     * @param url Parametr typu URL przechowuje adres servera
     * @param resourceBundle Parametr typu ResourceBundle lokalizuje obiekt root
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tutor_selectSubject.setOnAction(event -> {

        });

        tutor_selectGroup.setOnScrollTo(event -> {
            //TODO:Aktualizacja tabeli studentow przy wyborze grupy.
        });
        tutor_logout.setOnAction(actionEvent -> {
            System.out.println("Tutor logout");
            Platform.exit();
        });
    }
    public void showData(UserDatagram datagram)
    {
        tutorSubjectSelectionItems=FXCollections.observableArrayList();
        for (String subject:datagram.subjectList) {
            tutorSubjectSelectionItems.add(subject);
        }
        tutor_selectSubject.setItems(tutorSubjectSelectionItems);
        tutorGroupTableModel=FXCollections.observableArrayList();
        for(String group:datagram.groupList)
        {
            tutorGroupTableModel.add(new TutorGroupTableRow(group));
        }
    }
}
class TutorGroupTableRow
{
    public String getGroupName() {
        return groupName.get();
    }

    public StringProperty groupNameProperty() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName.set(groupName);
    }

    public TutorGroupTableRow(String groupName) {
        this.groupName = new SimpleStringProperty(groupName);
    }

    private StringProperty groupName;
}

class TutorMarkTableRow
{
    public TutorMarkTableRow(String mark) {
        this.mark = new SimpleStringProperty(mark);
    }

    public String getMark() {

        return mark.get();
    }

    public StringProperty markProperty() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark.set(mark);
    }

    StringProperty mark;
}