package psk.java.projekt.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import psk.java.projekt.server.LoginCredentials;
import psk.java.projekt.server.UserDatagram;

public class Main extends Application {
    private static Stage mainWindow;
    private static Parent tutorView;
    private static Parent loginForm;
    private static Parent studentView;
    private static ClientConnectionHandler handler;

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainWindow = primaryStage;
        tutorView = FXMLLoader.load(getClass().getClassLoader().getResource("psk/java/projekt/client/TutorView.fxml"));
        loginForm = FXMLLoader.load(getClass().getClassLoader().getResource("psk/java/projekt/client/LoginForm.fxml"));
        studentView = FXMLLoader.load(getClass().getClassLoader().getResource("psk/java/projekt/client/StudentView.fxml"));
        tutorView.prefHeight(400);
        tutorView.prefWidth(800);
        studentView.prefHeight(400);
        studentView.prefWidth(600);
        loginForm.prefHeight(400);
        loginForm.prefWidth(600);
        showLoginForm();
        mainWindow.setTitle("e-dziennik");
        mainWindow.show();
        //showStudentView();
        /*Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("psk/java/projekt/client/LoginForm.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();*/
        //Parent studentView = FXMLLoader.load(getClass().getClassLoader().getResource("psk/java/projekt/client/StudentView.fxml"));
        //Parent tutorView = FXMLLoader.load(getClass().getClassLoader().getResource("psk/java/projekt/client/TutorView.fxml"));
        /*primaryStage.setScene(new Scene(studentView,600,400));
        primaryStage.show();*/
        /*primaryStage.setScene(new Scene(tutorView, 800,400));
        primaryStage.show();*/
    }
    public static void showLoginForm() {
        //mainWindow.getScene().setRoot(loginForm);
        mainWindow.setScene(new Scene(loginForm,600,400));
        //mainWindow.show();
    }
    public static void showTutorView() {
        //mainWindow.getScene().setRoot(tutorView);
        mainWindow.setScene(new Scene(tutorView,800,400));
        //mainWindow.show();
    }
    public static void showStudentView() {
        //mainWindow.getScene().setRoot(studentView);
        mainWindow.setScene(new Scene(studentView,600,400));
        //mainWindow.show();
    }
    public static void loginProcedure(LoginCredentials credentials) {
        handler=ClientConnectionHandler.getInstance();
        handler.login(credentials);
    }
    public static void logonHandler(UserDatagram datagram)
    {
        if(datagram.type.equals("student"))
        {
            showStudentView();
        }
        else if(datagram.type.equals("root") || datagram.type.equals("tutor"))
        {
            showTutorView();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
