package psk.java.projekt.server;

import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ServerConnectionHandler implements Runnable {
    private Socket client;
    private Connection db;

    public ServerConnectionHandler(Socket client) {
        this.client = client;
        Thread thread = new Thread(this, "e-dziennik Connection Handler");
        try {
            db=DriverManager.getConnection("jdbc:oracle:thin:@alekstablefield.asuscomm.com:27020:XE","edziennik","B@zka123!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {

    }
}
