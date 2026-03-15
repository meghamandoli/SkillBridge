package view;

import dao.StudentDAO;
import model.Student;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
public class LoginView {

    public void show(Stage stage){

        // -------- LEFT PANEL --------
        Label welcomeTitle = new Label("Welcome Back!");
        welcomeTitle.setStyle("-fx-font-size:28px; -fx-text-fill:white;");

        Label subtitle = new Label("Login to continue using SkillBridge");
        subtitle.setStyle("-fx-text-fill:white;");

        VBox leftPanel = new VBox(15, welcomeTitle, subtitle);
        leftPanel.getStyleClass().add("left-panel");
        leftPanel.setAlignment(Pos.CENTER);
        leftPanel.setPadding(new Insets(40));

        leftPanel.setStyle(
                "-fx-background-color: linear-gradient(to bottom right,#4facfe,#00f2fe);" +
                        "-fx-background-radius:20;"
        );

        // -------- LOGIN FORM --------
        Label loginTitle = new Label("Login");
        loginTitle.setStyle("-fx-font-size:22px;");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setPrefWidth(220);
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        emailField.setPrefWidth(220);
        CheckBox remember = new CheckBox("Remember me");

        Hyperlink forgot = new Hyperlink("Forgot password?");

        HBox options = new HBox(20, remember, forgot);
        options.setAlignment(Pos.CENTER);
        Button loginButton = new Button("Sign In");
        loginButton.getStyleClass().add("login-btn");
        Hyperlink signup = new Hyperlink("Don't have an account? Sign Up");
        loginButton.setStyle(
                "-fx-background-color: linear-gradient(to right,#4facfe,#00f2fe);" +
                        "-fx-text-fill:white;" +
                        "-fx-font-size:14px;" +
                        "-fx-background-radius:20;" +
                        "-fx-padding:10 30;"
        );
        VBox rightPanel = new VBox(
                15,
                loginTitle,
                emailField,
                passwordField,
                options,
                loginButton,
                signup
        );
        rightPanel.getStyleClass().add("login-panel");
        rightPanel.setAlignment(Pos.CENTER);
        rightPanel.setPadding(new Insets(40));

        rightPanel.setStyle(
                "-fx-background-color: rgba(255,255,255,0.9);" +
                        "-fx-background-radius:20;"
        );

        // -------- CARD LAYOUT --------
        HBox card = new HBox(leftPanel, rightPanel);
        card.getStyleClass().add("card");
        card.setStyle(
                "-fx-background-color: rgba(255,255,255,0.2);" +
                        "-fx-background-radius:20;" +
                        "-fx-padding:20;"
        );
        card.setAlignment(Pos.CENTER);
        card.setSpacing(20);
        DropShadow shadow = new DropShadow();
        shadow.setRadius(20);
        shadow.setColor(Color.gray(0,0.4));

        card.setEffect(shadow);

        // -------- BACKGROUND --------
        StackPane root = new StackPane(card);
        root.setAlignment(Pos.CENTER);
        root.setStyle(
                "-fx-background-color: linear-gradient(to bottom right,#a1c4fd,#c2e9fb);"
        );

        Scene scene = new Scene(root,1000,600);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.setTitle("SkillBridge Login");
        stage.setScene(scene);
        stage.show();

        // -------- LOGIN BUTTON ACTION --------
        loginButton.setOnAction(e -> {

            String email = emailField.getText();
            String password = passwordField.getText();
            emailField.setStyle(
                    "-fx-background-radius:15;" +
                            "-fx-padding:10;" +
                            "-fx-border-radius:15;"
            );

            passwordField.setStyle(
                    "-fx-background-radius:15;" +
                            "-fx-padding:10;" +
                            "-fx-border-radius:15;"
            );

            StudentDAO dao = new StudentDAO();
            Student student = dao.loginStudent(email,password);

            if(student != null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Welcome " + student.getName());
                alert.show();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Invalid Login");
                alert.show();
            }

        });

    }
}