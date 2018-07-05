package psk.java.projekt.client;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import psk.java.projekt.server.UserDatagram;

import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;

/**
 * Klasa odpowiada za widok ktory widzi student, implementuje interfejs Initializable
 */
final public class StudentView implements Initializable {
    @FXML
    TableView student_table;
    @FXML
    Button student_logout;
    @FXML
    Label student_name;
    @FXML
    Label student_surname;
    @FXML
    TableColumn<StudentTableRow, String> studentTableColumnSubject;
    @FXML
    TableColumn<StudentTableRow, String> studentTableColumnMarks;
    UserDatagram datagram;
    ClientConnectionHandler clientConnectionHandler;
    private ObservableList<StudentTableRow> studentTableModel;

    /**
     * Czy to metoda tylko do testowania? :D
     */
    public void solicitation()
    {
        System.out.println("REEEEEEEEEEEEEEEEEE");
    }

    /**
     * Przesłonieta metoda typu void z interfejsu Initializable, odpowiada za inicjalizacje kontrolera
     * @param url Parametr typu URL przechowuje adres servera
     * @param resourceBundle Parametr typu ResourceBundle lokalizuje obiekt root
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        student_logout.setOnAction(actionEvent -> {
            System.out.println("Logout");
            clientConnectionHandler.logout();
            Platform.exit();
        });
        studentTableColumnSubject.setCellValueFactory(cellData->cellData.getValue().subjectProperty());
        studentTableColumnMarks.setCellValueFactory(cellData->cellData.getValue().marksProperty());
    }

    /**
     * Publiczny konstruktor klasy StudentView
     */
    public StudentView() {
        clientConnectionHandler = ClientConnectionHandler.getInstance();
        studentTableModel = FXCollections.observableArrayList();
    }

    /**
     * Publiczna metoda typu void odpowiada za wyswietlanie danych
     * @param argDatagram Parametr typu UserDatagram przechowuje dane z datagramu ktore maja byc wyswietlone
     */
    public void showData(UserDatagram argDatagram) {
        String przedmiotNazwa;
        StringBuilder ocenyTemp;
        this.datagram = argDatagram;
        ArrayList<Double> tablicaOcen;

        for(int i = 0; i < datagram.subjectList.size(); i++) {
            przedmiotNazwa = datagram.subjectList.get(i);
            tablicaOcen = datagram.gradeMap.get(przedmiotNazwa);
            ocenyTemp = new StringBuilder();
            System.out.println("Rozmiar tablicy ocen: " + tablicaOcen.size());
            System.out.println("Pierwszy element:" + tablicaOcen.get(0));
            for(int j = 0; j < tablicaOcen.size(); j++) {
                System.out.println("Procesowana ocena:" + tablicaOcen.get(j));
                ocenyTemp.append(tablicaOcen.get(j).toString() + ',' + ' ');
            }
            studentTableModel.add(new StudentTableRow(przedmiotNazwa, ocenyTemp.toString()));
            student_table.setItems(studentTableModel);
            //student_table.getItems().add(new StudentTableRow(przedmiotNazwa, ocenyTemp.toString()));
        }
        student_name.setText(datagram.imie);
        student_surname.setText(datagram.nazwisko);
    }
}

/**
 * Klasa odpowiada za wyswietlanie tabeli
 */
final class StudentTableRow {
    /**
     * Publiczna metoda to getter ma za zadanie pobrac informacje o przedmiotach
     * @return Zwraca typ String - dokladnie przedmiot
     */
    public String getSubject() {
        return subject.get();
    }

    /**
     * Publiczna metoda typu void to setter ktora ma za zadanie przypisac przedmiot do pola subject klasy StudentTableRow
     * @param subject Parametr typu String - dokladnie przedmiot
     */
    public void setSubject(String subject) {
        this.subject = new SimpleStringProperty(subject);
    }

    /**
     * Publiczna metoda to getter ma za zadanie pobrac informacje o ocenach
     * @return Zwraca typ String - dokladnie ocenę
     */
    public String getMarks() {
        return marks.get();
    }

    /**
     * Publiczna metoda odpowiada za wlasciwosci ocen
     * @return Zwraca typ StringProperty - dokldnie ocene
     */
    public StringProperty marksProperty()
    {
        return marks;
    }

    /**
     * Publiczna metoda odpowiada za wlasciwosci przedmiotow
     * @return Zwraca typ StringProperty - dokladnie przedmiot
     */
    public StringProperty subjectProperty()
    {
        return subject;
    }

    /**
     * Publiczna metoda typu void to setter ktory ma za zadanie przypisac ocene do pola marks klasy StudentTableRow
     * @param marks Parametr typu String - jest to ocena
     */
    public void setMarks(String marks) {
        this.marks = new SimpleStringProperty(marks);
    }

    /**
     * Publiczny konstruktor z dwoma parametrami klasy StudentTableRow
     * @param subject Parametr typu String - jest to przedmiot
     * @param marks Parametr typu String - jest to ocena
     */
    public StudentTableRow(String subject, String marks) {
        this.subject = new SimpleStringProperty(subject);
        this.marks = new SimpleStringProperty(marks);
    }

    StringProperty subject;
    StringProperty marks;
}