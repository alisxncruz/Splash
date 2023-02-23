package com.example.mysplash;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

public class MyAdapter extends BaseAdapter implements Serializable {

    private List<infoC> list;
    private Context context;
    private LayoutInflater layoutInflater;

    public MyAdapter (List<infoC> list, Context context)
    {

        this.list =list;
        this.context = context;
        if( context != null){
            layoutInflater = LayoutInflater.from(context);
        }
    }

    public boolean isEmptyOrNull(){
        return list == null || list.size()==0;
    }

    @Override
    public int getCount() {
        if(isEmptyOrNull()){
            return 0;
        }
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        if (isEmptyOrNull()){
            return null;
        }
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView redSocial = null;
        TextView pswd = null ;
        view = layoutInflater.inflate(R.layout.activity_edit_list, null);
        redSocial = view.findViewById(R.id.redS);
        pswd = view.findViewById(R.id.passG);
        redSocial.setText(String.valueOf(list.get(i).getRedPass()));
        pswd.setText(String.valueOf(list.get(i).getPass()));
        return view;
    }
}
