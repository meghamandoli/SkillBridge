package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import util.DBConnection;
public class ApplicationDAO {

    public boolean applyJob(int studentId, int jobId){

        String query = "INSERT INTO application (student_id, job_id, status) VALUES (?, ?, ?)";
        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query)){

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
        String query = "SELECT COUNT(*) FROM application WHERE student_id=?";
        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query)){
            
            ps.setInt(1, studentId);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    count = rs.getInt(1);
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return count;
    }
    public List<String[]> getApplicationsByStudent(int studentId){

        List<String[]> list = new ArrayList<>();

        String query =
                "SELECT j.title, c.name, a.status " +
                        "FROM application a " +
                        "JOIN job j ON a.job_id = j.id " +
                        "JOIN company c ON j.company_id = c.id " +
                        "WHERE a.student_id=?";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query)){
            
            ps.setInt(1, studentId);

            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    String title = rs.getString("title");
                    String company = rs.getString("name");
                    String status = rs.getString("status");

                    list.add(new String[]{title, company, status});
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return list;
    }
    public boolean alreadyApplied(int studentId, int jobId){
        boolean exists = false;

        String query = "SELECT * FROM application WHERE student_id=? AND job_id=?";
        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setInt(1, studentId);
            ps.setInt(2, jobId);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    exists = true;
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return exists;
    }
    public int getStatusCount(int studentId, String status){
        int count = 0;

        String query = "SELECT COUNT(*) FROM application WHERE student_id=? AND status=?";
        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setInt(1, studentId);
            ps.setString(2, status);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    count = rs.getInt(1);
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return count;
    }
    public int getApplicationCountByCompany(int companyId){

        int count = 0;

        String query = "SELECT COUNT(*) FROM application a JOIN job j ON a.job_id=j.id WHERE j.company_id=?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){

            ps.setInt(1, companyId);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    count = rs.getInt(1);
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return count;
    }
    public int getSelectedCountByCompany(int companyId){

        int count = 0;

        String query = "SELECT COUNT(*) FROM application a JOIN job j ON a.job_id=j.id WHERE j.company_id=? AND a.status='Selected'";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){

            ps.setInt(1, companyId);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    count = rs.getInt(1);
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return count;
    }
    public List<String[]> getApplicantsByCompany(int companyId){

        List<String[]> list = new ArrayList<>();

        String query = "SELECT s.id AS student_id, j.id AS job_id, s.name, s.email, j.title, a.status " +
                "FROM application a " +
                "JOIN student s ON a.student_id = s.id " +
                "JOIN job j ON a.job_id = j.id " +
                "WHERE j.company_id=?";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            
            ps.setInt(1, companyId);

            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    list.add(new String[]{
                            String.valueOf(rs.getInt("student_id")), // 0
                            String.valueOf(rs.getInt("job_id")),     // 1
                            rs.getString("name"),                   // 2
                            rs.getString("email"),                  // 3
                            rs.getString("title"),                  // 4
                            rs.getString("status")                  // 5
                    });
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return list;
    }
    public void updateStatus(int studentId, int jobId, String status){

        String query = "UPDATE application SET status=? WHERE student_id=? AND job_id=?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){

            ps.setString(1, status);
            ps.setInt(2, studentId);
            ps.setInt(3, jobId);

            ps.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}