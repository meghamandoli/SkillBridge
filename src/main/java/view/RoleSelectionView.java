package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class RoleSelectionView {

    public void show(Stage stage){

        Label title = new Label("Who are you?");
        title.setStyle("-fx-font-size:22px; -fx-font-weight:bold;");

        Button studentBtn = new Button("Student");
        Button companyBtn = new Button("Company");

        studentBtn.setPrefWidth(200);
        companyBtn.setPrefWidth(200);

        studentBtn.setOnAction(e -> {
            new LoginView().show(stage);
        });

        companyBtn.setOnAction(e -> {
            new CompanyLoginView().show(stage);
        });

        VBox layout = new VBox(20, title, studentBtn, companyBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));

        stage.setScene(new Scene(layout, 400, 300));
        stage.setTitle("SkillBridge");
        stage.show();
    }
}