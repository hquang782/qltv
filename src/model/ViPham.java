/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


public class ViPham {
    String maViPham;
    String maSinhVien;
    String ten;
    String phat;

    public ViPham() {
    }

    public ViPham(String maViPham, String maSinhVien, String ten, String phat) {
        this.maViPham = maViPham;
        this.maSinhVien = maSinhVien;
        this.ten = ten;
        this.phat = phat;
    }

    public String getMaViPham() {
        return maViPham;
    }

    public void setMaViPham(String maViPham) {
        this.maViPham = maViPham;
    }

    public String getMaSinhVien() {
        return maSinhVien;
    }

    public void setMaSinhVien(String maSinhVien) {
        this.maSinhVien = maSinhVien;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }
    
    public String getPhat() {
        return phat;
    }
    
    public void setPhat(String phat) {
         this.phat = phat;
    }

   
    
}
