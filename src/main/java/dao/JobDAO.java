package dao;

import java.sql.*;
import java.util.*;
import java.util.List;
import java.util.ArrayList;
import model.Job;
import util.DBConnection;
public class JobDAO {

    public List<Job> getAllJobs(){

        List<Job> jobs = new ArrayList<>();
        String query =
                "SELECT j.id, j.title, c.name, j.location, j.salary, j.min_cgpa, j.branch, j.no_backlogs, j.skills " +
                        "FROM job j JOIN company c ON j.company_id = c.id";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()){
                jobs.add(new Job(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("name"),
                        rs.getString("location"),
                        rs.getString("salary"),
                        rs.getDouble("min_cgpa"),
                        rs.getString("branch"),
                        rs.getBoolean("no_backlogs"),
                        rs.getString("skills")
                ));
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return jobs;
    }
    public int getJobCount(){
        int count = 0;
        String query = "SELECT COUNT(*) FROM job";
        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery()){
            
            if(rs.next()){
                count = rs.getInt(1);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return count;
    }
    public void insertJob(String title, String location, String salary, int companyId, double minCgpa, String branch, boolean noBacklogs, String skills){
        String query = "INSERT INTO job(title, location, salary, company_id, min_cgpa, branch, no_backlogs, skills) VALUES (?,?,?,?,?,?,?,?)";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){

            ps.setString(1, title);
            ps.setString(2, location);
            ps.setString(3, salary);
            ps.setInt(4, companyId);
            ps.setDouble(5, minCgpa);
            ps.setString(6, branch);
            ps.setBoolean(7, noBacklogs);
            ps.setString(8, skills);

            ps.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public List<Job> getJobsByCompany(int companyId){

        List<Job> list = new ArrayList<>();
        String query = "SELECT * FROM job WHERE company_id=?";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){

            ps.setInt(1, companyId);

            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    Job job = new Job();

                    job.setId(rs.getInt("id"));
                    job.setTitle(rs.getString("title"));
                    job.setLocation(rs.getString("location"));
                    job.setSalary(rs.getString("salary"));
                    job.setMinCgpa(rs.getDouble("min_cgpa"));
                    job.setBranch(rs.getString("branch"));
                    job.setNoBacklogs(rs.getBoolean("no_backlogs"));
                    job.setSkills(rs.getString("skills"));

                    list.add(job);
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return list;
    }
    public int getJobCountByCompany(int companyId){

        int count = 0;
        String query = "SELECT COUNT(*) FROM job WHERE company_id=?";

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
}
