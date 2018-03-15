package com.example.phoenix.xlambton;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phoenix.xlambton.models.Agent;
import com.example.phoenix.xlambton.models.DbHelper;
import com.example.phoenix.xlambton.models.ImageHandler;
import com.example.phoenix.xlambton.models.Session;

public class AgentProfile extends AppCompatActivity {

    private TextView nameTextView, levelTextView, agencyTextView, websiteTextView, countryTextView, phoneTextView, addressTextView;
    private ImageView agentImageView;
    private Agent agent;
    private Button editBtn;
    private ImageButton goToWebBtn, callBtn, mapBtn, historyBtn, smsBtn, missionUpdateBtn;
    private final int REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_profile);

        nameTextView = (TextView) findViewById(R.id.textViewAgentName_AP);
        levelTextView = (TextView) findViewById(R.id.textViewLevel_AP);
        agencyTextView = (TextView) findViewById(R.id.textViewAgency_AP);
        websiteTextView = (TextView) findViewById(R.id.textViewWebsite_AP);
        countryTextView = (TextView) findViewById(R.id.textViewCountry_AP);
        phoneTextView = (TextView) findViewById(R.id.textViewPhone_AP);
        addressTextView = (TextView) findViewById(R.id.textViewAddress_AP);
        agentImageView = (ImageView) findViewById(R.id.imageViewAgentProfile);
        editBtn = (Button) findViewById(R.id.buttonEdit_AP);
        goToWebBtn = (ImageButton) findViewById(R.id.imgButtonWebsite_AP);
        callBtn = (ImageButton) findViewById(R.id.imgButtonCall_AP);
        mapBtn = (ImageButton) findViewById(R.id.imgButtonLocation_AP);
        historyBtn = (ImageButton) findViewById(R.id.imgButtonInfo_AP);
        smsBtn = (ImageButton) findViewById(R.id.imgButtonSms_AP);
        missionUpdateBtn = (ImageButton) findViewById(R.id.imgButtonCam_AP);

        Intent intent = getIntent();
        agent = (Agent) intent.getSerializableExtra("agent");

        fillAgentDetails();

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAddAgentIntent = new Intent(AgentProfile.this, AddAgent.class);
                goToAddAgentIntent.putExtra("agent", agent);
                startActivityForResult(goToAddAgentIntent, REQUEST_CODE);
            }
        });

        goToWebBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSite = new Intent(Intent.ACTION_VIEW);
                String site = agent.getWebsite();
                if (!site.startsWith("http://")) {
                    site = "http://" + site;
                }
                intentSite.setData(Uri.parse(site));
                startActivity(intentSite);
            }
        });

        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(AgentProfile.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AgentProfile.this, new String[]{Manifest.permission.CALL_PHONE}, 123);
                } else {
                    Intent itemCALL = new Intent(Intent.ACTION_CALL);
                    itemCALL.setData(Uri.parse("tel:" + agent.getPhone()));
                    startActivity(itemCALL);
                }
            }
        });

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMAP = new Intent(Intent.ACTION_VIEW);
                intentMAP.setData(Uri.parse("geo:0,0?q=" + agent.getAddress()));
                startActivity(intentMAP);
            }
        });

        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMissionHistory = new Intent(AgentProfile.this, MissionHistory.class);
                goToMissionHistory.putExtra("agentId", agent.getId());
                startActivity(goToMissionHistory);
            }
        });

        smsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSMS = new Intent(Intent.ACTION_VIEW);

                intentSMS.setData(Uri.parse("sms:" + agent.getPhone()));
                startActivity(intentSMS);
            }
        });

        missionUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMissionUpdate = new Intent(AgentProfile.this, MissionUpdate.class);
                goToMissionUpdate.putExtra("phoneNo", agent.getPhone());
                startActivity(goToMissionUpdate);
            }
        });

    }

    private void fillAgentDetails() {
        nameTextView.setText(agent.getName());
        levelTextView.setText("Level: " + agent.getLevel());
        agencyTextView.setText("Agency: " + agent.getAgency());
        websiteTextView.setText(agent.getWebsite());
        countryTextView.setText("Country: " + agent.getCountry());
        phoneTextView.setText("Ph: " + agent.getPhone());
        addressTextView.setText(agent.getAddress());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            agent = (Agent) data.getSerializableExtra("updatedAgent");
            fillAgentDetails();
            String imagePath = agent.getPhotoPath();
            if (imagePath != null) {
                ImageHandler.setImage(imagePath, agentImageView);
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        String imagePath = agent.getPhotoPath();
        if (imagePath != null) {
            ImageHandler.setImage(imagePath, agentImageView);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_agent_profile, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_agentprofile_delete:

                DbHelper db = new DbHelper(AgentProfile.this);
                db.dbDeleteAgent(agent);
                finish();
                break;
            case R.id.menu_agentprofile_logout:
                Session.loggedIn = false;
                Intent intent = new Intent(AgentProfile.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
