package com.example.dtanp.quanlinhansu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dtanp.quanlinhansu.DAO.UserDAO;
import com.example.dtanp.quanlinhansu.model.User;

public class MainActivity extends Activity {

    UserDAO userDAO;
    EditText edtUser,edtPass;
    Button btnLogin;
    CheckBox chkLuu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userDAO = new UserDAO(this);
        userDAO.open();
        edtPass = findViewById(R.id.edtPass);
        edtUser = findViewById(R.id.edtUserName);
        btnLogin = findViewById(R.id.btnLogin);
        chkLuu = findViewById(R.id.chkluu);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtUser.getText().toString().trim();
                String pass = edtPass.getText().toString().trim();
                User user = new User();
                user.setPass(pass);
                user.setUserName(name);
                User tam = userDAO.getUserDangNhap(user);
                if(tam!=null)
                {
                    if (user.getPass().toString().equals(tam.getPass()))
                    {
                        Toast.makeText(MainActivity.this,"Dang Nhap thanh cong",Toast.LENGTH_SHORT).show();
                        Intent intent  = new Intent(MainActivity.this,NhanVienActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,"Mat Khau Khong Trung Khop",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this,"Loi Dang Nhap",Toast.LENGTH_SHORT).show();
                }
            }
        });
//        User u2 = new User("CCC","dhcnsg","123456");
//        if(userDAO.ThemUser(u1))
//        {
//            Toast.makeText(this,"Them thanh Cong",Toast.LENGTH_SHORT).show();
//        }
//        else
//            System.out.println("That Bai u1");
//        if(userDAO.ThemUser(u2))
//        {
//            Toast.makeText(this,"Them thanh Cong u2",Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
//            System.out.println("That bai u2");
//        }
//        User us = userDAO.getUserDangNhap(u1);
//        if(us==null)
//        {
//            System.out.println("null");
//        }
        //System.out.println(us.getId() +"-" + us.getUserName());
    }
}
