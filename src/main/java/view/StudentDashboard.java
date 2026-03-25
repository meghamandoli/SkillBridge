package view;

import dao.ApplicationDAO;
import dao.JobDAO;
import dao.UserDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Job;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class StudentDashboard {
    private String currentStudentName;
    private int currentStudentId;
    public void show(Stage stage, String studentName, int studentId){
        this.currentStudentName = studentName;
        this.currentStudentId = studentId;
        BorderPane root = new BorderPane();
 // temporary
        root.setLeft(createSidebar(stage, studentId));
        root.setTop(createHeader(studentName));
        // TEMP (later from login)
        root.setCenter(createMainContent(studentId));
        Scene scene = new Scene(root,1200,700);
        scene.getStylesheets().add(getClass().getResource("/dashboard.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("SkillBridge Dashboard");
        stage.show();

    }
    public BorderPane getDashboardLayout(Stage stage, int studentId){

        BorderPane root = new BorderPane();

        root.setLeft(createSidebar(stage, studentId));
        root.setTop(createHeader(currentStudentName));
        root.setCenter(createMainContent(studentId));

        return root;
    }

    // ---------- SIDEBAR ----------
    private VBox createSidebar(Stage stage, int studentId){

        Label logo = new Label("SkillBridge");

        Button dashboard = new Button("Dashboard");
        Button jobs = new Button("Browse Jobs");
        Button applications = new Button("My Applications");
        Button profile = new Button("Profile");
        Button alerts = new Button("Alerts");
        Button logout = new Button("Logout");
        dashboard.setOnAction(e -> {
            stage.getScene().setRoot(
                    new StudentDashboard().getDashboardLayout(stage, studentId)
            );
        });

        jobs.setOnAction(e -> {
            BorderPane root = new BorderPane();
            root.setLeft(createSidebar(stage, studentId));
            root.setTop(createHeader(currentStudentName));
            root.setCenter(createJobsPage(studentId)); // ⭐ FIX HERE
            stage.getScene().setRoot(root);
        });

        applications.setOnAction(e -> {
            BorderPane root = new BorderPane();
            root.setLeft(createSidebar(stage, studentId));
            root.setTop(createHeader("User"));
            root.setCenter(createApplicationsPage(studentId));
            stage.getScene().setRoot(root);
        });
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

        dashboard.getStyleClass().add("menu-button-active");
        profile.setOnAction(e -> {
            BorderPane root = new BorderPane();
            root.setLeft(createSidebar(stage, studentId));
            root.setTop(createHeader(currentStudentName));
            root.setCenter(createProfilePage(studentId)); // ⭐ REAL DATA
            stage.getScene().setRoot(root);
        });

        alerts.setOnAction(e -> {
            VBox box = new VBox(10);
            box.setPadding(new Insets(20));

            box.getChildren().add(new Label("Alerts Page"));

            BorderPane root = new BorderPane();
            root.setLeft(createSidebar(stage, studentId));
            root.setTop(createHeader(currentStudentName));
            root.setCenter(box);

            stage.getScene().setRoot(root);
        });

        logout.setOnAction(e -> {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Logout");
            alert.setHeaderText(null);
            alert.setContentText("Logging you out...\nHope you liked SkillBridge 🚀");

            alert.showAndWait();

            // After alert → go to login screen
            new LoginView().show(stage);  // ⭐ IMPORTANT
        });
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

        header.getStyleClass().add("header");

        return header;
    }

    // ---------- MAIN CONTENT ----------
    private VBox createMainContent(int studentId){

        // Top cards
        JobDAO jobDAO = new JobDAO();
        ApplicationDAO appDAO = new ApplicationDAO();
        int totalStudents = getStudentCount();
        int totalRecruiters = getCompanyCount();

        int totalJobs = jobDAO.getJobCount();
        int myApplications = appDAO.getApplicationCount(studentId);

        HBox cards = new HBox(20,
                createCard("Students", String.valueOf(totalStudents)),
                createCard("Recruiters", String.valueOf(totalRecruiters)),
                createCard("Open Jobs", String.valueOf(totalJobs)),
                createCard("Applications", String.valueOf(myApplications))
        );

        cards.setPadding(new Insets(20));

        // Fetch jobs from Dao
        List<Job> jobListData = jobDAO.getAllJobs();

        // Job container (VERTICAL)
        FlowPane jobList = new FlowPane();
        jobList.setHgap(25);
        jobList.setVgap(25);
        jobList.setPadding(new Insets(20));
        jobList.setPrefWrapLength(900); // controls how many cards per row


        for(Job job : jobListData){
            jobList.getChildren().add(
                    createJobCard(job, studentId)
            );
        }

        // Scroll pane (IMPORTANT)
        ScrollPane scroll = new ScrollPane(jobList);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        jobList.setStyle("-fx-background-color: transparent;");
        Label latestTitle = new Label("Latest Job Listings");
        latestTitle.setStyle("-fx-font-size:18px; -fx-font-weight:bold;");

        VBox jobsSection = new VBox(15, latestTitle, scroll);
        jobsSection.setPadding(new Insets(30, 20, 20, 20));

        VBox main = new VBox(20, cards, jobsSection);

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
    private VBox createJobCard(Job job, int studentId){

        // Title
        Label jobTitle = new Label(job.getTitle());
        jobTitle.getStyleClass().add("job-title");

        // Company + location
        Label companyLabel = new Label(job.getCompany() + " • " + job.getLocation());
        companyLabel.getStyleClass().add("job-company");

        // Tags
        Label salaryTag = new Label(job.getSalary());
        salaryTag.getStyleClass().add("tag");

        Label locationTag = new Label(job.getLocation());
        locationTag.getStyleClass().add("tag");

        HBox tags = new HBox(8, salaryTag, locationTag);

        Label reqTag = new Label("Req: " + job.getMinCgpa() + " CGPA");
        reqTag.setStyle("-fx-font-size:11px; -fx-text-fill: #999;");

        // Apply Button

        Button apply = new Button("Apply");
        ApplicationDAO appDao = new ApplicationDAO();
        model.Student student = new dao.StudentDAO().getStudentById(studentId);

        boolean isEligible = true;
        String reason = "Not Eligible";

        if (student != null) {
            if (student.getCgpa() < job.getMinCgpa()) {
                isEligible = false;
                reason = "Low CGPA";
            } else if (job.isNoBacklogs() && student.getBacklogs() > 0) {
                isEligible = false;
                reason = "Active Backlogs";
            } else if (job.getBranch() != null && !job.getBranch().isEmpty() && !job.getBranch().equalsIgnoreCase("Any") && !job.getBranch().equalsIgnoreCase(student.getBranch())) {
                isEligible = false;
                reason = "Branch Mismatch";
            } else if (job.getSkills() != null && !job.getSkills().trim().isEmpty()) {
                String[] requiredSkills = job.getSkills().split(",");
                String studentSkillsStr = student.getSkills() != null ? student.getSkills().toLowerCase() : "";
                for (String req : requiredSkills) {
                    if (!studentSkillsStr.contains(req.trim().toLowerCase())) {
                        isEligible = false;
                        reason = "Missing Skill";
                        break;
                    }
                }
            }
        }

        if(appDao.alreadyApplied(studentId, job.getId())){
            apply.setDisable(true);
            apply.setText("Applied");
        } else if (!isEligible) {
            apply.setDisable(true);
            apply.setText(reason);
            apply.setStyle("-fx-background-color: #555;");
        }
        apply.getStyleClass().add("apply-btn");

        apply.setOnAction(e -> {
//            ApplicationDAO dao = new ApplicationDAO();
            boolean success = appDao.applyJob(studentId, job.getId());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            if(success){
                alert.setContentText("Applied Successfully!");
                apply.setDisable(true);
                apply.setText("Applied");
            } else {
                alert.setContentText("Error!");
            }

            alert.show();
        });

        // Bottom layout
        HBox bottom = new HBox(apply);
        bottom.setAlignment(Pos.CENTER_RIGHT);

        // FINAL CARD
        VBox card = new VBox(12);
        card.getStyleClass().add("job-card");
        card.setPrefWidth(230);
        card.setPadding(new Insets(15));

        // ⭐ MOST IMPORTANT LINE (YOU MISSED THIS)
        card.getChildren().addAll(jobTitle, companyLabel, tags, reqTag, bottom);
        card.setOnMouseEntered(e -> {
            card.setStyle("-fx-scale-x:1.05; -fx-scale-y:1.05;");
        });

        card.setOnMouseExited(e -> {
            card.setStyle("-fx-scale-x:1; -fx-scale-y:1;");
        });
        return card;
    }
    private VBox createApplicationCard(String title, String company, String statusText){

        Label jobTitle = new Label(title);
        jobTitle.getStyleClass().add("job-title");

        Label companyLabel = new Label(company);
        companyLabel.getStyleClass().add("job-company");

        // Status Label
        Label status = new Label(statusText);

        // 🔥 Dynamic color
        if(statusText.equalsIgnoreCase("Applied")){
            status.getStyleClass().add("status-applied");
        }else if(statusText.equalsIgnoreCase("Selected")){
            status.getStyleClass().add("status-selected");
        }else{
            status.getStyleClass().add("status-rejected");
        }

        Button viewBtn = new Button("View");
        viewBtn.setOnAction(e -> {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Application Details");
            alert.setHeaderText(title);

            alert.setContentText(
                    "Company: " + company + "\n" +
                            "Status: " + statusText
            );

            alert.showAndWait();
        });
        viewBtn.getStyleClass().add("apply-btn");

        HBox bottom = new HBox(status, viewBtn);
        bottom.setSpacing(10);
        bottom.setAlignment(Pos.CENTER_RIGHT);

        VBox card = new VBox(10, jobTitle, companyLabel, bottom);
        card.getStyleClass().add("glass-card");
        card.setPrefWidth(240);
        card.setPadding(new Insets(18));
        card.setOnMouseEntered(e -> {
            card.setStyle("-fx-scale-x:1.05; -fx-scale-y:1.05;");
        });

        card.setOnMouseExited(e -> {
            card.setStyle("-fx-scale-x:1; -fx-scale-y:1;");
        });
        return card;
    }
    private VBox createApplicationsPage(int studentId){

        ApplicationDAO dao = new ApplicationDAO();
        List<String[]> apps = dao.getApplicationsByStudent(studentId);

        Label title = new Label("My Applications");
        title.setStyle("-fx-font-size:18px; -fx-font-weight:bold;");

        FlowPane cardContainer = new FlowPane();
        cardContainer.setHgap(30);
        cardContainer.setVgap(30);
        cardContainer.setPadding(new Insets(20));

        for(String[] app : apps){
            cardContainer.getChildren().add(
                    createApplicationCard(app[0], app[1], app[2])
            );
        }

        ScrollPane scroll = new ScrollPane(cardContainer);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        VBox layout = new VBox(15, title, scroll);
        layout.setPadding(new Insets(20));

        return layout;
    }
    private VBox createProfilePage(int studentId){

        VBox main = new VBox(20);
        main.setPadding(new Insets(20));

        // ---------- USER INFO ----------
        VBox profileCard = new VBox(10);
        profileCard.getStyleClass().add("glass-card");

        Label name = new Label();
        name.getStyleClass().add("job-title");

        Label email = new Label();
        Label branch = new Label();
        Label cgpa = new Label();

        try{
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM student WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, studentId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                name.setText(rs.getString("name"));
                email.setText("Email: " + rs.getString("email"));
                branch.setText("Branch: " + rs.getString("branch"));
                cgpa.setText("CGPA: " + rs.getDouble("cgpa"));
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        profileCard.getChildren().addAll(name, email, branch, cgpa);

        // ---------- STATS ----------
        ApplicationDAO dao = new ApplicationDAO();

        int total = dao.getApplicationCount(studentId);
        int selected = dao.getStatusCount(studentId, "Selected");
        int rejected = dao.getStatusCount(studentId, "Rejected");

        HBox stats = new HBox(20,
                createCard("Total", String.valueOf(total)),
                createCard("Selected", String.valueOf(selected)),
                createCard("Rejected", String.valueOf(rejected))
        );

        // ---------- APPLICATIONS ----------
        Label appTitle = new Label("My Applications");
        appTitle.getStyleClass().add("job-title");

        List<String[]> apps = dao.getApplicationsByStudent(studentId);

        FlowPane appList = new FlowPane();
        appList.setHgap(25);
        appList.setVgap(25);

        for(String[] app : apps){
            appList.getChildren().add(
                    createApplicationCard(app[0], app[1], app[2])
            );
        }

        ScrollPane scroll = new ScrollPane(appList);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        main.getChildren().addAll(profileCard, stats, appTitle, scroll);

        return main;
    }
    private int getStudentCount(){
        int count = 0;
        try{
            Connection con = DBConnection.getConnection();
            String query = "SELECT COUNT(*) FROM student";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                count = rs.getInt(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return count;
    }
    private int getCompanyCount(){
        int count = 0;
        try{
            Connection con = DBConnection.getConnection();
            String query = "SELECT COUNT(*) FROM company";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                count = rs.getInt(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return count;
    }
    private VBox createJobsPage(int studentId){

        JobDAO jobDAO = new JobDAO();
        List<Job> jobListData = jobDAO.getAllJobs();

        Label title = new Label("Browse Jobs");
        title.setStyle("-fx-font-size:18px; -fx-font-weight:bold;");

        FlowPane jobList = new FlowPane();
        jobList.setHgap(25);
        jobList.setVgap(25);
        jobList.setPadding(new Insets(20));

        for(Job job : jobListData){
            jobList.getChildren().add(
                    createJobCard(job, studentId)
            );
        }

        ScrollPane scroll = new ScrollPane(jobList);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        VBox layout = new VBox(15, title, scroll);
        layout.setPadding(new Insets(20));

        return layout;
    }

}