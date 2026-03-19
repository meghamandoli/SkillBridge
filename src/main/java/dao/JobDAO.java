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

        try(Connection con = DBConnection.getConnection()){

            String query =
                    "SELECT j.id, j.title, c.name, j.location, j.salary " +
                            "FROM job j JOIN company c ON j.company_id = c.id";

            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                jobs.add(new Job(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("name"),
                        rs.getString("location"),
                        rs.getString("salary")
                ));
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return jobs;
    }
    public int getJobCount(){
        int count = 0;
        try{
            Connection con = DBConnection.getConnection();
            String query = "SELECT COUNT(*) FROM job";
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
    public void insertJob(String title, String location, String salary, int companyId){

        try{
            Connection conn = DBConnection.getConnection();

            String query = "INSERT INTO job(title, location, salary, company_id) VALUES (?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, title);
            ps.setString(2, location);
            ps.setString(3, salary);
            ps.setInt(4, companyId);

            ps.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public List<Job> getJobsByCompany(int companyId){

        List<Job> list = new ArrayList<>();

        try{
            Connection conn = DBConnection.getConnection();

            String query = "SELECT * FROM job WHERE company_id=?";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, companyId);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Job job = new Job();

                job.setId(rs.getInt("id"));
                job.setTitle(rs.getString("title"));
                job.setLocation(rs.getString("location"));
                job.setSalary(rs.getString("salary"));

                list.add(job);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return list;
    }
    public int getJobCountByCompany(int companyId){

        int count = 0;

        try{
            Connection conn = DBConnection.getConnection();

            String query = "SELECT COUNT(*) FROM job WHERE company_id=?";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, companyId);

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
