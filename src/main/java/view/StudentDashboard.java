package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class StudentDashboard {

    public void show(Stage stage, String studentName){

        BorderPane root = new BorderPane();

        root.setLeft(createSidebar());
        root.setTop(createHeader(studentName));
        root.setCenter(createMainContent());

        Scene scene = new Scene(root,1200,700);
        scene.getStylesheets().add(getClass().getResource("/dashboard.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("SkillBridge Dashboard");
        stage.show();
    }

    // ---------- SIDEBAR ----------
    private VBox createSidebar(){

        Label logo = new Label("SkillBridge");

        Button dashboard = new Button("Dashboard");
        Button jobs = new Button("Browse Jobs");
        Button applications = new Button("My Applications");
        Button profile = new Button("Profile");
        Button alerts = new Button("Alerts");
        Button logout = new Button("Logout");

        VBox sidebar = new VBox(20,
                logo,
                dashboard,
                jobs,
                applications,
                profile,
                alerts,
                logout
        );
        sidebar.getStyleClass().add("sidebar");
        dashboard.getStyleClass().add("menu-button");
        jobs.getStyleClass().add("menu-button");
        applications.getStyleClass().add("menu-button");
        profile.getStyleClass().add("menu-button");
        alerts.getStyleClass().add("menu-button");
        logout.getStyleClass().add("menu-button");
        sidebar.setPadding(new Insets(20));
        sidebar.setPrefWidth(220);

        sidebar.setStyle(
                "-fx-background-color:#EAF4FF;"
        );

        return sidebar;
    }

    // ---------- HEADER ----------
    private HBox createHeader(String studentName){

        Label welcome = new Label("Welcome back, " + studentName);
        welcome.setStyle("-fx-font-size:18; -fx-font-weight:bold;");

        TextField search = new TextField();
        search.setPromptText("Search jobs...");
        search.setPrefWidth(200);

        Label profile = new Label("👤");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(20,
                welcome,
                spacer,
                search,
                profile
        );

        header.setPadding(new Insets(20));

        header.setStyle(
                "-fx-background-color:white;"
        );

        return header;
    }

    // ---------- MAIN CONTENT ----------
    private VBox createMainContent(){

        HBox cards = new HBox(20,
                createCard("Students","2410"),
                createCard("Recruiters","382"),
                createCard("Open Jobs","147"),
                createCard("Applications","38")
        );
        cards.setPadding(new Insets(20));
        cards.setSpacing(25);
        HBox latestJobs = new HBox(20,
                createJobCard("SDE Intern - iOS"),
                createJobCard("Data Analyst"),
                createJobCard("Full Stack Dev")
        );
        Label latestTitle = new Label("Latest Job Listings");
        latestTitle.setStyle("-fx-font-size:18px; -fx-font-weight:bold;");
        VBox jobsSection = new VBox(10,
                latestTitle,
                latestJobs
        );
        Label aiTitle = new Label("AI Recommendations");
        aiTitle.setStyle("-fx-font-size:18px; -fx-font-weight:bold;");
        HBox aiJobs = new HBox(20,
                createJobCard("Backend Engineer"),
                createJobCard("ML Engineer Intern")
        );
        VBox aiSection = new VBox(10,
                aiTitle,
                aiJobs
        );
        HBox middle = new HBox(30,
                jobsSection,
                aiJobs
        );

        VBox main = new VBox(30,
                cards,
                middle
        );

        main.setPadding(new Insets(30));

        main.setStyle(
                "-fx-background-color:#F5F9FF;"
        );

        return main;
    }

    // ---------- CARD ----------
    private VBox createCard(String title,String value){

        Label t = new Label(title);
        Label v = new Label(value);

        t.setStyle("-fx-font-size:16px;");
        v.setStyle("-fx-font-size:26px; -fx-font-weight:bold;");

        VBox card = new VBox(10,t,v);

        card.getStyleClass().add("card");

        return card;
    }

    // ---------- JOB CARD ----------
    private VBox createJobCard(String title){

        Label jobTitle = new Label(title);
        jobTitle.setStyle("-fx-font-size:16px; -fx-font-weight:bold;");

        Label company = new Label("Tech Company • Remote");
        company.setStyle("-fx-text-fill:#555;");

        Button apply = new Button("Apply");
        apply.getStyleClass().add("apply-btn");

        HBox bottom = new HBox(10, company, apply);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        bottom.getChildren().add(1, spacer);

        VBox card = new VBox(10, jobTitle, bottom);

        card.getStyleClass().add("job-card");

        return card;
    }
}