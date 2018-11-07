package com.example.dtanp.quanlinhansu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dtanp.quanlinhansu.DAO.PhongBanDAO;
import com.example.dtanp.quanlinhansu.fragment.FragmentInfoPhongBan;
import com.example.dtanp.quanlinhansu.fragment.FragmentListPhongBan;
import com.example.dtanp.quanlinhansu.model.NhanVien;
import com.example.dtanp.quanlinhansu.model.PhongBan;
import com.example.dtanp.quanlinhansu.myinterface.TruyenPhongBan;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PhongBangActivity extends Activity implements TruyenPhongBan{

    AlertDialog dialog;
    EditText edtTen,edtDiaChi,edtSDT;
    PhongBanDAO phongBanDAO;
    FragmentListPhongBan fragmentListPhongBan;
    List<PhongBan> listPhongBan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phong_bang);
        addDialog();
        phongBanDAO = new PhongBanDAO(this);
        phongBanDAO.open();
        fragmentListPhongBan = (FragmentListPhongBan) getFragmentManager().findFragmentById(R.id.fragmentListPhongBan);
        listPhongBan=phongBanDAO.LayDanhSachPhongBan();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_phongban,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if(item.getItemId()==R.id.btnadd)
        {
            dialog.show();
        }
        if (item.getItemId()==R.id.menugioithieu)
        {
            Intent intent = new Intent(PhongBangActivity.this,AboutActivity.class);
            startActivity(intent);
        }

        if (item.getItemId()==R.id.menuNhanVien)
        {
            Intent intent = new Intent(PhongBangActivity.this,NhanVienActivity.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    public void setDataPhongBan(PhongBan phongBan) {
        FragmentInfoPhongBan fragmentInfoPhongBan = (FragmentInfoPhongBan) getFragmentManager().findFragmentById(R.id.fragmentInfoPhongBan);
        if (fragmentInfoPhongBan!=null&&fragmentInfoPhongBan.isInLayout())
        {
            fragmentInfoPhongBan.setDataInfoPhongBan(phongBan);
        }
        else
        {
            Intent intent = new Intent(PhongBangActivity.this,InfoPhongBanActivity.class);
            intent.putExtra("phongban",phongBan);
            startActivity(intent);
        }

    }

    @Override
    public void XuLySuaPhongBan() {
        Intent intent = new Intent(PhongBangActivity.this,PhongBangActivity.class);
        startActivity(intent);
    }

    public void addDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialog_phongban,null);
        edtDiaChi = view.findViewById(R.id.edtDiaChi);
        edtSDT = view.findViewById(R.id.edtSDT);
        edtTen = view.findViewById(R.id.edtTen);
        builder.setView(view);
        builder.setPositiveButton(R.string.luu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                PhongBan phongBan = new PhongBan();
                phongBan.setMa(getMaTuTang());
                phongBan.setTen(edtTen.getText().toString());
                phongBan.setSoDienThoai(edtSDT.getText().toString());
                phongBan.setDiaChi(edtDiaChi.getText().toString());
                boolean flag = phongBanDAO.ThemPhongBan(phongBan);
                if(flag==true)
                {
                    fragmentListPhongBan.AddPhongBan(phongBan);
                    listPhongBan.add(phongBan);
                    Toast.makeText(PhongBangActivity.this,"Thêm Thành Công",Toast.LENGTH_SHORT).show();
                    edtTen.setText("");
                    edtDiaChi.setText("");
                    edtSDT.setText("");
                }
                else
                {
                    Toast.makeText(PhongBangActivity.this,"Thêm Thất Bại",Toast.LENGTH_SHORT).show();
                }

            }
        });
        builder.setNegativeButton(R.string.huy, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(PhongBangActivity.this,"Cancle",Toast.LENGTH_SHORT).show();
            }
        });
        dialog = builder.create();
    }
    public String getMaTuTang() {


        Calendar calendar = Calendar.getInstance();
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        if (month.length()<2)
        {
            month = "0"+month;
        }
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        if (day.length()<2)
        {
            day="0"+day;
        }
        StringBuilder string = new StringBuilder("PB" + day + month);
        List<String> listMaPB = new ArrayList<>();
        int ma = 0;
        if (listPhongBan.size() > 0) {
            for(PhongBan phongBan : listPhongBan)
            {
                if(phongBan.getMa().contains(string.toString()))
                {
                    listMaPB.add(phongBan.getMa());
                }
            }
            if (listMaPB.size() > 0) {
                int[] arr = new int[listMaPB.size()];
                for (int i = 0; i < listMaPB.size(); i++) {
                    int t = Integer.parseInt(listMaPB.get(i).substring(6));
                    arr[i] = t;
                }
                selectionSort(arr);
                for (int i =0 ; i<arr.length; i++)
                {
                    if((i+1)!=arr.length)
                    {
                        if(arr[i+1]-arr[i]!=1)
                        {
                            ma=arr[i]+1;
                            break;
                        }
                    }
                    ma = arr[i]+1;
                }

            } else {
                ma = 1;
            }
        } else {
            ma = 1;
        }
        string.append(ma);
        while (string.length()<10)
        {
            string.insert(6,"0");
        }

        return string.toString();
    }

    public void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int index = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[index]) {
                    index = j;//searching for lowest index
                }
            }
            int smallerNumber = arr[index];
            arr[index] = arr[i];
            arr[i] = smallerNumber;
        }


    }
}
