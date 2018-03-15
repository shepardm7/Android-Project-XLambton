package com.example.phoenix.xlambton;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.phoenix.xlambton.adapters.MissionHistoryRowAdapter;
import com.example.phoenix.xlambton.models.DbHelper;

import java.util.ArrayList;

public class MissionHistory extends AppCompatActivity {

    private ListView missionHistoryListView;
    private TextView nothingToDispMessage;
    private long agentId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_history);
        missionHistoryListView = (ListView) findViewById(R.id.listViewMissionHistory);
        nothingToDispMessage = (TextView) findViewById(R.id.nothingToDispMessage_MH);
        agentId = getIntent().getLongExtra("agentId", -1);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mission_history, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_missionhistory_add:

                Intent goToAddMissionIntent = new Intent(MissionHistory.this, AddMissionHistory.class);
                goToAddMissionIntent.putExtra("agentId", agentId);
                startActivity(goToAddMissionIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        DbHelper db = new DbHelper(MissionHistory.this);
        MissionHistoryRowAdapter adapter = new MissionHistoryRowAdapter(MissionHistory.this, db.getMissions(agentId));
        missionHistoryListView.setAdapter(adapter);

        if (missionHistoryListView.getCount() > 0) {
            nothingToDispMessage.setVisibility(View.GONE);
        } else {
            nothingToDispMessage.setVisibility(View.VISIBLE);
        }
    }
}
