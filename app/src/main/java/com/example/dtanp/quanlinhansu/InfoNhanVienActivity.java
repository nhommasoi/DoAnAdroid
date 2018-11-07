package com.example.dtanp.quanlinhansu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtanp.quanlinhansu.fragment.FragmentInfoNhanVien;
import com.example.dtanp.quanlinhansu.model.NhanVien;
import com.example.dtanp.quanlinhansu.myinterface.TruyenNhanVien;

import java.util.Calendar;

public class InfoNhanVienActivity extends Activity implements TruyenNhanVien {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_nhan_vien);
        FragmentInfoNhanVien fragmentInfoNhanVien = (FragmentInfoNhanVien) getFragmentManager().findFragmentById(R.id.fragmenInfoNhanVien);
        NhanVien nhanVien = (NhanVien) getIntent().getSerializableExtra("nhanvien");
        fragmentInfoNhanVien.setDataInfo(nhanVien);

    }

    @Override
    public void setDataNhanVien(NhanVien nhanVien) {

    }

    @Override
    public void XuLySuaNhanVien() {
        FragmentInfoNhanVien fragmentInfoNhanVien = (FragmentInfoNhanVien) getFragmentManager().findFragmentById(R.id.fragmenInfoNhanVien);
        fragmentInfoNhanVien.HienThiSave();
    }
//    public boolean onContextItemSelected(MenuItem item) {
//        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        int position = adapterContextMenuInfo.position;
//        Toast.makeText(this,position+"aaaaa",Toast.LENGTH_SHORT).show();
//        return true;
//    }


}
