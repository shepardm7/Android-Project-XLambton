package com.example.phoenix.xlambton;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.phoenix.xlambton.adapters.AgentRowAdapter;
import com.example.phoenix.xlambton.models.Agent;
import com.example.phoenix.xlambton.models.DbHelper;

import java.util.ArrayList;

public class AgentList extends AppCompatActivity {

    ListView agentListView;
    private ArrayList<Agent> agentsList;
    private boolean searchFlag;
    private String searchStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_list);

        searchFlag = false;

        agentListView = (ListView) findViewById(R.id.listViewAgent);


        agentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Agent agent = (Agent) agentListView.getItemAtPosition(position);
                Intent goToAgentProfileIntent = new Intent(AgentList.this, AgentProfile.class);
                goToAgentProfileIntent.putExtra("agent", agent);
                startActivity(goToAgentProfileIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        searchStr = intent.getStringExtra("searchStr");
        DbHelper db = new DbHelper(this);
        if (searchStr == null) {
            agentsList = db.getAllAgents();
            AgentRowAdapter adapter = new AgentRowAdapter(this, agentsList);
            agentListView.setAdapter(adapter);
        } else {
            agentsList = db.getSearchedAgents(searchStr);
            AgentRowAdapter adapter = new AgentRowAdapter(this, agentsList);
            agentListView.setAdapter(adapter);
        }
    }
}
