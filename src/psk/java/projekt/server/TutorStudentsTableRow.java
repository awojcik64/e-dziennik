package psk.java.projekt.server;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

public class TutorStudentsTableRow implements Serializable
{
    private String name;
    private String surname;

    public String getName() {
        return name;
    }

    public StringProperty nameProperty() {
        return new SimpleStringProperty(name);
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getSurname() {
        return surname;
    }

    public StringProperty surnameProperty() {
        return new SimpleStringProperty(surname);
    }

    public void setSurname(String surname) {
        this.surname=surname;
    }

    public TutorStudentsTableRow(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
}