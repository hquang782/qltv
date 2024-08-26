
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
import model.ViPham;
import util.ConnectDB;

/**
 *
 * @author OS
 */
public class ViPhamController {
    
    
    public ArrayList<ViPham> danhSachTatCaViPham() {
        ArrayList<ViPham> lists = new ArrayList<ViPham>();
        try {
            String sql = "SELECT MaViPham, MaSV, Ten, Phat FROM vipham";
            try (Connection con = ConnectDB.connectSQLServer(); Statement stm = con.createStatement();) {
                ResultSet rs = stm.executeQuery(sql);
//            lists.clear();
                while (rs.next()) {
                    ViPham vp = new ViPham();
                    vp.setMaViPham(rs.getString(1));
                    vp.setMaSinhVien(rs.getString(2));
                    vp.setTen(rs.getString(3));
                    vp.setPhat(rs.getString(4));
                    lists.add(vp);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SinhVienController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lists;
    }

    public int insert(ViPham vp) {
        int result = 0;
        try {

            String sql = "Insert into ViPham values (?,?,?,?)";
            try (Connection con = ConnectDB.connectSQLServer(); PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, vp.getMaViPham());
                ps.setString(2, vp.getMaSinhVien());
                ps.setString(3, vp.getTen());
                ps.setString(4,vp.getPhat());
                result = ps.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ViPhamController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;

    }

    public int update(ViPham vp) {
    int result = 0;
    try {
        String sql = "Update vipham set MaSV = ?, Ten = ?, Phat = ? Where MaViPham = ?";
        try (Connection con = ConnectDB.connectSQLServer(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, vp.getMaSinhVien());
            ps.setString(2, vp.getTen());
            ps.setString(3, vp.getPhat());
            ps.setString(4, vp.getMaViPham());
            result = ps.executeUpdate();
        }
    } catch (SQLException ex) {
        Logger.getLogger(ViPhamController.class.getName()).log(Level.SEVERE, "Lỗi khi cập nhật dữ liệu: " + ex.getMessage(), ex);
    }
    return result;
}


    

    
    public int delete(String maViPham) {
        int result = 0;
        try {

            String sql = "DELETE FROM ViPham WHERE MaViPham=?";
            Connection con = ConnectDB.connectSQLServer();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maViPham);
            result = ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ViPhamController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public ArrayList<ViPham> SearchTen(String ma) {
        ArrayList<ViPham> lists = new ArrayList<>();
        try {
            String sql = "SELECT * FROM ViPham WHERE MaSV like '%" + ma + "%' or MaViPham like '%" + ma + "%'  or Ten like '%" + ma + "%' or Phat like '%" + ma + "%'";
            try (Connection con = ConnectDB.connectSQLServer(); PreparedStatement ps = con.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ViPham vp = new ViPham();
                    vp.setMaViPham(rs.getString(1));
                    vp.setMaSinhVien(rs.getString(2));
                    vp.setTen(rs.getString(3));
                    vp.setPhat(rs.getString(4));
                    lists.add(vp);
                }
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(ViPhamController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lists;
    }
        
        
        
}
