package dao;

import model.Student;
import util.DBConnection;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class StudentDAO {

    public boolean insertStudent(Student student) {

        String query = "INSERT INTO student(name,email,password,cgpa,branch,backlogs,skills) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setString(3, student.getPassword());
            ps.setDouble(4, student.getCgpa());
            ps.setString(5, student.getBranch());
            ps.setInt(6, student.getBacklogs());
            ps.setString(7, student.getSkills());

            ps.executeUpdate();

            System.out.println("Student inserted successfully!");

            return true;

        } catch (Exception e) {

            e.printStackTrace();
            return false;

        }

    }
    public Student loginStudent(String email, String password) {

        String query = "SELECT * FROM student WHERE email=? AND password=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    Student s = new Student();

                    s.setId(rs.getInt("id"));
                    s.setName(rs.getString("name"));
                    s.setEmail(rs.getString("email"));
                    s.setPassword(rs.getString("password"));
                    s.setCgpa(rs.getDouble("cgpa"));
                    s.setBranch(rs.getString("branch"));
                    s.setBacklogs(rs.getInt("backlogs"));
                    s.setSkills(rs.getString("skills"));

                    return s;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public boolean resetPassword(String email, String newPassword){

        String query = "UPDATE student SET password=? WHERE email=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1,newPassword);
            ps.setString(2,email);

            int rows = ps.executeUpdate();

            return rows>0;

        } catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean rememberUser(String email){

        String query = "UPDATE student SET remember=1 WHERE email=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, email);

            ps.executeUpdate();

            return true;

        } catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public Student getRememberedUser(){

        String query = "SELECT * FROM student WHERE remember=1 LIMIT 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            if(rs.next()){

                Student s = new Student();

                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setEmail(rs.getString("email"));
                s.setPassword(rs.getString("password"));
                s.setCgpa(rs.getDouble("cgpa"));
                s.setBranch(rs.getString("branch"));
                s.setBacklogs(rs.getInt("backlogs"));
                s.setSkills(rs.getString("skills"));

                return s;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public void clearRememberedUser(){

        String query = "UPDATE student SET remember=0";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.executeUpdate();

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public Student getStudentById(int id) {
        String query = "SELECT * FROM student WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Student s = new Student();
                    s.setId(rs.getInt("id"));
                    s.setName(rs.getString("name"));
                    s.setEmail(rs.getString("email"));
                    s.setPassword(rs.getString("password"));
                    s.setCgpa(rs.getDouble("cgpa"));
                    s.setBranch(rs.getString("branch"));
                    s.setBacklogs(rs.getInt("backlogs"));
                    s.setSkills(rs.getString("skills"));
                    return s;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}