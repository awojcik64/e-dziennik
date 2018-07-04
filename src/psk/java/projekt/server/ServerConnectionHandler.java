package psk.java.projekt.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

final public class ServerConnectionHandler implements Runnable {
    private Socket client;
    private Connection db;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private LoginCredentials credentials;
    private UserDatagram userDatagram;
    public ServerConnectionHandler(Socket client) {
        System.out.println("New connection handler created.");
        this.client = client;
        Thread thread = new Thread(this, "e-dziennik Connection Handler");
        try {
            db=DriverManager.getConnection("jdbc:oracle:thin:@alekstablefield.asuscomm.com:27020:XE","edziennik","B@zka123!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        thread.start();
    }
    @Override
    public void run() {
        try {

            output=new ObjectOutputStream(client.getOutputStream());
            boolean rows = checkLoginData();
            if(isLoginFailure(rows)) {
                output.writeObject("loginFailed");
                output.flush();
                Thread.currentThread().join();
            }
            else
            {
                getAccountType();
                if(isTutor())
                {
                    processGroupsData();

                    processSubjectNames();
                }
                else if(isStudent()) {
                    processStudentData();
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            e.getMessage();
        }
    }

    private boolean isLoginFailure(boolean rows) {
        return rows==false;
    }

    private boolean isStudent() {
        System.out.println("Czy jest to student? "+userDatagram.type.equals("student"));
        return userDatagram.type.equals("student");
    }

    private boolean isTutor() {
        System.out.println("Czy jest to wykladowca?"+(userDatagram.type.equals("root") || userDatagram.type.equals("wykladowca")));
        return userDatagram.type.equals("root") || userDatagram.type.equals("wykladowca");
    }

    private void getAccountType() throws SQLException {
        Statement stmt;
        ResultSet rs;
        String sqlCheckType="SELECT typ FROM konto WHERE LOGIN=\'"+credentials.getLogin()+"\' AND PASSWORD=\'"+credentials.getPasswd()+"\'";
        stmt=db.createStatement();
        rs=stmt.executeQuery(sqlCheckType);
        userDatagram=new UserDatagram();
        rs.next();
        userDatagram.type=rs.getString(1);
        System.out.println("Odczytano typ:"+userDatagram.type);
        System.out.println("Odczytałem: "+userDatagram.type);
    }

    private void processSubjectNames() throws SQLException, IOException {
        Statement stmt;
        ResultSet rs;
        String sqlGetSubjects="SELECT przedmioty.nazwa FROM grupa_zajeciowa" +
                " JOIN przedmioty ON grupa_zajeciowa.id_przedmiotu=przedmioty.id_przedmiotu" +
                " WHERE grupa_zajeciowa.id_wykladowcy=1";
        stmt=db.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        rs=stmt.executeQuery(sqlGetSubjects);
        while(rs.next())
        {
            userDatagram.subjectList.add(rs.getString(1));
        }
        output.writeObject(userDatagram);
    }

    private void processGroupsData() throws SQLException {
        Statement stmt;
        ResultSet rs;
        String sqlGetGroups="SELECT grupy.nazwa FROM grupa_zajeciowa" +
                " JOIN grupy ON grupy.nr_grupy = grupa_zajeciowa.nr_grupy" +
                " WHERE grupa_zajeciowa.id_wykladowcy=1";
        stmt=db.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        rs=stmt.executeQuery(sqlGetGroups);

        while(rs.next())
        {
            userDatagram.groupList.add(rs.getString(1));
        }
    }

    private boolean checkLoginData() throws IOException, ClassNotFoundException, SQLException {
        input=new ObjectInputStream(client.getInputStream());
        System.out.println("Receiving credentials from user...");
        credentials=(LoginCredentials)input.readObject();
        System.out.println("Here's what I got: "+credentials.getLogin()+" i "+credentials.getPasswd());
        String question="SELECT LOGIN, PASSWORD, ID_OSOBA FROM KONTO WHERE LOGIN=\'"+credentials.getLogin()+"\' AND PASSWORD=\'"+credentials.getPasswd()+"\'";
        Statement stmt=db.createStatement();
        System.out.println("Sending db query...");
        ResultSet rs=stmt.executeQuery(question);
        boolean rows;
        if(!rs.next())
        {
            System.out.println("Brak rekordow dotyczacych uzytkownika.");
            rows=false;
        }
        else
        {
            rows=true;
            System.out.println("Wyglada na to, ze dane logowania sa poprawne!");
            credentials.setId(rs.getInt(3));
        }
        return rows;
    }

    private void processStudentData() throws SQLException, IOException {
        Statement stmt;
        ResultSet rs;
        getNrAlbumu();
        rs = getStudentSubjects();
        ArrayList<String> przedmiotList=new ArrayList<>();
        System.out.println("Ok1");
        while(rs.next())
        {
            przedmiotList.add(rs.getString(1));
            rs.next();
        }
        System.out.println("Ok2");
        String sqlGetMarksForEachSubject;
        ArrayList<Double> tmpMarkList;
        System.out.println("Ok3");
        System.out.println("Rozmiar listy przedmiotow:"+przedmiotList.size());
        userDatagram.gradeMap=new HashMap();
        for(int i=0; i< przedmiotList.size(); i++)
        {
            System.out.println("Loop iter start");
            sqlGetMarksForEachSubject="SELECT ocena, przedmioty.nazwa FROM oceny" +
                    " JOIN przedmioty ON oceny.id_przedmiotu=przedmioty.id_przedmiotu" +
                    " WHERE przedmioty.nazwa=\'" +
                    ""+przedmiotList.get(i)+"\' AND nr_albumu="+userDatagram.studentNrAlbumu;
            stmt=db.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            rs=stmt.executeQuery(sqlGetMarksForEachSubject);
            tmpMarkList=new ArrayList<Double>();

            while(rs.next())
            {
                tmpMarkList.add(rs.getDouble(1));
                rs.next();
            }
            userDatagram.gradeMap.put(przedmiotList.get(i),tmpMarkList);
            userDatagram.subjectList=przedmiotList;
            System.out.println(przedmiotList==null?"Jest nullem":"Nie jest nullem");
            System.out.println("Loop iter end");
        }
        output.writeObject(userDatagram);
        System.out.println("Wyslano");
    }

    private ResultSet getStudentSubjects() throws SQLException {
        Statement stmt;
        ResultSet rs;
        String sqlGetSubject="SELECT DISTINCT przedmioty.nazwa FROM oceny" +
                " JOIN przedmioty ON oceny.id_przedmiotu=przedmioty.id_przedmiotu" +
                " WHERE oceny.nr_albumu = "+userDatagram.studentNrAlbumu+" " +
                "ORDER BY przedmioty.nazwa DESC";
        stmt=db.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        rs=stmt.executeQuery(sqlGetSubject);

        return rs;
    }

    private void getNrAlbumu() throws SQLException {
        Statement stmt;
        ResultSet rs;
        String sqlGetStudentNumber="SELECT nr_albumu FROM studenci" +
                " JOIN konto ON studenci.id_osoby=konto.id_osoba" +
                " WHERE konto.id_osoba="+credentials.getId()+"";
        stmt=db.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        rs=stmt.executeQuery(sqlGetStudentNumber);
        if(!rs.next())
        {
            System.out.println("Did not find student number");
        }
        userDatagram.studentNrAlbumu=rs.getInt(1);
        System.out.println("Numer albumu: "+userDatagram.studentNrAlbumu);
    }
}
