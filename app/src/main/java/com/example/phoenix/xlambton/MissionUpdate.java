package com.example.phoenix.xlambton;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.phoenix.xlambton.models.ImageHandler;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MissionUpdate extends AppCompatActivity {

    ImageView selectedImageView, imageView1, imageView2, imageView3, imageView4;
    Button sendMmsBtn;
    HashMap<String, File> imageFiles;
    private String agentPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_update);

        imageView1 = (ImageView) findViewById(R.id.imageView1_MU);
        imageView2 = (ImageView) findViewById(R.id.imageView2_MU);
        imageView3 = (ImageView) findViewById(R.id.imageView3_MU);
        imageView4 = (ImageView) findViewById(R.id.imageView4_MU);
        sendMmsBtn = (Button) findViewById(R.id.buttonSendMMS_MU);

        agentPhone = getIntent().getStringExtra("phoneNo");

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        imageFiles = new HashMap<String, File>();

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImageView = imageView1;
                dispatchTakePictureIntent();
                imageFiles.put("File1", imageFile);
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImageView = imageView2;
                dispatchTakePictureIntent();
                imageFiles.put("File2", imageFile);
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImageView = imageView3;
                dispatchTakePictureIntent();
                imageFiles.put("File3", imageFile);
            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImageView = imageView4;
                dispatchTakePictureIntent();
                imageFiles.put("File4", imageFile);
            }
        });

        imageView1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                selectedImageView = imageView1;
                resetImage("File1");
                return false;
            }
        });

        imageView2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                selectedImageView = imageView2;
                resetImage("File2");
                return false;
            }
        });

        imageView3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                selectedImageView = imageView3;
                resetImage("File3");
                return false;
            }
        });

        imageView4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                selectedImageView = imageView4;
                resetImage("File4");
                return false;
            }
        });

        sendMmsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent sendIntent = new Intent(Intent.ACTION_SEND);
//                sendIntent.setClassName("com.android.mms", "com.android.mms.ui.ComposeMessageActivity");
//                sendIntent.putExtra("sms_body", "some text");
//                sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(mCurrentPhotoPath));
//                sendIntent.setType("image/jpg");
//                startActivity(sendIntent);
                Intent mmsIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
//file is the file on the SD Card

                ArrayList<Uri> uris = new ArrayList<>();
                mmsIntent.putExtra("address", agentPhone);
                mmsIntent.putExtra("subject", "Mission Update!");

                Iterator iterator = imageFiles.entrySet().iterator();
                while(iterator.hasNext()){
                    Map.Entry pair = (Map.Entry) iterator.next();
                    uris.add(Uri.fromFile((File) pair.getValue()));
                }

                mmsIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
                //MainActivity.this.startActivityForResult(Intent.createChooser(mmsIntent, getString(R.string.chooseIntentMMS)), SEND_EMIAL_INTENT);
                for (int i = 0; i < imageFiles.size(); i++) {
                    //mmsIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(imageFiles.get(i).toURL().toString()));
                    try {
                        mmsIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                mmsIntent.setType("image/jpg");//mmsIntent.setType("image/*"); Maybe?
                startActivity(mmsIntent);
            }
        });
    }

    private void resetImage(final String fileKey) {
        AlertDialog.Builder alert = new AlertDialog.Builder(MissionUpdate.this);
        alert.setTitle("Remove image?");
        alert.setCancelable(false);
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedImageView.setImageResource(R.drawable.ic_image);
                imageFiles.remove(fileKey);
                dialog.dismiss();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

    String mCurrentPhotoPath;

    File imageFile;
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
        imageFile = image;

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    static final int REQUEST_TAKE_PHOTO = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            ImageHandler.setImage(mCurrentPhotoPath, selectedImageView);
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


}
