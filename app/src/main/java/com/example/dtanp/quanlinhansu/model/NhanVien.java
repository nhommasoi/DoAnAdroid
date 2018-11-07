package com.example.dtanp.quanlinhansu.model;

import java.io.Serializable;

public class NhanVien implements Serializable {
    private String ma;
    private String ten;
    private String maPhongBan;
    private byte[] image;
    private int gioiTinh;
    private String  ngaySinh;

    public NhanVien() {
    }

    public NhanVien(String ma, String ten, String maPhongBan, byte[] image, int gioiTinh, String ngaySinh) {
        this.ma = ma;
        this.ten = ten;
        this.maPhongBan = maPhongBan;
        this.image = image;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMaPhongBan() {
        return maPhongBan;
    }

    public void setMaPhongBan(String maPhongBan) {
        this.maPhongBan = maPhongBan;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(int gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }
}
