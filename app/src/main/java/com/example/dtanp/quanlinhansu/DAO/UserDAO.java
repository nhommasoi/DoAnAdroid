package com.example.dtanp.quanlinhansu.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dtanp.quanlinhansu.database.DBNhanVien;
import com.example.dtanp.quanlinhansu.model.User;

public class UserDAO {

    SQLiteDatabase database;
    DBNhanVien dbNhanVien;

    public UserDAO(Context context) {

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

    public User getUserDangNhap(User user)
    {
        User us = new User();
        Cursor cursor = database.query(DBNhanVien.TABLE_USER,null,DBNhanVien.USER_NAME +"='"+user.getUserName()+"'",null,null,null,null);

        if (!cursor.isAfterLast())
        {
            cursor.moveToFirst();
            String ma = cursor.getString(cursor.getColumnIndex(DBNhanVien.USER_ID));
            String pass = cursor.getString(cursor.getColumnIndex(DBNhanVien.USER_PASS));
            String name = cursor.getString(cursor.getColumnIndex(DBNhanVien.USER_NAME));
            us.setId(ma);
            us.setUserName(name);
            us.setPass(pass);
            return us;
        }
        return null;
    }
    public boolean ThemUser(User user)
    {
        ContentValues values = new ContentValues();
        values.put(DBNhanVien.NHANVIEN_ID,user.getId());
        values.put(DBNhanVien.USER_NAME,user.getUserName());
        values.put(DBNhanVien.USER_PASS,user.getPass());
        long id = database.insert(DBNhanVien.TABLE_USER,null,values);
        if (id!=-1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
