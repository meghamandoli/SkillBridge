package dao;

import model.Company;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CompanyDAO {

    public Company loginCompany(String email, String password){

        Company company = null;
        String query = "SELECT * FROM company WHERE email=? AND password=?";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setString(1, email);
            ps.setString(2, password);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    company = new Company(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password")
                    );
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return company;
    }
    public boolean resetPassword(String email, String newPassword){

        String query = "UPDATE company SET password=? WHERE email=?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){

            ps.setString(1, newPassword);
            ps.setString(2, email);

            int rows = ps.executeUpdate();

            return rows > 0;

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
    public void rememberCompany(String email){

        String query = "UPDATE company SET remember=1 WHERE email=?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){

            ps.setString(1, email);
            ps.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public Company getRememberedCompany(){

        String query = "SELECT * FROM company WHERE remember=1 LIMIT 1";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery()){

            if(rs.next()){
                return new Company(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }
    public boolean insertCompany(Company company){

        String query = "INSERT INTO company(name,email,password) VALUES (?,?,?)";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){

            ps.setString(1, company.getName());
            ps.setString(2, company.getEmail());
            ps.setString(3, company.getPassword());

            ps.executeUpdate();

            return true;

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
}