/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

public class TheLoaiSach {
    String maTheLoai;
    String tenTheLoai;
    String viTri;
    public TheLoaiSach(){
        this.maTheLoai = "";
        this.tenTheLoai ="";
        this.viTri = "";
    }
    public TheLoaiSach(String maTheLoai,String tenTheLoai,String viTri){
        this.maTheLoai = maTheLoai;
        this.tenTheLoai =tenTheLoai;
        this.viTri = viTri;
    }

    public String getMaTheLoai() {
        return maTheLoai;
    }

    public void setMaTheLoai(String maTheLoai) {
        this.maTheLoai = maTheLoai;
    }

    public String getTenTheLoai() {
        return tenTheLoai;
    }

    public void setTenTheLoai(String tenTheLoai) {
        this.tenTheLoai = tenTheLoai;
    }

    public String getViTri() {
        return viTri;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
    }
}
