package psk.java.projekt.server;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
final public class UserDatagram implements Serializable {
    public String type;
    public String imie;
    public String nazwisko;
    public String tytul;
    public Map<String,Double[]> gradeMap;
    public ArrayList<String> groupList;
    public ArrayList<String> studentList;
    public ArrayList<String> subjectList;

}

