package com.example.dtanp.quanlinhansu.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dtanp.quanlinhansu.DAO.NhanVienDAO;
import com.example.dtanp.quanlinhansu.NhanVienActivity;
import com.example.dtanp.quanlinhansu.R;
import com.example.dtanp.quanlinhansu.adapter.CustomAdapterListNhanVien;
import com.example.dtanp.quanlinhansu.model.NhanVien;
import com.example.dtanp.quanlinhansu.myinterface.TruyenNhanVien;

import java.util.ArrayList;
import java.util.List;

public class FragmentListNhanVien extends Fragment {

    NhanVienDAO nhanVienDAO;
    ListView listNhanVien;
    CustomAdapterListNhanVien adpter;
    List<NhanVien> list;
    TruyenNhanVien truyenNhanVien;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_nhanvien,container,false);
        nhanVienDAO = new NhanVienDAO(getActivity());
        nhanVienDAO.open();
        list = nhanVienDAO.LayDanhSachNhanVien();
        adpter = new CustomAdapterListNhanVien(getActivity(),R.layout.custom_list_nhanvien,list);
        listNhanVien = view.findViewById(R.id.listNhanVien);
        truyenNhanVien = (TruyenNhanVien) getActivity();
//        for (int i = 0;i<30;i++)
//        {
//            NhanVien nv = new NhanVien();
//            nv.setMa(i+"");
//            nv.setTen("Nhan Vien " + i);
//            if(i%2==0)
//                nv.setGioiTinh(1);
//            else
//                nv.setGioiTinh(2);
//            nv.setMaPhongBan("IT");
//            nv.setNgaySinh(i+"/10/2018");
//            list.add(nv);
//            adpter.notifyDataSetChanged();
//        }
        listNhanVien.setAdapter(adpter);
        listNhanVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                truyenNhanVien.setDataNhanVien((NhanVien) adpter.getItem(i));
            }
        });
        adpter.notifyDataSetChanged();
        registerForContextMenu(listNhanVien);
        return view;

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.menu_context,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = adapterContextMenuInfo.position;
        Toast.makeText(getActivity(),position+"aaaa",Toast.LENGTH_SHORT).show();
        return true;
    }

    public void AddNhanVien(NhanVien nhanVien)
    {
        System.out.println("Toi Add nhan vien");
        list.add(nhanVien);
        adpter.notifyDataSetChanged();
    }

    public void ResetList()
    {
        NhanVienDAO vienDAO = new NhanVienDAO(getActivity());
        vienDAO.open();
        list = vienDAO.LayDanhSachNhanVien();
        adpter.notifyDataSetChanged();
        adpter.notifyDataSetInvalidated();
        listNhanVien.setAdapter(adpter);
    }


}
