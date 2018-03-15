package com.example.phoenix.xlambton.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phoenix.xlambton.R;
import com.example.phoenix.xlambton.models.Agent;
import com.example.phoenix.xlambton.models.ImageHandler;

import java.util.ArrayList;

/**
 * Created by Phoenix on 16-Aug-17.
 */

public class AgentRowAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Agent> agents;

    public AgentRowAdapter(Context context, ArrayList<Agent> agents) {
        this.context = context;
        this.agents = agents;
    }

    @Override
    public int getCount() {
        return agents.size();
    }

    @Override
    public Object getItem(int position) {
        return agents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return agents.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = convertView;

        if (view == null) {
            view = inflater.inflate(R.layout.agent_row, parent, false);
            TextView nameText = (TextView) view.findViewById(R.id.agentRowName);
            TextView levelText = (TextView) view.findViewById(R.id.agentRowLevel);
            ImageView imageView = (ImageView) view.findViewById(R.id.agentRowImageView);
            Agent agent = agents.get(position);
            nameText.setText(agent.getName());
            levelText.setText(agent.getLevel());
            String imagePath = agent.getPhotoPath();
            if(imagePath != null)
                ImageHandler.setImage(imagePath, imageView);
        }
        return view;
    }
}
