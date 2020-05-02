package com.example.hw_laptopshop0105;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class LaptopListViewAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<Laptop> list;

    public LaptopListViewAdapter(Context context, int layout, List<Laptop> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        TextView txtID, txtName, txtPrice;
        ImageView image_anh_show;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.txtID = convertView.findViewById(R.id.tv_id_show);
            holder.txtName = convertView.findViewById(R.id.tv_name_show);
            holder.txtPrice = convertView.findViewById(R.id.tv_price_show);
            holder.image_anh_show = convertView.findViewById(R.id.img_show_show);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }

        Laptop laptop = list.get(position);
        holder.txtID.setText(   "ID:" + laptop.getId());
        holder.txtName.setText( "Name:" + laptop.getName());
        holder.txtPrice.setText("Price:" + String.valueOf(laptop.getPrice()));

        byte[] hinhanh = laptop.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanh, 0, hinhanh.length);
        holder.image_anh_show.setImageBitmap(bitmap);


        return convertView;
    }
}
