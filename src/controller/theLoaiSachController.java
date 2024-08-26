/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.TheLoaiSach;
import util.ConnectDB;

/**
 *
 * @author OS
 */
public class theLoaiSachController {
    public String layTenTheoMa(String maTheLoai) {
        
        String tlSach = null;
        String sql = "select TenTheLoai from theloaisach where MaTheLoai=?";
        try {
            Connection conn = util.ConnectDB.connectSQLServer();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, maTheLoai);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                tlSach = rs.getString(1);
            }
            pstm.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(LopController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tlSach;
    }
    public String layMaTheoTen(String tenTL) {
        
        String maTheLoai = null;
        String sql = "select maTheLoai from theloaisach where tenTheLoai=?";
        try {
            Connection conn = util.ConnectDB.connectSQLServer();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, tenTL);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                maTheLoai = rs.getString(1);
            }
            pstm.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(LopController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return maTheLoai;
    }
    
    public ArrayList<TheLoaiSach> Search(String ma){
        ArrayList<TheLoaiSach> lists = new ArrayList<>();
        try {
            String sql = "SELECT * FROM theloaisach WHERE MatheLoai like '%" + ma + "%' or TenTheLoai like '%" + ma + "%'or ViTri like '%" + ma + "%'";
            try (Connection con = ConnectDB.connectSQLServer(); PreparedStatement ps = con.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    TheLoaiSach tls = new TheLoaiSach();
                    tls.setMaTheLoai(rs.getString(1));
                    tls.setTenTheLoai(rs.getString(2));
                    tls.setViTri(rs.getString(3));
                    lists.add(tls);
                }
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(ViPhamController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lists;
    }
    
    public ArrayList ListTheLoai(){
        String sql = "select * from TheLoaiSach";
        ArrayList list = new ArrayList();
        try{
            Connection connection = util.ConnectDB.connectSQLServer();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                list.add(new TheLoaiSach(rs.getString(1),rs.getString(2),rs.getString(3)));

            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return list;
    }
    
    public boolean themMoiTheLoai(String maTheLoai,String tenTheLoai,String viTri){
        String sql = "Insert into TheLoaiSach values(?,?,?)";
        try (Connection connection = util.ConnectDB.connectSQLServer(); PreparedStatement prepareStatement = connection.prepareStatement(sql)){
            prepareStatement.setString(1,maTheLoai);
            prepareStatement.setString(2,tenTheLoai);
            prepareStatement.setString(3,viTri);
            int row = prepareStatement.executeUpdate();
            if(row !=0){
                return true;
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return false;

    }
    
    
    
    public boolean xoaDuLieuTheLoai(String maTheLoai){
        String sql = "delete from TheLoaiSach where MaTheLoai = '"+ maTheLoai+"'";
        try (Connection connection = util.ConnectDB.connectSQLServer(); Statement stmt = connection.createStatement()){
           int row= stmt.executeUpdate(sql);
            if(row !=0){
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
        return false;
    }
    
    
    
    public int update(TheLoaiSach tls) {
        int result = 0;
        try {

            String sql = "Update theloaisach set MaTheLoai = ? , TenTheloai = ? , ViTri = ? Where MaTheLoai = ? or TenTheloai = ?";
            try (Connection con = ConnectDB.connectSQLServer(); PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, tls.getMaTheLoai());
                ps.setString(2, tls.getTenTheLoai());
                ps.setString(3, tls.getViTri());
                ps.setString(4, tls.getMaTheLoai());
                ps.setString(5, tls.getTenTheLoai());
                result = ps.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ViPhamController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;

    }
}
