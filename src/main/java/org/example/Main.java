package org.example;

import javafx.application.Application;
import javafx.stage.Stage;
import view.RoleSelectionView;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        new RoleSelectionView().show(stage);

    }

    public static void main(String[] args) {
        launch();
    }
}
