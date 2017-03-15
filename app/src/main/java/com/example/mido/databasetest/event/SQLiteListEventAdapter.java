package com.example.mido.databasetest.event;

/**
 * Created by sr on 3/12/17.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mido.databasetest.R;

import java.util.ArrayList;

public class SQLiteListEventAdapter extends BaseAdapter {

    Context context;
     ArrayList<String> arevent_type;
     ArrayList<String> arevent_des ;


    public SQLiteListEventAdapter(
            Context context2,
             ArrayList<String> arevent_type,
             ArrayList<String> subject
    )
    {

        this.context = context2;
         this.arevent_type = arevent_type;
         this.arevent_des = subject ;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return arevent_type.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getView(int position, View child, ViewGroup parent) {

        Holder holder;

        LayoutInflater layoutInflater;

        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.listviewdatalayout_event, null);

            holder = new Holder();

             holder.event_type = (TextView) child.findViewById(R.id.event_type);
             holder.event_des = (TextView) child.findViewById(R.id.des1);

            child.setTag(holder);

        } else {

            holder = (Holder) child.getTag();
        }
         holder.event_type.setText(arevent_type.get(position));
         holder.event_des.setText(arevent_des.get(position));

        return child;
    }

    public class Holder {
         TextView event_type;
        TextView event_des;
     }

}