package com.example.phoenix.xlambton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phoenix.xlambton.models.DbHelper;

public class AddMissionHistory extends AppCompatActivity {

    private EditText missionNameText, missionDateText, missionStatusText;
    private Button addBtn;
    private long agentId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mission_history);

        missionNameText = (EditText) findViewById(R.id.editTextMissionHistoryName);
        missionDateText = (EditText) findViewById(R.id.editTextMissionHistoryDate);
        missionStatusText = (EditText) findViewById(R.id.editTextMissionHistoryStatus);
        addBtn = (Button) findViewById(R.id.buttonSaveMissionHistory);

        agentId = getIntent().getLongExtra("agentId", -1);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper db = new DbHelper(AddMissionHistory.this);
                String missionName, missionDate, missionStatus;
                missionName = missionNameText.getText().toString().trim();
                missionDate = missionDateText.getText().toString().trim();
                missionStatus = missionStatusText.getText().toString().trim();
                if (missionName != null && missionDate != null && missionStatus != null) {
                    db.addMission(missionName, missionDate, missionStatus, agentId);
                    Toast.makeText(AddMissionHistory.this, "Mission added!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddMissionHistory.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
