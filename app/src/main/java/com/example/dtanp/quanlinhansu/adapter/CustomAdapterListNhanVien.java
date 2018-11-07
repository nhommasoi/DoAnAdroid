package com.example.dtanp.quanlinhansu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dtanp.quanlinhansu.R;
import com.example.dtanp.quanlinhansu.fragment.FragmentListPhongBan;
import com.example.dtanp.quanlinhansu.model.NhanVien;

import java.util.List;

public class CustomAdapterListNhanVien extends ArrayAdapter {

    Context context;
    int resource;
    List<NhanVien> list;

    public CustomAdapterListNhanVien(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.list=objects;
    }

    private class ViewHolder
    {
        ImageView imgAnh;
        TextView txtMa;
        TextView txtTen;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null)
        {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource,parent,false);
            holder.imgAnh=convertView.findViewById(R.id.imgAnh);
            holder.txtMa = convertView.findViewById(R.id.txtMa);
            holder.txtTen = convertView.findViewById(R.id.txtTen);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        NhanVien nhanVien = list.get(position);
        holder.txtMa.setText(nhanVien.getMa().toString());
        holder.txtTen.setText(nhanVien.getTen().toString());
        //chuyen byte thanh bitmap
        byte[] hinh = list.get(position).getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinh,0,hinh.length);
        holder.imgAnh.setImageBitmap(bitmap);

        return convertView;
    }
}
