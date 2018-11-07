package com.example.dtanp.quanlinhansu.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.dtanp.quanlinhansu.database.DBNhanVien;
import com.example.dtanp.quanlinhansu.model.NhanVien;

import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {

    SQLiteDatabase database;
    DBNhanVien dbNhanVien;

    public NhanVienDAO(Context context) {
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

    public boolean ThemNhanVien(NhanVien nhanVien)
    {
        ContentValues values = new ContentValues();
        values.put(DBNhanVien.NHANVIEN_ID,nhanVien.getMa());
        values.put(DBNhanVien.NHANVIEN_GIOITINH,nhanVien.getGioiTinh());
        values.put(DBNhanVien.NHANVIEN_IMAGE,nhanVien.getImage());
        values.put(DBNhanVien.NHANVIEN_NGAYSINH,nhanVien.getNgaySinh());
        values.put(DBNhanVien.NHANVIEN_Ten,nhanVien.getTen());
        values.put(DBNhanVien.NHANVIEN_PHONGBAN,nhanVien.getMaPhongBan());
        long id = database.insert(DBNhanVien.TABLE_NHANVIEN,null,values);
        System.out.println(id+"id ne ");
        if (id!=-1)
        {
            return true;
        }
        else
            return false;
    }
    public boolean XoaNhanVien(NhanVien nhanVien)
    {
        int t=database.delete(DBNhanVien.TABLE_NHANVIEN,DBNhanVien.NHANVIEN_ID+"='"+nhanVien.getMa()+"'",null);
        if (t!=-1)
            return true;
        else
            return false;
    }

    public boolean SuaNhanVien(NhanVien nhanVienMoi)
    {
        ContentValues values = new ContentValues();
        values.put(DBNhanVien.NHANVIEN_ID,nhanVienMoi.getMa());
        values.put(DBNhanVien.NHANVIEN_GIOITINH,nhanVienMoi.getGioiTinh());
        values.put(DBNhanVien.NHANVIEN_IMAGE,nhanVienMoi.getImage());
        values.put(DBNhanVien.NHANVIEN_NGAYSINH,nhanVienMoi.getNgaySinh());
        values.put(DBNhanVien.NHANVIEN_Ten,nhanVienMoi.getTen());
        values.put(DBNhanVien.NHANVIEN_PHONGBAN,nhanVienMoi.getMaPhongBan());
        int t = database.update(DBNhanVien.TABLE_NHANVIEN,values,DBNhanVien.NHANVIEN_ID+"= '"+nhanVienMoi.getMa()+"'",null);
        if(t!=-1)
        {
            return true;
        }
        else
            return false;
    }

    public List<NhanVien> LayDanhSachNhanVien()
    {
        List<NhanVien> list = new ArrayList<>();
        Cursor cursor = database.query(DBNhanVien.TABLE_NHANVIEN,null,null,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {

            String ma = cursor.getString(cursor.getColumnIndex(DBNhanVien.NHANVIEN_ID));
            String ten = cursor.getString(cursor.getColumnIndex(DBNhanVien.NHANVIEN_Ten));
            String ngaySinh = cursor.getString(cursor.getColumnIndex(DBNhanVien.NHANVIEN_NGAYSINH));
            int gioiTinh = cursor.getInt(cursor.getColumnIndex(DBNhanVien.NHANVIEN_GIOITINH));
            String maPhongBan = cursor.getString(cursor.getColumnIndex(DBNhanVien.NHANVIEN_PHONGBAN));
            byte[] image = cursor.getBlob(cursor.getColumnIndex(DBNhanVien.NHANVIEN_IMAGE));
            NhanVien nhanVien = new NhanVien(ma,ten,maPhongBan,image,gioiTinh,ngaySinh);
            list.add(nhanVien);
            cursor.moveToNext();
        }
        return list;
    }

}
