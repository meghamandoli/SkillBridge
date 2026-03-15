package dao;

import model.Student;
import util.DBConnection;

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

}