/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

public class SachPhieuMuon {
    String ma;
    String maSach;
    String maPhieuMuon;

    public SachPhieuMuon() {
    }

    public SachPhieuMuon(String ma, String maSach, String maPhieuMuon) {
        this.ma = ma;
        this.maSach = maSach;
        this.maPhieuMuon = maPhieuMuon;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getMaPhieuMuon() {
        return maPhieuMuon;
    }

    public void setMaPhieuMuon(String maPhieuMuon) {
        this.maPhieuMuon = maPhieuMuon;
    }
    
    
}
