package com.example.dtanp.quanlinhansu.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtanp.quanlinhansu.DAO.PhongBanDAO;
import com.example.dtanp.quanlinhansu.PhongBangActivity;
import com.example.dtanp.quanlinhansu.R;
import com.example.dtanp.quanlinhansu.model.PhongBan;
import com.example.dtanp.quanlinhansu.myinterface.TruyenPhongBan;

public class FragmentInfoPhongBan extends Fragment {

    TextView txtMa,txtTen,txtDiaChi,txtSDT;
    AlertDialog dialog,dialogXoa;
    EditText edtTen,edtDiaChi,edtSDT;
    ImageButton imgXoa,imgSua;
    PhongBanDAO phongBanDAO;
    PhongBan phongBanInfo;
    Button btnSave;
    TruyenPhongBan truyenPhongBan ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_phongban,container,false);
        txtMa = view.findViewById(R.id.txtMa);
        txtTen = view.findViewById(R.id.txtTen);
        txtDiaChi = view.findViewById(R.id.txtDiaChi);
        txtSDT = view.findViewById(R.id.txtSDT);
        addDialog();
        addDialogXoa();
        phongBanDAO =new PhongBanDAO(getActivity());
        phongBanDAO.open();
        imgXoa = view.findViewById(R.id.btnXoa);
        imgSua = view.findViewById(R.id.btnSua);
        imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogXoa.show();
            }
        });
        imgSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtTen.setText(phongBanInfo.getTen());
                edtDiaChi.setText(phongBanInfo.getDiaChi());
                edtSDT.setText(phongBanInfo.getSoDienThoai());
                dialog.show();
            }
        });
        truyenPhongBan = (TruyenPhongBan) getActivity();
        btnSave = view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),PhongBangActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }

    public void setDataInfoPhongBan(PhongBan phongBan)
    {
        this.phongBanInfo =phongBan;
        txtMa.setText(phongBan.getMa());
        txtTen.setText(phongBan.getTen());
        txtDiaChi.setText(phongBan.getDiaChi());
        txtSDT.setText(phongBan.getSoDienThoai());
    }
    public void addDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialog_phongban,null);
        edtDiaChi = view.findViewById(R.id.edtDiaChi);
        edtSDT = view.findViewById(R.id.edtSDT);
        edtTen = view.findViewById(R.id.edtTen);
        builder.setView(view);
        builder.setPositiveButton(R.string.luu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                PhongBan phongBan = new PhongBan();
                phongBan.setMa(phongBanInfo.getMa());
                phongBan.setTen(edtTen.getText().toString());
                phongBan.setSoDienThoai(edtSDT.getText().toString());
                phongBan.setDiaChi(edtDiaChi.getText().toString());
                boolean flag = phongBanDAO.SuaPhongBan(phongBan);
                if(flag==true)
                {
                    setDataInfoPhongBan(phongBan);
                    truyenPhongBan.XuLySuaPhongBan();
                    Toast.makeText(getActivity(),"Sua Thành Công",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getActivity(),"Sua Thất Bại",Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton(R.string.huy, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getActivity(),"Cancle",Toast.LENGTH_SHORT).show();
            }
        });
        dialog = builder.create();
    }
    public void addDialogXoa()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.xoa);
        builder.setMessage(R.string.ghichuxoa);
        builder.setPositiveButton(R.string.xacnhan, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                phongBanDAO.XoaPongBan(phongBanInfo);
                Intent intent = new Intent(getActivity(),PhongBangActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(),"Xoa Thanh Cong",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.huy, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogXoa = builder.create();
    }
    public void HienThiSave()
    {
        btnSave.setVisibility(View.VISIBLE);
    }
}
