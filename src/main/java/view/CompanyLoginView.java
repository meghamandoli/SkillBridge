package view;

import dao.CompanyDAO;
import model.Company;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public class CompanyLoginView {

    public void show(Stage stage){
        Company remembered = new CompanyDAO().getRememberedCompany();

        if(remembered != null){
            new CompanyDashboard().show(stage, remembered.getName(), remembered.getId());
            return;
        }
        // -------- LEFT PANEL --------
        Label welcomeTitle = new Label("Welcome Back!");
        welcomeTitle.setStyle("-fx-font-size:28px; -fx-text-fill:white;");

        Label subtitle = new Label("Login as Company to manage jobs");
        subtitle.setStyle("-fx-text-fill:white;");

        VBox leftPanel = new VBox(15, welcomeTitle, subtitle);
        leftPanel.setAlignment(Pos.CENTER);
        leftPanel.setPadding(new Insets(40));

        leftPanel.setStyle(
                "-fx-background-color: linear-gradient(to bottom right,#00c9a7,#92fe9d);" +
                        "-fx-background-radius:20;"
        );

        // -------- LOGIN FORM --------
        Label loginTitle = new Label("Company Login");
        loginTitle.setStyle("-fx-font-size:22px;");

        TextField emailField = new TextField();
        emailField.setPromptText("Company Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        CheckBox remember = new CheckBox("Remember me");
        Hyperlink forgot = new Hyperlink("Forgot password?");
        forgot.setOnAction(e -> {

            TextInputDialog emailDialog = new TextInputDialog();
            emailDialog.setHeaderText("Enter your email");

            emailDialog.showAndWait().ifPresent(email -> {

                TextInputDialog passDialog = new TextInputDialog();
                passDialog.setHeaderText("Enter new password");

                passDialog.showAndWait().ifPresent(newPass -> {

                    CompanyDAO dao = new CompanyDAO();
                    boolean updated = dao.resetPassword(email,newPass);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);

                    if(updated){
                        alert.setContentText("Password Updated");
                    }else{
                        alert.setContentText("Email not found");
                    }

                    alert.show();
                });
            });
        });
        HBox options = new HBox(20, remember, forgot);
        options.setAlignment(Pos.CENTER);
        Button loginButton = new Button("Sign In");
        Hyperlink signup = new Hyperlink("Don't have an account? Sign Up");
        signup.setOnAction(e -> {
            new CompanyRegisterView().show(stage);
        });
        loginButton.setStyle(
                "-fx-background-color: linear-gradient(to right,#00c9a7,#92fe9d);" +
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
        rightPanel.setAlignment(Pos.CENTER);
        rightPanel.setPadding(new Insets(40));

        rightPanel.setStyle(
                "-fx-background-color: rgba(255,255,255,0.9);" +
                        "-fx-background-radius:20;"
        );

        // -------- CARD --------
        HBox card = new HBox(leftPanel, rightPanel);
        card.setAlignment(Pos.CENTER);
        card.setSpacing(20);
        card.setPadding(new Insets(20));

        DropShadow shadow = new DropShadow();
        shadow.setRadius(20);
        shadow.setColor(Color.gray(0,0.4));
        card.setEffect(shadow);

        // -------- ROOT --------
        StackPane root = new StackPane(card);
        root.setAlignment(Pos.CENTER);
        root.setStyle(
                "-fx-background-color: linear-gradient(to bottom right,#a1c4fd,#c2e9fb);"
        );

        Scene scene = new Scene(root,1000,600);
        stage.setScene(scene);
        stage.setTitle("Company Login");
        stage.show();

        // -------- LOGIN ACTION --------
        loginButton.setOnAction(e -> {

            String email = emailField.getText();
            String password = passwordField.getText();

            CompanyDAO dao = new CompanyDAO();
            Company company = dao.loginCompany(email, password);

            if(company != null){

                if(remember.isSelected()){
                    dao.rememberCompany(email);
                }

                new CompanyDashboard().show(stage, company.getName(), company.getId());
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Invalid Login");
                alert.show();
            }
        });
    }
}