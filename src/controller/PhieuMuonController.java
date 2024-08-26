/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.SocketOptions;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.PhieuMuon;
import util.ConnectDB;

/**
 *
 * @author OS
 */
public class PhieuMuonController {

//    public int insert(PhieuMuon pm) {
//        int result = 0;
//        try {
//
//            String sql = "Insert into PhieuMuon values(?,?,?,?)";
//            Connection con = ConnectDB.connectSQLServer();
//            CallableStatement ps = con.prepareCall(sql);
//            ps.setString(1, pm.getMaPhieuMuon());
//            ps.setString(2, pm.getMaSinhVien());
//            ps.setString(3, pm.getNgayMuon());
//            ps.setString(4, pm.getNgayHetHan());
//            result = ps.executeUpdate();
//        } catch (SQLException ex) {
//            Logger.getLogger(PhieuMuonController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return result;
//
//    }

    public int update(PhieuMuon pm) {
        int result = 0;
        try {
            String sql = "Update PhieuMuon SET  MaSV = ?, MaSach =?, SoLuong = ?, NgayMuon = ?, NgayHenTra  = ? WHERE MaPhieuMuon = ? ";
            String sqlSelects = "SELECT SoLuong FROM sach WHERE MaSach = ?";
            String sqlSelectpm = "SELECT SoLuong from PhieuMuon WHERE MaSach = ?";
            String sqlupdate = "Update sach set SoLuong = ? where MaSach = ?";
            try (Connection con = ConnectDB.connectSQLServer(); PreparedStatement ps = con.prepareCall(sql);
                    PreparedStatement psSelects = con.prepareCall(sqlSelects);
                    PreparedStatement psSelectpm = con.prepareCall(sqlSelectpm);
                    PreparedStatement psUpdate = con.prepareCall(sqlupdate); 
                    ) {

                ps.setString(1, pm.getMaSinhVien());
                ps.setString(2, pm.getMaSach());
                ps.setString(3, Integer.toString(pm.getSoLuong()));
                ps.setString(4, pm.getNgayMuon());
                ps.setString(5, pm.getNgayHetHan());
                ps.setString(6, pm.getMaPhieuMuon());
                psSelects.setString(1,pm.getMaSach());
                psSelectpm.setString(1,pm.getMaSach());
                ResultSet rs = psSelects.executeQuery();
                ResultSet rspm = psSelectpm.executeQuery();
                if (rs.next()) {
                    String soLuong = rs.getString("SoLuong");
                    int sl = Integer.parseInt(soLuong);
//                    sl = sl - pm.getSoLuong();
                    if(rspm.next()){
                        String Soluongpm = rspm.getString("SoLuong");
                        int slpm = Integer.parseInt(Soluongpm);
                        sl = sl + slpm - pm.getSoLuong();
                        if(sl >= 0){
                            psUpdate.setString(1, Integer.toString(sl)); 
                            psUpdate.setString(2, pm.getMaSach());
                            psUpdate.executeUpdate();
                            result = ps.executeUpdate();
//                       
                        }
                        else{
                            result =0;
                        }
                    }
                }
                

            }
        } catch (Exception ex) {
            System.err.println("Lỗi: " + ex);
        }
        return result;
    }

    public int delete(String maPhieuMuon) {
        int result = 0;
        try {
            String sql = "DELETE FROM PhieuMuon WHERE MaPhieuMuon=?";
            try (Connection con = ConnectDB.connectSQLServer(); PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, maPhieuMuon);
                result = ps.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public ArrayList<PhieuMuon> SearchMaSVMaPhieu(String ma) {
        ArrayList<PhieuMuon> lists = new ArrayList<PhieuMuon>();
        try {
           String sql = "SELECT * FROM PhieuMuon WHERE MaSV like '%" + ma + "%'  or MaPhieuMuon like '%" + ma + "%'or SoLuong like '%" 
                   + ma + "%' or MaSach like '%" + ma + "%' or NgayMuon like '%" + ma + "%' or NgayHenTra like '%" + ma + "%'";
 

            Connection con = ConnectDB.connectSQLServer();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                PhieuMuon pm = new PhieuMuon();
                pm.setMaPhieuMuon(rs.getString(1));
                pm.setMaSinhVien(rs.getString(2));
                pm.setMaSach(rs.getString(3));
                pm.setSoLuong(Integer.parseInt(rs.getString(4)));

                pm.setNgayMuon(rs.getString(5));
                pm.setNgayHetHan(rs.getString(6));
                lists.add(pm);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lists;
    }
    

    public ArrayList listPhieuMuon() {
        ArrayList<PhieuMuon> lists = new ArrayList<>();
        try {
            String sql = "SELECT * from PhieuMuon";
            try (Connection con = ConnectDB.connectSQLServer(); Statement stm = con.createStatement();) {
                ResultSet rs = stm.executeQuery(sql);
                while (rs.next()) {
                    PhieuMuon pm = new PhieuMuon();
                    pm.setMaPhieuMuon(rs.getString(1));
                    pm.setMaSinhVien(rs.getString(2));
                    pm.setMaSach(rs.getString(3));
                    pm.setSoLuong(Integer.parseInt(rs.getString(4)));
                    pm.setNgayMuon(rs.getString(5));
                    pm.setNgayHetHan(rs.getString(6));
                    lists.add(pm);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SinhVienController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lists;
    }

    public int themMoiPhieuMuon(PhieuMuon pm) {
        int results = 0;
        System.out.println(pm.getMaPhieuMuon());
        String sql = "Insert into phieumuon values(?,?,?,?,?,?)";
        String sqlSelects = "SELECT SoLuong FROM sach WHERE MaSach = ?";
//        String sqlSelectpm = "SELECT SoLuong from PhieuMuon WHERE MaSach = ?";
        String sqlupdate = "Update sach set SoLuong = ? where MaSach = ?";
        try (Connection con = ConnectDB.connectSQLServer(); PreparedStatement ps = con.prepareStatement(sql);
                PreparedStatement psSelects = con.prepareCall(sqlSelects);
//                PreparedStatement psSelectpm = con.prepareCall(sqlSelectpm);
                PreparedStatement psUpdate = con.prepareCall(sqlupdate); 
                ) {
            ps.setString(1, pm.getMaPhieuMuon());
            ps.setString(2, pm.getMaSinhVien());
            ps.setString(3, pm.getMaSach());
            ps.setString(4, Integer.toString(pm.getSoLuong()));
            ps.setString(5, pm.getNgayMuon());
            ps.setString(6, pm.getNgayHetHan());
            
            psSelects.setString(1,pm.getMaSach());
//            psSelectpm.setString(1,pm.getMaSach());
            ResultSet rs = psSelects.executeQuery();
//            ResultSet rspm = psSelectpm.executeQuery();
            if (rs.next()) {
                String soLuong = rs.getString("SoLuong");
                int sl = Integer.parseInt(soLuong);
                sl = sl - pm.getSoLuong();
                if(sl >= 0){
                    psUpdate.setString(1, Integer.toString(sl)); 
                    psUpdate.setString(2, pm.getMaSach());
                    psUpdate.executeUpdate();
                    results = ps.executeUpdate();
                }
                else{results =0;}
            }
            
            
            

        } catch (SQLException ex) {
            Logger.getLogger(PhieuMuonController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return results ;
    }
    

    public boolean xoaDuLieuPhieuMuon(String maPhieuMuon) {
        String sql = "delete from PhieuMuon where MaPhieuMuon = '" + maPhieuMuon + "'";
//        string sqlpm = "Select "
        try (Connection connection = util.ConnectDB.connectSQLServer(); Statement stmt = connection.createStatement()) {
            int row = stmt.executeUpdate(sql);
            if (row != 0) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
        return false;
    }

    public String layMaPMTheoMaSV(String maSv) {
        String sql = "select maphieumuon from phieumuon where masv = '" + maSv + "' ";
        String result = null;
        try {
            try (Connection conn = util.ConnectDB.connectSQLServer(); Statement stm = conn.createStatement()) {
                ResultSet rs = stm.executeQuery(sql);
                if (rs.next()) {
                    result = rs.getString(1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PhieuMuonController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
