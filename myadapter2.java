package com.example.hp.bromocinema;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by HP on 30/10/2016.
 */
public class myadapter2 extends BaseAdapter {

    Context context;
    ArrayList<videoitem> items = new ArrayList<videoitem>();

    public myadapter2(Context context,ArrayList<videoitem> item) {

        this.context=context;
        this.items = item;

    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position).name;
    }

    @Override
    public long getItemId(int position) {
        return position;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater flate = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = flate.inflate(R.layout.listvideo, null);
        TextView videotex=(TextView)v.findViewById(R.id.videotext);
        videotex.setText(items.get(position).name);


        return v;
    }

}
