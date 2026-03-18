package dao;

import model.Company;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CompanyDAO {

    public Company loginCompany(String email, String password){

        Company company = null;

        try{
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM company WHERE email=? AND password=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                company = new Company(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return company;
    }
}