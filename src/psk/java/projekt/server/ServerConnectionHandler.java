package psk.java.projekt.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Klasa odpowiada za polaczenie, implementuje interfejs Runnable
 */
final public class ServerConnectionHandler implements Runnable {
    private Socket client;
    private Connection db;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private LoginCredentials credentials;
    private UserDatagram userDatagram;

    /**
     * Publiczny konstruktor klasy ServerConnectionHandler
     * @param client Parametr typu Socket przekazuje do konstruktora gniazdo
     */
    public ServerConnectionHandler(Socket client) {
        userDatagram = new UserDatagram();
        System.out.println("New connection handler created.");
        this.client = client;
        Thread thread = new Thread(this, "e-dziennik Connection Handler");
        try {
            db = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","kubasiek22","kubasiek2");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        thread.start();
    }

    /**
     * Przeslonieta metoda typu void z interfejsu Runnable, to co znajduje sie w metodzie run() bedzie sie wykonywalo wspolbierznie
     */
    @Override
    public void run() {
        try {
            output = new ObjectOutputStream(client.getOutputStream());
            boolean rows = checkLoginData();
            if(isLoginFailure(rows)) {
                output.writeObject("loginFailed");
                output.flush();
                Thread.currentThread().join();
            } else {
                getAccountType();
                if(isTutor()) {
                    processGroupsData();
                    processSubjectNames();
                } else if(isStudent()) {
                    processStudentData();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    /**
     * Prywatna metoda typu boolean, jezeli logowanie przebieglo nie pomyslnie to zwraca false
     * @param rows Parametr typu boolean
     * @return Zwraca wartości typu boolean
     */
    private boolean isLoginFailure(boolean rows) {
        return rows == false;
    }

    /**
     * Prywatna metoda typu boolean, sprawdza czy zalogowany jest studentem
     * @return Zwraca wartosci typu boolean true jezeli userDatagram.type.equals("student"); w przeciwnym przypadku zwraca false
     */
    private boolean isStudent() {
        System.out.println("Czy jest to student? " + userDatagram.type.equals("student"));
        return userDatagram.type.equals("student");
    }

    /**
     * Prywatna metoda typu boolean, sprawdza czy zalogowany jest wykladowca
     * @return Zwraca wartosci typu boolean, true jezeli userDatagram.type.equals("root") || userDatagram.type.equals("wykladowca"); w przeciwnym przypadku zwraca false
     */
    private boolean isTutor() {
        System.out.println("Czy jest to wykladowca?" + (userDatagram.type.equals("root") || userDatagram.type.equals("wykladowca")));
        return userDatagram.type.equals("root") || userDatagram.type.equals("wykladowca");
    }

    /**
     * Prywatna metoda typu void to getter - pozyskuje informacje o typie konta
     * @throws SQLException Moze wystąpic wyjatek typu SQLException
     */
    private void getAccountType() throws SQLException {
        Statement stmt;
        ResultSet rs;
        String sqlCheckType = "SELECT typ FROM konto WHERE LOGIN=\'" + credentials.getLogin() + "\' AND PASSWORD=\'" + credentials.getPasswd() + "\'";
        stmt = db.createStatement();
        rs = stmt.executeQuery(sqlCheckType);

        rs.next();
        userDatagram.type = rs.getString(1);
        System.out.println("Odczytano typ:" + userDatagram.type);
        System.out.println("Odczytałem: " + userDatagram.type);
    }

    /**
     * Prywatna metoda typu void przetwarza przedmioty i dodaje je do subjectList
     * @throws SQLException Moze wystapic wyjatek typu SQLException
     * @throws IOException Moze wystapic wyjatek typu IOException
     */
    private void processSubjectNames() throws SQLException, IOException {
        Statement stmt;
        ResultSet rs;
        String sqlGetSubjects = "SELECT przedmioty.nazwa FROM grupa_zajeciowa" +
                " JOIN przedmioty ON grupa_zajeciowa.id_przedmiotu = przedmioty.id_przedmiotu" +
                " WHERE grupa_zajeciowa.id_wykladowcy = 1";
        stmt = db.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = stmt.executeQuery(sqlGetSubjects);
        while(rs.next()) {
            userDatagram.subjectList.add(rs.getString(1));
        }
        output.writeObject(userDatagram);
    }

    /**
     * Prywatna metoda typu void przetwarza grupy i dodaje je do groupList
     * @throws SQLException Moze wystapic wyjatek typu SQLException
     */
    private void processGroupsData() throws SQLException {
        Statement stmt;
        ResultSet rs;
        String sqlGetGroups = "SELECT grupy.nazwa FROM grupa_zajeciowa" +
                " JOIN grupy ON grupy.nr_grupy = grupa_zajeciowa.nr_grupy" +
                " WHERE grupa_zajeciowa.id_wykladowcy = 1";
        stmt = db.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = stmt.executeQuery(sqlGetGroups);

        while(rs.next()) {
            userDatagram.groupList.add(rs.getString(1));
        }
    }

    /**
     * Prywatna metoda typu boolean sprawdza dane logowania
     * @return Zwraca wartocci typu boolean, false jezeli !rs.next() w przeciwnym przypadku zwraca true
     * @throws IOException Moze wystapic wyjatek typu IOException
     * @throws ClassNotFoundException Moze wystapic wyjatek typu ClassNotFoundException
     * @throws SQLException Moze wystapic wyjatek typu SQLException
     */
    private boolean checkLoginData() throws IOException, ClassNotFoundException, SQLException {
        input = new ObjectInputStream(client.getInputStream());
        System.out.println("Receiving credentials from user...");
        credentials = (LoginCredentials)input.readObject();
        System.out.println("Here's what I got: " + credentials.getLogin() + " i " + credentials.getPasswd());
        String question = "SELECT LOGIN, PASSWORD, ID_OSOBA FROM KONTO WHERE LOGIN = \'" + credentials.getLogin() + "\' AND PASSWORD = \'" + credentials.getPasswd() + "\'";
        Statement stmt = db.createStatement();
        System.out.println("Sending db query...");
        ResultSet rs = stmt.executeQuery(question);
        boolean rows;
        if(!rs.next()) {
            System.out.println("Brak rekordow dotyczacych uzytkownika.");
            rows = false;
        } else {
            rows = true;
            System.out.println("Wyglada na to, ze dane logowania sa poprawne!");
            credentials.setId(rs.getInt(3));
            getNameAndSurname(credentials.getId());
        }
        return rows;
    }

    /**
     * Prywatna metoda typu void to getter ktory ma za zadanie pozyskac imie oraz nazwisko
     * @param id_osoba Parametr typu int reprezentuje id_osoby
     * @throws SQLException Moze wystapic wyjatek typu SQLException
     */
    private void getNameAndSurname(int id_osoba) throws SQLException {
        Statement stmt;
        ResultSet rs;
        String sqlGetNameAndSurname = "SELECT imie, nazwisko FROM osoby WHERE id_osoby = " + id_osoba;
        stmt = db.createStatement();
        rs = stmt.executeQuery(sqlGetNameAndSurname);
        if(rs.next()) {
            userDatagram.imie = new String(rs.getString(1));
            userDatagram.nazwisko = new String(rs.getString(2));
        }
    }

    /**
     * Prywatna metoda typu void przetwarza dane studenta
     * @throws SQLException Moze wystapic wyjatek typu SQLException
     * @throws IOException Moze wystapic wyjatek typu IOException
     */
    private void processStudentData() throws SQLException, IOException {
        Statement stmt;
        ResultSet rs;
        getNrAlbumu();
        rs = getStudentSubjects();
        ArrayList<String> przedmiotList = new ArrayList<>();
        System.out.println("Ok1");
        while(rs.next()) {
            przedmiotList.add(rs.getString(1));
            rs.next();
        }
        System.out.println("Ok2");
        String sqlGetMarksForEachSubject;
        ArrayList<Double> tmpMarkList;
        System.out.println("Ok3");
        System.out.println("Rozmiar listy przedmiotow:" + przedmiotList.size());
        userDatagram.gradeMap = new HashMap();
        for(int i = 0; i < przedmiotList.size(); i++) {
            System.out.println("Loop iter start");
            sqlGetMarksForEachSubject = "SELECT ocena, przedmioty.nazwa FROM oceny" +
                    " JOIN przedmioty ON oceny.id_przedmiotu = przedmioty.id_przedmiotu" +
                    " WHERE przedmioty.nazwa = \'" +
                    "" + przedmiotList.get(i) + "\' AND nr_albumu = " + userDatagram.studentNrAlbumu;
            stmt = db.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(sqlGetMarksForEachSubject);
            tmpMarkList = new ArrayList<Double>();

            while(rs.next()) {
                tmpMarkList.add(rs.getDouble(1));
                rs.next();
            }
            userDatagram.gradeMap.put(przedmiotList.get(i), tmpMarkList);
            userDatagram.subjectList = przedmiotList;
            System.out.println(przedmiotList == null ? "Jest nullem" : "Nie jest nullem");
            System.out.println("Loop iter end");
        }
        output.writeObject(userDatagram);
        System.out.println("Wyslano");
    }

    /**
     * Prywatna metoda ktora jest getterem i ma za zadanie pozyskac przedmioty studenta
     * @return Zwraca typ ResultSet ktory zawiera nazwy przedmiotów
     * @throws SQLException Moze wsytapic wyjatek typu SQLException
     */
    private ResultSet getStudentSubjects() throws SQLException {
        Statement stmt;
        ResultSet rs;
        String sqlGetSubject = "SELECT DISTINCT przedmioty.nazwa FROM oceny" +
                " JOIN przedmioty ON oceny.id_przedmiotu = przedmioty.id_przedmiotu" +
                " WHERE oceny.nr_albumu = " + userDatagram.studentNrAlbumu + " " +
                "ORDER BY przedmioty.nazwa DESC";
        stmt = db.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = stmt.executeQuery(sqlGetSubject);

        return rs;
    }

    /**
     * Prywatna metoda typu void to getter ktory ma za zadanie pozyskac numer albumu
     * @throws SQLException Moze wystapic wyjatek typu SQLException
     */
    private void getNrAlbumu() throws SQLException {
        Statement stmt;
        ResultSet rs;
        String sqlGetStudentNumber = "SELECT nr_albumu FROM studenci" +
                " JOIN konto ON studenci.id_osoby = konto.id_osoba" +
                " WHERE konto.id_osoba = " + credentials.getId() + "";
        stmt = db.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = stmt.executeQuery(sqlGetStudentNumber);
        if(!rs.next()) {
            System.out.println("Did not find student number");
        }
        userDatagram.studentNrAlbumu = rs.getInt(1);
        System.out.println("Numer albumu: "+userDatagram.studentNrAlbumu);
    }
}
