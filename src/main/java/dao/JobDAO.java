package dao;

import java.sql.*;
import java.util.*;

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
}
