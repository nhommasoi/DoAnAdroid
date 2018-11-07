package com.example.dtanp.quanlinhansu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dtanp.quanlinhansu.R;
import com.example.dtanp.quanlinhansu.model.PhongBan;

import java.util.List;

public class CustomAdapterListPhongBan extends ArrayAdapter {

    Context context;
    int resource;
    List<PhongBan> list;

    public CustomAdapterListPhongBan(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.list=objects;
    }

    private class ViewHolder{
        TextView txtMa;
        TextView txtTen;
    }

    @Override
    public View getView(int position, View convertView,ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null)
        {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource,parent,false);
            holder.txtMa = convertView.findViewById(R.id.txtMa);
            holder.txtTen = convertView.findViewById(R.id.txtTen);
            convertView.setTag(holder);

        }
        else
        {
            holder= (ViewHolder) convertView.getTag();
        }
        PhongBan phongBan = list.get(position);
        holder.txtMa.setText(phongBan.getMa().toString());
        holder.txtTen.setText(phongBan.getTen().toString());
        return convertView;
    }
}
