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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Klasa odpowiada za wyświetlanie widoku wykładowcy, implementuje interfejs Initializable
 */
final public class TutorView implements Initializable {
    @FXML
    ComboBox<String> tutor_selectSubject;
    @FXML
    TableView<TutorGroupTableRow> tutor_selectGroup;
    @FXML
    TableView<TutorStudentsTableRow> tutor_selectStudent;
    @FXML
    TableView<TutorMarkTableRow> tutor_selectMark;
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
    @FXML
    TableColumn<TutorGroupTableRow, String> tutorGroupColumn;
    @FXML
    TableColumn<TutorStudentsTableRow, String> tutorNameColumn;
    @FXML
    TableColumn<TutorStudentsTableRow, String> tutorSurnameColumn;
    @FXML
    TableColumn<TutorMarkTableRow, String> tutorMarkColumn;
    private ObservableList<TutorGroupTableRow> tutorGroupTableModel;
    private ObservableList<TutorStudentsTableRow> tutorStudentsTableModel;
    private ObservableList<String> tutorSubjectSelectionItems;
    private ObservableList<TutorMarkTableRow> tutorMarkTableModel;
    private ClientConnectionHandler clientConnectionHandler;
    private UserDatagram userDatagram;


    public TutorView() {
        clientConnectionHandler = ClientConnectionHandler.getInstance();
    }

    /**
     * To przesłonięta metoda typu void z interfejsu Initializable, odpowiada za inicjalizację kontrolera
     *
     * @param url            Parametr typu URL przechowuje adres servera
     * @param resourceBundle Parametr typu ResourceBundle lokalizuje obiekt root
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tutor_selectSubject.setOnAction(event -> {
            userDatagram.command = "getGrupaByPrzedmiot";
            userDatagram.nazwaPrzedmiotu = tutor_selectSubject.getValue();
            tutorGroupTableModel = FXCollections.observableArrayList();
            try {
                userDatagram = (UserDatagram) clientConnectionHandler.getDataFromServer(userDatagram);
                for (String group : userDatagram.groupList) {
                    tutorGroupTableModel.add(new TutorGroupTableRow(group));
                }
                tutor_selectGroup.setItems(tutorGroupTableModel);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        tutor_selectGroup.setOnMouseClicked(event -> {
            userDatagram.command = "getStudentsByGroup";
            userDatagram.nazwaGrupy = tutor_selectGroup.getSelectionModel().getSelectedItem().getGroupName();
            tutorStudentsTableModel=FXCollections.observableArrayList();
            try {
                userDatagram = (UserDatagram) clientConnectionHandler.getDataFromServer(userDatagram);
                for (TutorStudentsTableRow row : userDatagram.tutorStudentsList) {
                    tutorStudentsTableModel.add(new TutorStudentsTableRow(row.getName(), row.getSurname()));
                }
                tutor_selectStudent.setItems(tutorStudentsTableModel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        tutor_selectStudent.setOnMouseClicked(event->
        {
            userDatagram.command="getMarksByStudent";
            userDatagram.studentImie=tutor_selectStudent.getSelectionModel().getSelectedItem().getName();
            userDatagram.studentNazwisko=tutor_selectStudent.getSelectionModel().getSelectedItem().getSurname();
            System.out.println("Id studenta do odczytu:" +userDatagram.studentNrAlbumu);
            System.out.println("Nr przedmiotu:"+userDatagram.idPrzedmiotu);
            tutorMarkTableModel=FXCollections.observableArrayList();
            try{
                userDatagram=(UserDatagram)clientConnectionHandler.getDataFromServer(userDatagram);
                for(String row:userDatagram.markList)
                {
                    tutorMarkTableModel.add(new TutorMarkTableRow(row));
                    System.out.println("Select student trigger executed");
                }
                tutor_selectMark.setItems(tutorMarkTableModel);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });
        tutor_logout.setOnAction(actionEvent -> {
            System.out.println("Tutor logout");
            Platform.exit();
        });
        tutorGroupColumn.setCellValueFactory(cellData -> cellData.getValue().groupNameProperty());
        tutorNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        tutorSurnameColumn.setCellValueFactory(cellData -> cellData.getValue().surnameProperty());
        tutorMarkColumn.setCellValueFactory(cellData -> cellData.getValue().markProperty());
    }

    public void showData(UserDatagram datagram) {
        this.userDatagram = datagram;
        tutorSubjectSelectionItems = FXCollections.observableArrayList();
        for (String subject : userDatagram.subjectList) {
            tutorSubjectSelectionItems.add(subject);
        }
        tutor_selectSubject.setItems(tutorSubjectSelectionItems);
        tutorGroupTableModel = FXCollections.observableArrayList();
        for (String group : userDatagram.groupList) {
            tutorGroupTableModel.add(new TutorGroupTableRow(group));
        }
    }
}

class TutorGroupTableRow {
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

class TutorMarkTableRow {
    public TutorMarkTableRow(String mark) {
        this.mark = mark;
    }

    public String getMark() {

        return mark;
    }

    public StringProperty markProperty() {
        return new SimpleStringProperty(mark);
    }

    public void setMark(String mark) {
        this.mark=mark;
    }

    String mark;
}