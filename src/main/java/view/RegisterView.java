package view;

import dao.StudentDAO;
import model.Student;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegisterView {

    public void show(Stage stage){

        Label title = new Label("Student Registration");

        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        TextField cgpaField = new TextField();
        cgpaField.setPromptText("CGPA");

        TextField branchField = new TextField();
        branchField.setPromptText("Branch");

        TextField backlogsField = new TextField();
        backlogsField.setPromptText("Backlogs (e.g., 0)");

        TextField skillsField = new TextField();
        skillsField.setPromptText("Skills (Comma separated)");

        Button registerBtn = new Button("Register");

        VBox layout = new VBox(10,
                title,
                nameField,
                emailField,
                passwordField,
                cgpaField,
                branchField,
                backlogsField,
                skillsField,
                registerBtn
        );

        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout,400,450);

        stage.setScene(scene);
        stage.setTitle("Register");

        stage.show();

        registerBtn.setOnAction(e -> {

            Student student = new Student();

            student.setName(nameField.getText());
            student.setEmail(emailField.getText());
            student.setPassword(passwordField.getText());
            student.setCgpa(Double.parseDouble(cgpaField.getText()));
            student.setBranch(branchField.getText());
            try {
                student.setBacklogs(Integer.parseInt(backlogsField.getText()));
            } catch (NumberFormatException nfe) {
                student.setBacklogs(0);
            }
            student.setSkills(skillsField.getText());

            StudentDAO dao = new StudentDAO();
            dao.insertStudent(student);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Registration Successful");
            alert.show();

        });

    }
}
