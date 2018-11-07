package com.example.dtanp.quanlinhansu.model;

import java.io.Serializable;

public class PhongBan implements Serializable{
    private String ma;
    private String ten;
    private String diaChi;
    private String soDienThoai;

    public PhongBan() {
    }

    public PhongBan(String ma, String ten, String diaChi, String soDienThoai) {
        this.ma = ma;
        this.ten = ten;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
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

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    @Override
    public String toString() {
        return this.ten;
    }
}
