package psk.java.projekt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("LoginForm.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
        Parent studentView = FXMLLoader.load(getClass().getResource("StudentView.fxml"));
        Parent tutorView = FXMLLoader.load(getClass().getResource("TutorView.fxml"));
        /*primaryStage.setScene(new Scene(studentView,600,400));
        primaryStage.show();*/
        /*primaryStage.setScene(new Scene(tutorView, 800,400));
        primaryStage.show();*/
    }


    public static void main(String[] args) {
        launch(args);
    }
}
