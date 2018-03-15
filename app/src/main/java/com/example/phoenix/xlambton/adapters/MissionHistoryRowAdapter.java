package com.example.phoenix.xlambton.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import com.example.phoenix.xlambton.R;

/**
 * Created by Phoenix on 17-Aug-17.
 */

public class MissionHistoryRowAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> name, date, status;

    public MissionHistoryRowAdapter(Context context, ArrayList<String>[] missions) {
        this.context = context;
        this.name = missions[0];
        this.date = missions[1];
        this.status = missions[2];
    }

    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        LayoutInflater inflater = LayoutInflater.from(context);

        if(view == null) {
            view = inflater.inflate(R.layout.mission_history_row, parent, false);
            TextView missionNameText, missionDateText, missionStatusText;
            missionNameText = (TextView) view.findViewById(R.id.missionHistoryName);
            missionDateText = (TextView) view.findViewById(R.id.missionHistoryDate);
            missionStatusText = (TextView) view.findViewById(R.id.missionHistoryStatus);
            missionNameText.setText("Mission name: " + name.get(position));
            missionDateText.setText("Date: " + date.get(position));
            missionStatusText.setText("Status: " + status.get(position));
        }
        return view;
    }
}
