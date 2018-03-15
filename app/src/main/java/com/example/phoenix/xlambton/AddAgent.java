package com.example.phoenix.xlambton;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.phoenix.xlambton.models.Agent;
import com.example.phoenix.xlambton.models.AgentFormHelper;
import com.example.phoenix.xlambton.models.DbHelper;
import com.example.phoenix.xlambton.models.ImageHandler;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddAgent extends AppCompatActivity {

    ImageView agentImageView;
    private static final int CAMERA_CODE = 990;
    private String dirAppPhoto;
    private AgentFormHelper helper;
    private Agent agent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_agent);

        agentImageView = (ImageView) findViewById(R.id.imageViewAddAgent);
        helper = new AgentFormHelper(this);

        agentImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        Intent intent = getIntent();
        agent = (Agent) intent.getSerializableExtra("agent");
        if (agent != null) {
            helper.fillAgentForm(agent);
        }
    }
    

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);


        if (agent != null) {
            if (agent.getPhotoPath() != null && mCurrentPhotoPath == null) {
                mCurrentPhotoPath = agent.getPhotoPath();
                ImageHandler.setImage(agent.getPhotoPath(), agentImageView);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_agent, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_addagent_save:
                if (agent != null) {
                    updateAgentInDB();
                } else {
                    insertAgentInDB();
                }
                break;

            case R.id.menu_addagent_reset:

                break;

            case R.id.menu_addagent_logout:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertAgentInDB() {
        DbHelper db = new DbHelper(AddAgent.this);
        Agent agent = helper.getAgentFromForm(mCurrentPhotoPath);
        if (db.addAgent(agent)) {
            Toast.makeText(this, "New agent has been added!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Agent with this phone number is already registered", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateAgentInDB() {
        DbHelper db = new DbHelper(AddAgent.this);
        Agent agent1 = helper.getAgentFromForm(mCurrentPhotoPath);
        if (db.dbAlterAgent(agent1, agent.getId())) {
            Toast.makeText(this, "Agent has been updated successfully!", Toast.LENGTH_SHORT).show();
            Agent updatedAgent = db.dbGetAgentAt(agent.getId());
            Intent returnIntent = getIntent();
            returnIntent.putExtra("updatedAgent", updatedAgent);
            setResult(RESULT_OK, returnIntent);
            finish();
        } else {
            Toast.makeText(this, "Agent with this phone number is already registered", Toast.LENGTH_SHORT).show();
        }
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    static final int REQUEST_TAKE_PHOTO = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            ImageHandler.setImage(mCurrentPhotoPath, agentImageView);
        }
    }



    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

//    private void setPic() {
//        // Get the dimensions of the View
//        int targetW = agentImageView.getWidth();
//        int targetH = agentImageView.getHeight();
//
//        // Get the dimensions of the bitmap
//        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//        bmOptions.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
//        int photoW = bmOptions.outWidth;
//        int photoH = bmOptions.outHeight;
//
//        // Determine how much to scale down the image
//        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
//
//        // Decode the image file into a Bitmap sized to fill the View
//        bmOptions.inJustDecodeBounds = false;
//        bmOptions.inSampleSize = scaleFactor;
//        bmOptions.inPurgeable = true;
//
//        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
//        agentImageView.setImageBitmap(bitmap);
//    }

}
