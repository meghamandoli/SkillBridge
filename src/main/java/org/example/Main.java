package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import util.DBConnection;
import model.Student;
import dao.StudentDAO;
import view.LoginView;
public class Main extends Application {

    @Override
    public void start(Stage stage) {

        LoginView loginView = new LoginView();
        loginView.show(stage);

    }
    public static void main(String[] args) {
        launch();
    }
}