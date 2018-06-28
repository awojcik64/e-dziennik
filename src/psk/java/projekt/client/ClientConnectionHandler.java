package psk.java.projekt.client;

import com.sun.security.ntlm.Client;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import psk.java.projekt.server.LoginCredentials;
import psk.java.projekt.server.UserDatagram;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ClientConnectionHandler{
    ObjectOutputStream output;
    ObjectInputStream input;
    private static Socket server;
    private static ClientConnectionHandler instance;
    private ClientConnectionHandler(){
        try{
            server=new Socket("localhost",27027);
            server.setSoTimeout(5000);
        }
        catch(Exception e)
        {
            e.getMessage();
            e.printStackTrace();
        }

    }
    public void login(LoginCredentials credentials)
    {
        try {
            output=new ObjectOutputStream(server.getOutputStream());
            output.writeObject(credentials);
            output.flush();
            System.out.println("Wysłano dane logowania do serwera.");
            input=new ObjectInputStream(server.getInputStream());
            Object response=input.readObject();
            if(response instanceof String)
            {
                if(response.equals("loginFailed"))
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd logowania");
                    alert.setContentText("Niepoprawna nazwa użytkownika, bądź hasło");
                    alert.showAndWait();
                    Platform.exit();
                }
                else
                {
                    unknownError();
                }
            }
            else if(response instanceof UserDatagram)
            {
                //if(((UserDatagram) response).type.equals("student"))
                //{
                    Main.logonHandler((UserDatagram)response);
                //}
            }
            else
            {
                unknownError();
            }

        }catch(SocketTimeoutException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connection timeout");
            alert.setContentText("Upłynął czas oczekiwania na odpowiedź od serwera!");
            alert.showAndWait();
            Platform.exit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void unknownError()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Nieznany błąd");
        alert.setContentText("Serwer odpowiedział nieznanym typem komunikatu.");
        alert.showAndWait();
        Platform.exit();
    }
    static public ClientConnectionHandler getInstance()
    {
        if(instance==null)
        {
            instance=new ClientConnectionHandler();
            return instance;
        }
        else
        {
            return instance;
        }

    }

}
