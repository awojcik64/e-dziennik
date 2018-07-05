package psk.java.projekt.server;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Klasa odpowiada za datagram uzytkownika, implementuje interfejs Serializable
 */
final public class UserDatagram implements Serializable {
    public String type;
    public String imie;
    public String nazwisko;
    public String tytul;
    public Integer studentNrAlbumu;
    public HashMap<String,ArrayList<Double>> gradeMap;
    public ArrayList<String> groupList;
    public ArrayList<String> studentList;
    public ArrayList<String> subjectList;
}

