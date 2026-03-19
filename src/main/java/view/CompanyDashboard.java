package view;

import dao.ApplicationDAO;
import dao.JobDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Job;

import java.util.List;

public class CompanyDashboard {

    private String currentCompanyName;
    private int currentCompanyId;

    public void show(Stage stage, String companyName, int companyId){

        this.currentCompanyName = companyName;
        this.currentCompanyId = companyId;

        BorderPane root = new BorderPane();

        root.setLeft(createSidebar(stage));
        root.setTop(createHeader());
        root.setCenter(createMainContent());

        Scene scene = new Scene(root,1200,700);
        scene.getStylesheets().add(getClass().getResource("/dashboard.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Company Dashboard");
        stage.show();
    }

    // ---------- SIDEBAR ----------
    private VBox createSidebar(Stage stage){

        Label logo = new Label("SkillBridge");

        Button dashboard = new Button("Dashboard");
        Button postJob = new Button("Post Job");
        Button myJobs = new Button("My Jobs");
        Button applicants = new Button("Applicants");
        Button logout = new Button("Logout");
        applicants.setOnAction(e -> {
            stage.getScene().setRoot(getLayout(stage, createApplicantsPage()));
        });
        dashboard.setOnAction(e -> {
            stage.getScene().setRoot(getLayout(stage, createMainContent()));
        });

        postJob.setOnAction(e -> {
            stage.getScene().setRoot(getLayout(stage, createPostJobPage()));
        });

        myJobs.setOnAction(e -> {
            stage.getScene().setRoot(getLayout(stage, createMyJobsPage()));
        });

        logout.setOnAction(e -> {
            new RoleSelectionView().show(stage);
        });

        VBox sidebar = new VBox(20,
                logo,
                dashboard,
                postJob,
                myJobs,
                applicants,
                logout
        );

        sidebar.getStyleClass().add("sidebar");

        dashboard.getStyleClass().add("menu-button");
        postJob.getStyleClass().add("menu-button");
        myJobs.getStyleClass().add("menu-button");
        applicants.getStyleClass().add("menu-button");
        logout.getStyleClass().add("menu-button");

        sidebar.setPadding(new Insets(20));
        sidebar.setPrefWidth(220);

        return sidebar;
    }

    // ---------- HEADER ----------
    private HBox createHeader(){

        Label welcome = new Label("Welcome, " + currentCompanyName);
        welcome.setStyle("-fx-font-size:18; -fx-font-weight:bold;");

        HBox header = new HBox(welcome);
        header.setPadding(new Insets(20));
        header.getStyleClass().add("header");

        return header;
    }

    // ---------- MAIN ----------
    private VBox createMainContent(){

        JobDAO jobDAO = new JobDAO();
        ApplicationDAO appDAO = new ApplicationDAO();

        int jobs = jobDAO.getJobCountByCompany(currentCompanyId);
        int applications = appDAO.getApplicationCountByCompany(currentCompanyId);
        int selected = appDAO.getSelectedCountByCompany(currentCompanyId);

        HBox cards = new HBox(20,
                createCard("Jobs Posted", String.valueOf(jobs)),
                createCard("Applications", String.valueOf(applications)),
                createCard("Selected", String.valueOf(selected))
        );

        cards.setPadding(new Insets(20));

        VBox main = new VBox(30, cards);
        main.setPadding(new Insets(20));

        return main;
    }

    // ---------- POST JOB PAGE ----------
    private VBox createPostJobPage(){

        TextField title = new TextField();
        title.setPromptText("Job Title");

        TextField location = new TextField();
        location.setPromptText("Location");

        TextField salary = new TextField();
        salary.setPromptText("Salary");

        Button post = new Button("Post Job");
        post.getStyleClass().add("apply-btn");

        post.setOnAction(e -> {

            JobDAO dao = new JobDAO();
            dao.insertJob(title.getText(), location.getText(), salary.getText(), currentCompanyId);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Job Posted!");
            alert.show();
        });

        VBox form = new VBox(15, title, location, salary, post);
        form.setPadding(new Insets(30));
        form.getStyleClass().add("glass-card");

        return new VBox(form);
    }

    // ---------- MY JOBS ----------
    private VBox createMyJobsPage(){

        JobDAO dao = new JobDAO();
        List<Job> jobs = dao.getJobsByCompany(currentCompanyId);

        FlowPane jobList = new FlowPane();
        jobList.setHgap(25);
        jobList.setVgap(25);
        jobList.setPadding(new Insets(20));

        for(Job job : jobs){
            jobList.getChildren().add(createJobCard(job));
        }

        ScrollPane scroll = new ScrollPane(jobList);
        scroll.setFitToWidth(true);

        return new VBox(scroll);
    }

    // ---------- JOB CARD ----------
    private VBox createJobCard(Job job){

        Label title = new Label(job.getTitle());
        title.getStyleClass().add("job-title");

        Label location = new Label(job.getLocation());
        Label salary = new Label(job.getSalary());

        VBox card = new VBox(10, title, location, salary);
        card.getStyleClass().add("job-card");
        card.setPadding(new Insets(15));

        return card;
    }

    // ---------- CARD ----------
    private VBox createCard(String title, String value){

        Label t = new Label(title);
        Label v = new Label(value);

        v.setStyle("-fx-font-size:24px; -fx-font-weight:bold;");

        VBox card = new VBox(10, t, v);
        card.getStyleClass().add("card");

        return card;
    }

    // ---------- LAYOUT ----------
    private BorderPane getLayout(Stage stage, VBox center){

        BorderPane root = new BorderPane();

        root.setLeft(createSidebar(stage));
        root.setTop(createHeader());
        root.setCenter(center);

        return root;
    }
    private VBox createApplicantsPage(){

        ApplicationDAO dao = new ApplicationDAO();
        List<String[]> applicants = dao.getApplicantsByCompany(currentCompanyId);

        FlowPane container = new FlowPane();
        container.setHgap(25);
        container.setVgap(25);
        container.setPadding(new Insets(20));

        for(String[] app : applicants){

            int studentId = Integer.parseInt(app[0]);
            int jobId = Integer.parseInt(app[1]);

            container.getChildren().add(
                    createApplicantCard(
                            studentId,
                            jobId,
                            app[2],
                            app[3],
                            app[4],
                            app[5]
                    )
            );
        }

        ScrollPane scroll = new ScrollPane(container);
        scroll.setFitToWidth(true);

        return new VBox(scroll);
    }
private VBox createApplicantCard(int studentId, int jobId,
                                 String name, String email,
                                 String job, String status){

    Label studentName = new Label(name);
    studentName.getStyleClass().add("job-title");

    Label studentEmail = new Label(email);
    Label jobTitle = new Label("Applied for: " + job);

    Label statusLabel = new Label(status);

    Button accept = new Button("Accept");
    Button reject = new Button("Reject");

    ApplicationDAO dao = new ApplicationDAO();

    accept.setOnAction(e -> {
        dao.updateStatus(studentId, jobId, "Selected");
        statusLabel.setText("Selected");
    });

    reject.setOnAction(e -> {
        dao.updateStatus(studentId, jobId, "Rejected");
        statusLabel.setText("Rejected");
    });

    HBox buttons = new HBox(10, accept, reject);

    VBox card = new VBox(10,
            studentName,
            studentEmail,
            jobTitle,
            statusLabel,
            buttons
    );

    card.getStyleClass().add("glass-card");
    card.setPadding(new Insets(15));
    card.setPrefWidth(250);

    return card;
}
}