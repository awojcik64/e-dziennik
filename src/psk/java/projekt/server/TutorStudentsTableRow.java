package psk.java.projekt.server;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TutorStudentsTableRow
{
    private StringProperty name;
    private StringProperty surname;

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSurname() {
        return surname.get();
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public TutorStudentsTableRow(String name, String surname) {
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
    }
}