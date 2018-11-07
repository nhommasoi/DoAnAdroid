package com.example.dtanp.quanlinhansu;

import android.app.Activity;
import android.os.Bundle;

import com.example.dtanp.quanlinhansu.fragment.FragmentInfoPhongBan;
import com.example.dtanp.quanlinhansu.model.PhongBan;
import com.example.dtanp.quanlinhansu.myinterface.TruyenPhongBan;

public class InfoPhongBanActivity extends Activity implements TruyenPhongBan {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_phong_ban);
        FragmentInfoPhongBan fragmentInfoPhongBan = (FragmentInfoPhongBan) getFragmentManager().findFragmentById(R.id.fragmentInfoPhongBan);
        PhongBan phongBan = (PhongBan) getIntent().getSerializableExtra("phongban");
        fragmentInfoPhongBan.setDataInfoPhongBan(phongBan);

    }

    @Override
    public void setDataPhongBan(PhongBan phongBan) {

    }

    @Override
    public void XuLySuaPhongBan() {
        FragmentInfoPhongBan fragmentInfoPhongBan = (FragmentInfoPhongBan) getFragmentManager().findFragmentById(R.id.fragmentInfoPhongBan);
        fragmentInfoPhongBan.HienThiSave();
    }
}
