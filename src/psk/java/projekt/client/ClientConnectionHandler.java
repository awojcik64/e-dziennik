package psk.java.projekt.client;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import psk.java.projekt.server.LoginCredentials;
import psk.java.projekt.server.UserDatagram;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Klasa odpowiada za połączenie Klienta
 */
final public class ClientConnectionHandler{
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private static Socket server;
    private static ClientConnectionHandler instance;

    /**
     * Prywatny konstruktor klasy ClientConnectionHandler
     */
    private ClientConnectionHandler(){
        try {
            server = new Socket("localhost",27027);
            server.setSoTimeout(5000);
        } catch(Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }

    /**
     * Metoda typu void odpowiada za logowanie klienta
     * @param credentials Parametr typu LoginCredentials przechowuje login oraz hasło
     */
    void login(LoginCredentials credentials) {
        try {
            output = new ObjectOutputStream(server.getOutputStream());
            output.writeObject(credentials);
            output.flush();
            System.out.println("Wysłano dane logowania do serwera.");
            input = new ObjectInputStream(server.getInputStream());
            Object response = input.readObject();
            if(response instanceof String) {
                if(response.equals("loginFailed")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd logowania");
                    alert.setContentText("Niepoprawna nazwa użytkownika, bądź hasło");
                    alert.showAndWait();
                    Platform.exit();
                } else {
                    unknownErrorAlert();
                }
            } else if(response instanceof UserDatagram) {
                Main.logonHandler((UserDatagram)response);
                System.out.println((UserDatagram)response + "<- wartosc wskaznika response");
            } else {
                unknownErrorAlert();
            }
        } catch(SocketTimeoutException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connection timeout");
            alert.setContentText("Upłynął czas oczekiwania na odpowiedź od serwera!");
            alert.showAndWait();
            Platform.exit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda typu void sygnalizuje nieznany błąd
     */
    public static void unknownErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Nieznany błąd");
        alert.setContentText("Serwer odpowiedział nieznanym typem komunikatu.");
        alert.showAndWait();
        Platform.exit();
    }

    /**
     * Publiczna statyczna metoda to typowy getter ma pozyskać instancję obiektu
     * @return Zwraca instancję obiektu klasy ClientConnectionHandler jezeli nie istnieje
     */
    static public ClientConnectionHandler getInstance() {
        if(instance == null) {
            instance = new ClientConnectionHandler();
            return instance;
        } else {
            return instance;
        }
    }

    /**
     * Publiczna metoda typu void odpowiada za wylogowanie klienta
     */
    public void logout() {
        if(server.isConnected()) {
            try {
                output.writeObject("logout");
            } catch (IOException e) {
                //e.printStackTrace();
                unknownErrorAlert();
            }
        }
    }
}
