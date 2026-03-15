package dao;

import model.Student;
import util.DBConnection;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class StudentDAO {

    public boolean insertStudent(Student student) {

        try {

            Connection conn = DBConnection.getConnection();

            String query = "INSERT INTO student(name,email,password,cgpa,branch) VALUES (?,?,?,?,?)";

            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setString(3, student.getPassword());
            ps.setDouble(4, student.getCgpa());
            ps.setString(5, student.getBranch());

            ps.executeUpdate();

            System.out.println("Student inserted successfully!");

            return true;

        } catch (Exception e) {

            e.printStackTrace();
            return false;

        }

    }
    public Student loginStudent(String email, String password) {

        try {

            Connection conn = DBConnection.getConnection();

            String query = "SELECT * FROM student WHERE email=? AND password=?";

            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Student s = new Student();

                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setEmail(rs.getString("email"));
                s.setPassword(rs.getString("password"));
                s.setCgpa(rs.getDouble("cgpa"));
                s.setBranch(rs.getString("branch"));

                return s;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}