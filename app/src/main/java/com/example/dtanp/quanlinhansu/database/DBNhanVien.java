package com.example.dtanp.quanlinhansu.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBNhanVien extends SQLiteOpenHelper {

    public static final String QUANLYNHANVIEN = "QuanLyNhanVien";
    public static final int VERSION = 1;
    public static final String TABLE_NHANVIEN = "nhanvien";
    public static final String NHANVIEN_ID = "ma";
    public static final String NHANVIEN_Ten = "ten";
    public static final String NHANVIEN_IMAGE= "image";
    public static final String NHANVIEN_PHONGBAN = "maphongban";
    public static final String NHANVIEN_GIOITINH = "gioitinh";
    public static final String NHANVIEN_NGAYSINH = "ngaysinh";
    public static final String TABLE_PHONGBAN = "phongban";
    public static final String PHONGBAN_MA = "ma";
    public static final String PHONGBAN_TEN = "ten";
    public static final String PHONGBAN_DIACHI = "diachi";
    public static final String PHONGBAN_SODIENTHOAI = "sodienthoai";
    public static final String TABLE_USER = "user";
    public static final String USER_ID = "ma";
    public static final String USER_NAME = "name";
    public static final String USER_PASS = "pass";


    public DBNhanVien(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, QUANLYNHANVIEN, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE "+TABLE_NHANVIEN +" ( "+NHANVIEN_ID+" TEXT PRIMARY KEY, "+NHANVIEN_Ten+" TEXT, "+NHANVIEN_IMAGE+" BLOB, "+NHANVIEN_PHONGBAN+" TEXT, "+NHANVIEN_GIOITINH+" INTEGER , "+NHANVIEN_NGAYSINH+" TEXT );";
        sqLiteDatabase.execSQL(query);
        query = "CREATE TABLE "+TABLE_PHONGBAN+" ("+PHONGBAN_MA+" TEXT PRIMARY KEY,"+PHONGBAN_TEN+" TEXT, "+PHONGBAN_DIACHI+" TEXT,"+PHONGBAN_SODIENTHOAI+" TEXT);";
        sqLiteDatabase.execSQL(query);
        query = "CREATE TABLE "+TABLE_USER+" ("+USER_ID+" TEXT PRIMARY KEY,"+USER_NAME+" TEXT, "+USER_PASS+" TEXT);";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
