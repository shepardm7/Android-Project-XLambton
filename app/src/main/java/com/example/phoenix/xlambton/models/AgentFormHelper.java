package com.example.phoenix.xlambton.models;

import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.phoenix.xlambton.AddAgent;
import com.example.phoenix.xlambton.R;

/**
 * Created by Phoenix on 16-Aug-17.
 */

public class AgentFormHelper {
    private final EditText nameField, levelField, agencyField, webField, countryField, phoneField, addressField;
    private final ImageView photoField;
    private Agent agent;

    public AgentFormHelper(AddAgent activity) {
        nameField = (EditText) activity.findViewById(R.id.editTextName);
        levelField = (EditText) activity.findViewById(R.id.editTextLevel);
        agencyField = (EditText) activity.findViewById(R.id.editTextAgency);
        webField = (EditText) activity.findViewById(R.id.editTextWebsite);
        countryField = (EditText) activity.findViewById(R.id.editTextCountry);
        phoneField = (EditText) activity.findViewById(R.id.editTextPhone);
        addressField = (EditText) activity.findViewById(R.id.editTextAddress);
        photoField = (ImageView) activity.findViewById(R.id.imageViewAddAgent);
        agent = new Agent();
    }

    public Agent getAgentFromForm(String imagePath){
        agent.setName(nameField.getText().toString());
        agent.setLevel(levelField.getText().toString());
        agent.setAgency(agencyField.getText().toString());
        agent.setWebsite(webField.getText().toString());
        agent.setCountry(countryField.getText().toString());
        agent.setPhone(phoneField.getText().toString());
        agent.setAddress(addressField.getText().toString());
        agent.setPhotoPath(imagePath);
        return agent;
    }

    public void fillAgentForm(Agent agent) {
        nameField.setText(agent.getName());
        levelField.setText(agent.getLevel());
        agencyField.setText(agent.getAgency());
        webField.setText(agent.getWebsite());
        countryField.setText(agent.getCountry());
        phoneField.setText(agent.getPhone());
        addressField.setText(agent.getAddress());
    }
}
