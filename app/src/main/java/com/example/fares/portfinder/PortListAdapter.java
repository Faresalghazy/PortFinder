package com.example.fares.portfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class PortListAdapter extends BaseAdapter {

    private ArrayList<String> ports;
    private LayoutInflater layoutInflater;

    public PortListAdapter(Context context, ArrayList<String> ports) {
        this.ports = ports;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return ports.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = layoutInflater.inflate(R.layout.list_view_item, null);

        TextView tv = view.findViewById(R.id.tv_port_number);

        tv.setText(ports.get(i));

        return view;
    }
}