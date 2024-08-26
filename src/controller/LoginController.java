/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Admin;
import util.ConnectDB;

/**
 *
 * @author ADMIN
 */
public class LoginController {

    public boolean dangNhapSinhVien(String sdt, String password) {
        boolean result = false;
        try {
            String sql = "select SDT, Password from sinhvien where sdt=? and password=?";
            try (Connection connection = util.ConnectDB.connectSQLServer(); PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
                prepareStatement.setString(1, sdt);
                prepareStatement.setString(2, password);
                ResultSet rs = prepareStatement.executeQuery();
                if (rs.next()) {
                    result = true;
                }
            } 
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);

        }
        return result;
    }
    
    public int updateAdmin(Admin ad) {
        int result = 0;
        try {
            String sql = "Update admin SET Password =? WHERE Username = ? ";
            try (Connection con = ConnectDB.connectSQLServer(); CallableStatement ps = con.prepareCall(sql)) {

                ps.setString(1, ad.getPassword());
                ps.setString(2, ad.getUsername());
                result = ps.executeUpdate();
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi: " + ex);
        }
        return result;
    }
    
    public ArrayList<Admin> GetAccount() {
        ArrayList<Admin> lists = new ArrayList<>();
        try {
            String sql = "SELECT * FROM admin";
            try (Connection con = ConnectDB.connectSQLServer(); Statement stm = con.createStatement();) {
                ResultSet rs = stm.executeQuery(sql);
//            lists.clear();
                while (rs.next()) {
                    Admin vp = new Admin();
                    vp.setUsername(rs.getString(1));
                    vp.setPassword(rs.getString(2));
                    vp.setTen(rs.getString(3));
                    lists.add(vp);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SinhVienController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lists;
    }
    
     public boolean dangNhapAdmin(String username, String password) {
        boolean result = false;
        try {
            String sql = "select Username, Password from admin where username=? and password=?";
            try (Connection connection = util.ConnectDB.connectSQLServer(); PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
                prepareStatement.setString(1, username);
                prepareStatement.setString(2, password);
                ResultSet rs = prepareStatement.executeQuery();
                if (rs.next()) {
                    result = true;
                }
           
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }

}
