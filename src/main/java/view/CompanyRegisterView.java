package view;

import dao.CompanyDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Company;

public class CompanyRegisterView {

    public void show(Stage stage){

        Label title = new Label("Register Company");
        title.setStyle("-fx-font-size:20px; -fx-font-weight:bold;");

        TextField nameField = new TextField();
        nameField.setPromptText("Company Name");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button registerBtn = new Button("Register");

        registerBtn.setOnAction(e -> {

            String name = nameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();

            Company company = new Company(0, name, email, password);

            CompanyDAO dao = new CompanyDAO();

            if(dao.insertCompany(company)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Registration Successful!");
                alert.show();

                new CompanyLoginView().show(stage); // go back to login
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Error!");
                alert.show();
            }
        });

        VBox layout = new VBox(15, title, nameField, emailField, passwordField, registerBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        stage.setScene(new Scene(layout, 400, 300));
        stage.setTitle("Company Register");
        stage.show();
    }
}