package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import util.DBConnection;
import model.Student;
import dao.StudentDAO;
public class Main extends Application {

    @Override
    public void start(Stage stage) {

        DBConnection.getConnection();

        StudentDAO dao = new StudentDAO();

        Student s = new Student(0, "Megha", "megha@gmail.com", "1234", 8.5, "CSE");

        dao.insertStudent(s);

        Label label = new Label("Welcome to SkillBridge 🚀");

        StackPane root = new StackPane();
        root.getChildren().add(label);

        Scene scene = new Scene(root, 600, 400);

        stage.setTitle("SkillBridge Placement System");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}