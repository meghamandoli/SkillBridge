package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CompanyDashboard {

    public void show(Stage stage, String companyName, int companyId){

        BorderPane root = new BorderPane();

        Label header = new Label("Welcome, " + companyName);
        header.setStyle("-fx-font-size:18px; -fx-font-weight:bold;");
        header.setPadding(new Insets(20));

        root.setTop(header);

        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.setTitle("Company Dashboard");
        stage.show();
    }
}