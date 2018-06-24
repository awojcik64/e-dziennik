package psk.java.projekt.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;

public class ServerConnectionHandler implements Runnable {
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
    void loginProcedure(LoginCredentials credentials)
    {

    }

    @Override
    public void run() {
        try {
            input=new ObjectInputStream(client.getInputStream());
            System.out.println("Receiving credentials from user...");
            credentials=(LoginCredentials)input.readObject();
            System.out.println("Here's what I got: "+credentials.getLogin()+" i "+credentials.getPasswd());
            String question="SELECT LOGIN, PASSWORD FROM KONTO WHERE LOGIN=\'"+credentials.getLogin()+"\' AND PASSWORD=\'"+credentials.getPasswd()+"\'";
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
            }
            output=new ObjectOutputStream(client.getOutputStream());
            if(rows==false) {
                output.writeObject("loginFailed");
                output.flush();
                Thread.currentThread().join();
            }
            else
            {
                String sqlCheckType="SELECT typ FROM konto WHERE LOGIN=\'"+credentials.getLogin()+"\' AND PASSWORD=\'"+credentials.getPasswd()+"\'";
                stmt=db.createStatement();
                rs=stmt.executeQuery(sqlCheckType);
                userDatagram=new UserDatagram();
                rs.next();
                userDatagram.type=rs.getString(1);
                System.out.println("Odczytałem: "+userDatagram.type);
                if(userDatagram.type.equals("root") || userDatagram.type.equals("tutor"))
                {
                    String sqlGetGroups="SELECT grupy.nazwa FROM grupa_zajeciowa" +
                            " JOIN grupy ON grupy.nr_grupy = grupa_zajeciowa.nr_grupy" +
                            " WHERE grupa_zajeciowa.id_wykladowcy=1";
                    stmt=db.createStatement();
                    rs=stmt.executeQuery(sqlGetGroups);
                    while(!rs.isLast())
                    {
                        userDatagram.groupList.add(rs.getString(1));
                    }

                    String sqlGetSubjects="SELECT przedmioty.nazwa FROM grupa_zajeciowa" +
                            " JOIN przedmioty ON grupa_zajeciowa.id_przedmiotu=przedmioty.id_przedmiotu" +
                            " WHERE grupa_zajeciowa.id_wykladowcy=1";
                    stmt=db.createStatement();
                    rs=stmt.executeQuery(sqlGetSubjects);
                    while(!rs.isLast())
                    {
                        userDatagram.subjectList.add(rs.getString(1));
                    }
                    output.writeObject(userDatagram);


                }
                else if(userDatagram.type.equals("student"))
                {
                    String sqlGetSubject="SELECT ";
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
}
