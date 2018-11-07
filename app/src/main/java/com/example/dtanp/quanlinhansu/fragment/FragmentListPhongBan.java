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

import com.example.dtanp.quanlinhansu.DAO.PhongBanDAO;
import com.example.dtanp.quanlinhansu.NhanVienActivity;
import com.example.dtanp.quanlinhansu.R;
import com.example.dtanp.quanlinhansu.adapter.CustomAdapterListNhanVien;
import com.example.dtanp.quanlinhansu.adapter.CustomAdapterListPhongBan;
import com.example.dtanp.quanlinhansu.model.NhanVien;
import com.example.dtanp.quanlinhansu.model.PhongBan;
import com.example.dtanp.quanlinhansu.myinterface.TruyenPhongBan;

import java.util.ArrayList;
import java.util.List;

public class FragmentListPhongBan extends Fragment {

     ListView listPhongBan;
     CustomAdapterListPhongBan adapter;
     List<PhongBan> list;
     TruyenPhongBan truyenPhongBan;
     PhongBanDAO phongBanDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frangment_list_phongban,container,false);
        phongBanDAO = new PhongBanDAO(getActivity());
        phongBanDAO.open();
        list = phongBanDAO.LayDanhSachPhongBan();
        adapter = new CustomAdapterListPhongBan(getActivity(),R.layout.custom_list_phongban,list);
        listPhongBan = view.findViewById(R.id.listPhongBan);
        truyenPhongBan = (TruyenPhongBan) getActivity();
//        for (int i=0;i<30;i++)
////        {
////            PhongBan pb = new PhongBan();
////            pb.setMa(i+"");
////            pb.setDiaChi(i + " Phan Van Tri");
////            pb.setTen("IT " + i);
////            pb.setSoDienThoai("0000000"+i);
////            list.add(pb);
////        }
        listPhongBan.setAdapter(adapter);
        listPhongBan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                truyenPhongBan.setDataPhongBan((PhongBan) adapter.getItem(i));
            }
        });
        adapter.notifyDataSetChanged();
        registerForContextMenu(listPhongBan);

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.menu_context,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        return super.onContextItemSelected(item);
    }
    public void AddPhongBan(PhongBan phongBan)
    {
        list.add(phongBan);
        adapter.notifyDataSetChanged();
    }
}
