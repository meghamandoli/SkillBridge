package dao;

import util.DBConnection;

import java.sql.*;

public class UserDAO {

    public int getStudentCount(){
        int count = 0;
        try{
            Connection con = DBConnection.getConnection();
            String query = "SELECT COUNT(*) FROM users WHERE role='student'";
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

    public int getRecruiterCount(){
        int count = 0;
        try{
            Connection con = DBConnection.getConnection();
            String query = "SELECT COUNT(*) FROM users WHERE role='company'";
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