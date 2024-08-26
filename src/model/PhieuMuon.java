/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

public class PhieuMuon {
    String maPhieuMuon;
    String maSinhVien;
    String maSach;
    int soLuong;
    String ngayMuon;
    String ngayHetHan;

    public PhieuMuon() {
    }

    public PhieuMuon(String maPhieuMuon, String maSinhVien, String maSach, int soLuong, String ngayMuon, String ngayHetHan) {
        this.maPhieuMuon = maPhieuMuon;
        this.maSinhVien = maSinhVien;
        this.maSach = maSach;
        this.soLuong = soLuong;
        this.ngayMuon = ngayMuon;
        this.ngayHetHan = ngayHetHan;
    }

    public String getMaPhieuMuon() {
        return maPhieuMuon;
    }

    public void setMaPhieuMuon(String maPhieuMuon) {
        this.maPhieuMuon = maPhieuMuon;
    }

    public String getMaSinhVien() {
        return maSinhVien;
    }

    public void setMaSinhVien(String maSinhVien) {
        this.maSinhVien = maSinhVien;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(String ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public String getNgayHetHan() {
        return ngayHetHan;
    }

    public void setNgayHetHan(String ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }
        
}