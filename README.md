SkillBridge is a comprehensive desktop-based job portal application built with JavaFX and MySQL. It serves as a modern platform connecting students with potential employers, enabling students to explore job listings, verify their eligibility in real-time, and seamlessly apply for positions directly through the application.

- **Student Dashboard:** A centralized, interactive dashboard presenting key statistics, the latest job listings, and application status tracking.
- **Job Browsing & Intelligent Eligibility:** Students can easily view available jobs with clearly defined criteria (minimum CGPA, branch constraints, required skills, and backlog limits). The system instantly validates a student's profile against a job's requirements to govern their application eligibility.
- **Application Tracking:** Students can manage current applications and check their precise status within the hiring pipeline (e.g., Applied, Selected, Rejected).
- **Profile Management:** Users can explore their profile details and application history under the profile sectipn.
- **Database Integration:** Utilizes MySQL for robust, reliable data persistence of users, jobs, and applications.

- **Frontend/GUI:** JavaFX (Controls, FXML)
- **Backend:** Java 21
- **Database:** MySQL 8
- **Build Tool:** Maven

To run this application, ensure you have the following installed on your system:

- **Java Development Kit (JDK):** Version 21 or higher.
- **MySQL Server:** Version 8.0 or higher.
- **Maven:** For managing dependencies and building the project.

1. Start your local MySQL server.
2. Initialize the database schema and populate initial dummy data by running the provided SQL dump (`skillbridge.sql`):
   ```bash
   mysql -u <your_mysql_username> -p < skillbridge.sql
   ```
3. Update the database credentials to match your local setup. Open the `util.DBConnection` class (or equivalent where the connection is managed) and ensure the DB URL, username, and password fields are correct.

Open a terminal in the root directory of the project and use Maven to download dependencies and compile the source code:
```bash
mvn clean install
```

You can execute the application either directly from your IDE by running the `org.example.Main` file, or via Maven using the JavaFX plugin:
```bash
mvn javafx:run
```
