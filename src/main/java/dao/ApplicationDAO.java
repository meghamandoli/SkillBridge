package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import util.DBConnection;
public class ApplicationDAO {

    public boolean applyJob(int studentId, int jobId){

        try(Connection con = DBConnection.getConnection()){

            String query = "INSERT INTO application (student_id, job_id, status) VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, studentId);
            ps.setInt(2, jobId);
            ps.setString(3, "Applied");

            int rows = ps.executeUpdate();

            return rows > 0;

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
    public int getApplicationCount(int studentId){
        int count = 0;
        try{
            Connection con = DBConnection.getConnection();
            String query = "SELECT COUNT(*) FROM application WHERE student_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, studentId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                count = rs.getInt(1);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return count;
    }
    public List<String[]> getApplicationsByStudent(int studentId){

        List<String[]> list = new ArrayList<>();

        try{
            Connection con = DBConnection.getConnection();

            String query =
                    "SELECT j.title, c.name, a.status " +
                            "FROM application a " +
                            "JOIN job j ON a.job_id = j.id " +
                            "JOIN company c ON j.company_id = c.id " +
                            "WHERE a.student_id=?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, studentId);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String title = rs.getString("title");
                String company = rs.getString("name");
                String status = rs.getString("status");

                list.add(new String[]{title, company, status});
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return list;
    }
    public boolean alreadyApplied(int studentId, int jobId){
        boolean exists = false;

        try{
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM application WHERE student_id=? AND job_id=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, studentId);
            ps.setInt(2, jobId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                exists = true;
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return exists;
    }
    public int getStatusCount(int studentId, String status){
        int count = 0;

        try{
            Connection con = DBConnection.getConnection();

            String query = "SELECT COUNT(*) FROM application WHERE student_id=? AND status=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, studentId);
            ps.setString(2, status);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                count = rs.getInt(1);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return count;
    }
}