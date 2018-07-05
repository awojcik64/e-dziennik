package psk.java.projekt.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import psk.java.projekt.server.LoginCredentials;
import psk.java.projekt.server.UserDatagram;

/**
 * Głowna klasa aplikacji
 */
final public class Main extends Application {
    private static Stage mainWindow;
    private static Parent tutorView;
    private static TutorView tutorViewCtrl;
    private static Parent loginForm;
    private static LoginForm loginFormCtrl;
    private static Parent studentView;
    private static StudentView studentViewCtrl;
    private static ClientConnectionHandler handler;
    private static FXMLLoader loginFormLoader;
    private static FXMLLoader tutorViewLoader;
    private static FXMLLoader studentViewLoader;

    /**
     * Metoda to główny punkt wejścia dla wszystkich aplikacji JavaFX
     * @param primaryStage Główna scena
     * @throws Exception Metoda może powodować wyjątki
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        mainWindow = primaryStage;
        loginFormLoader = new FXMLLoader(getClass().getClassLoader().getResource("psk/java/projekt/client/LoginForm.fxml"));
        tutorViewLoader = new FXMLLoader(getClass().getClassLoader().getResource("psk/java/projekt/client/TutorView.fxml"));
        studentViewLoader = new FXMLLoader(getClass().getClassLoader().getResource("psk/java/projekt/client/StudentView.fxml"));
        tutorView = tutorViewLoader.load();
        tutorViewCtrl = tutorViewLoader.getController();
        loginForm = loginFormLoader.load();
        loginFormCtrl = loginFormLoader.getController();
        studentView = studentViewLoader.load();
        studentViewCtrl = studentViewLoader.getController();
        studentViewCtrl.solicitation();
        assert studentViewCtrl != null || tutorViewCtrl != null || loginFormCtrl != null:"Nie udało się załadować poprawnie kontrolerów.";
        tutorView.prefHeight(400);
        tutorView.prefWidth(800);
        studentView.prefHeight(400);
        studentView.prefWidth(600);
        loginForm.prefHeight(400);
        loginForm.prefWidth(600);
        showLoginForm();
        mainWindow.setTitle("e-dziennik");
        mainWindow.show();
    }

    /**
     * Metoda odpowiada za wyświetlenie formularza logowania
     */
    public static void showLoginForm() {
        //mainWindow.getScene().setRoot(loginForm);
        mainWindow.setScene(new Scene(loginForm,600,400));
        //mainWindow.show();
    }

    /**
     * Metoda odpowiada za wyświetlenie widoku wykładowcy
     */
    public static void showTutorView() {
        //mainWindow.getScene().setRoot(tutorView);
        mainWindow.setScene(new Scene(tutorView,800,400));
        //mainWindow.show();
    }

    /**
     * Metoda odpowiada za wyświetlenie widoku studenta
     */
    public static void showStudentView() {
        //mainWindow.getScene().setRoot(studentView);
        mainWindow.setScene(new Scene(studentView,600,400));
        //mainWindow.show();
    }

    /**
     * Metoda odpowiada za logowanie
     * @param credentials Parametr przechowuje dane logowania - login i hasło
     */
    public static void loginProcedure(LoginCredentials credentials) {
        handler = ClientConnectionHandler.getInstance();
        handler.login(credentials);
    }

    /**
     * Metoda odpowiada za wyświetlenie odpowiedniego widoku dla danego typu konta
     * @param datagram Dane które pochodzą z datagramu
     */
    public static void logonHandler(UserDatagram datagram) {
        if(datagram.type.equals("student")) {
            showStudentView();
            studentViewCtrl.showData(datagram);
        } else if(datagram.type.equals("root") || datagram.type.equals("tutor")) {
            showTutorView();
        }
    }

    /**
     * Metoda wywołuje metodę launch z interfejsu Application która ma za zadanie uruchomienie aplikacji
     * @param args Argumenty programu
     */
    public static void main(String[] args) {
        launch(args);
    }
}
