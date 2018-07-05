package psk.java.projekt.server;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Klasa odpowiada za datagram użytkownika, implementuje interfejs Serializable
 */
final public class UserDatagram implements Serializable {
    public String command;
    public String type;
    public String imie;
    public String nazwisko;
    public String tytul;
    public Integer studentNrAlbumu;
    public Integer tutorID;
    public String nazwaPrzedmiotu;
    public int idPrzedmiotu;
    public String nazwaGrupy;
    public int idGrupy;
    public int addedMark;
    public String studentImie;
    public String studentNazwisko;
    public HashMap<String,ArrayList<Double>> gradeMap;
    public ArrayList<String> groupList;
    public ArrayList<String> subjectList;
    public ArrayList<String> markList;
    public ArrayList<TutorStudentsTableRow> tutorStudentsList;
}
