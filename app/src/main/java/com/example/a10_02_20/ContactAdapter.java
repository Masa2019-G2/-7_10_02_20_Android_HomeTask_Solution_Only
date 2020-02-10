package com.example.a10_02_20;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ContactAdapter extends BaseAdapter {
    private List<Contact> list;

    public ContactAdapter(List<Contact> list) {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_contact,parent,false);
        }
        Contact contact = list.get(position);
        TextView nameTxt = convertView.findViewById(R.id.nameTxt);
        TextView phoneTxt = convertView.findViewById(R.id.phoneTxt);
        nameTxt.setText(contact.name);
        phoneTxt.setText(contact.phone);
        return convertView;
    }
}
