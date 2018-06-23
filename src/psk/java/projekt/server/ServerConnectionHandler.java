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
    public ServerConnectionHandler(Socket client) {
        this.client = client;
        Thread thread = new Thread(this, "e-dziennik Connection Handler");
        try {
            db=DriverManager.getConnection("jdbc:oracle:thin:@alekstablefield.asuscomm.com:27020:XE","edziennik","B@zka123!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    void loginProcedure(LoginCredentials credentials)
    {

    }

    @Override
    public void run() {
        try {
            input=new ObjectInputStream(client.getInputStream());
            output=new ObjectOutputStream(client.getOutputStream());
            credentials=(LoginCredentials)input.readObject();
            String question="SELECT LOGIN, PASSWORD FROM KONTO WHERE LOGIN="+credentials.getLogin()+" AND PASSWORD="+credentials.getPasswd();
            Statement stmt=db.createStatement();
            ResultSet rs=stmt.executeQuery(question);
            int rows;
            if(rs.last())
            {
                rows = rs.getRow();
                rs.beforeFirst();
            }
            else{
                rows=0;
            }
            if(rows==0) {
                output.writeObject("loginFailed");
                output.flush();
                Thread.currentThread().join();
            }
            else
            {

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
