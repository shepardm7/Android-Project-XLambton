package com.example.phoenix.xlambton;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoggedInActivity extends AppCompatActivity {

    Button viewAgentsBtn, searchAgentBtn, addAgentBtn;
    AlertDialog.Builder alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        if (ContextCompat.checkSelfPermission(LoggedInActivity.this,
                Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LoggedInActivity.this,
                    new String[]{Manifest.permission.RECEIVE_SMS}, 1);
        }

        viewAgentsBtn = (Button) findViewById(R.id.buttonViewAgentList);
        searchAgentBtn = (Button) findViewById(R.id.buttonSearchAgent);
        addAgentBtn = (Button) findViewById(R.id.buttonAddAgent);

        addAgentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAddAgentIntent = new Intent(LoggedInActivity.this, AddAgent.class);
                startActivity(goToAddAgentIntent);
            }
        });

        viewAgentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAgentListIntent = new Intent(LoggedInActivity.this, AgentList.class);
                startActivity(goToAgentListIntent);
            }
        });

        searchAgentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(LoggedInActivity.this);
                final EditText editText = new EditText(LoggedInActivity.this);
                alert.setTitle("Enter name of the agent you want to search.");
                alert.setView(editText);
                alert.setCancelable(false);
                alert.setPositiveButton("Search", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String searchStr = editText.getText().toString();
                        if (!searchStr.isEmpty()) {
                            Intent goToAgentListIntent = new Intent(LoggedInActivity.this, AgentList.class);
                            goToAgentListIntent.putExtra("searchStr", searchStr);
                            dialog.dismiss();
                            startActivity(goToAgentListIntent);
                        } else {
                            Toast.makeText(LoggedInActivity.this, "Please provide a name to search", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert.show();
            }
        });
    }
}
