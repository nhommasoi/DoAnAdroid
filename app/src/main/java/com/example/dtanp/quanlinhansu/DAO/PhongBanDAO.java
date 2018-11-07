package com.example.dtanp.quanlinhansu.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dtanp.quanlinhansu.database.DBNhanVien;
import com.example.dtanp.quanlinhansu.model.NhanVien;
import com.example.dtanp.quanlinhansu.model.PhongBan;

import java.util.ArrayList;
import java.util.List;

public class PhongBanDAO {
    SQLiteDatabase database;
    DBNhanVien dbNhanVien;

    public PhongBanDAO(Context context) {
        dbNhanVien = new DBNhanVien(context,null,null,1);
    }
    public void open()
    {
        database = dbNhanVien.getWritableDatabase();
    }

    public void close()
    {
        dbNhanVien.close();
    }

    public boolean ThemPhongBan(PhongBan phongBan)
    {
        ContentValues values = new ContentValues();
        values.put(DBNhanVien.PHONGBAN_MA,phongBan.getMa());
        values.put(DBNhanVien.PHONGBAN_TEN,phongBan.getTen());
        values.put(DBNhanVien.PHONGBAN_DIACHI,phongBan.getDiaChi());
        values.put(DBNhanVien.PHONGBAN_SODIENTHOAI,phongBan.getSoDienThoai());
        long id = database.insert(DBNhanVien.TABLE_PHONGBAN,null,values);
        System.out.println(id+"id ne ");
        if (id!=-1)
        {
            return true;
        }
        else
            return false;
    }
    public boolean XoaPongBan(PhongBan phongBan)
    {
        int t=database.delete(DBNhanVien.TABLE_PHONGBAN,DBNhanVien.PHONGBAN_MA+"='"+phongBan.getMa()+"'",null);
        if (t!=-1)
            return true;
        else
            return false;
    }

    public boolean SuaPhongBan(PhongBan phongBanMoi)
    {
        ContentValues values = new ContentValues();
        values.put(DBNhanVien.PHONGBAN_MA,phongBanMoi.getMa());
        values.put(DBNhanVien.PHONGBAN_TEN,phongBanMoi.getTen());
        values.put(DBNhanVien.PHONGBAN_DIACHI,phongBanMoi.getDiaChi());
        values.put(DBNhanVien.PHONGBAN_SODIENTHOAI,phongBanMoi.getSoDienThoai());
        int t = database.update(DBNhanVien.TABLE_PHONGBAN,values,DBNhanVien.PHONGBAN_MA+"='"+phongBanMoi.getMa()+"'",null);
        if(t!=-1)
        {
            return true;
        }
        else
            return false;
    }

    public List<PhongBan> LayDanhSachPhongBan()
    {
        List<PhongBan> list = new ArrayList<>();
        Cursor cursor = database.query(DBNhanVien.TABLE_PHONGBAN,null,null,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {

            String ma = cursor.getString(cursor.getColumnIndex(DBNhanVien.PHONGBAN_MA));
            String ten = cursor.getString(cursor.getColumnIndex(DBNhanVien.PHONGBAN_TEN));
            String diaChi = cursor.getString(cursor.getColumnIndex(DBNhanVien.PHONGBAN_DIACHI));
            String soDienThoai = cursor.getString(cursor.getColumnIndex(DBNhanVien.PHONGBAN_SODIENTHOAI));
            PhongBan phongBan = new PhongBan(ma,ten,diaChi,soDienThoai);
            list.add(phongBan);
            cursor.moveToNext();
        }
        return list;
    }
}
