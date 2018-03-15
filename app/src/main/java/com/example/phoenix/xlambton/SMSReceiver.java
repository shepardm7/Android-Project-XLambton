package com.example.phoenix.xlambton;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.example.phoenix.xlambton.models.DbHelper;

/**
 * Created by Phoenix on 17-Aug-17.
 */

public class SMSReceiver extends BroadcastReceiver {
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {

        Object[] smsPdus = (Object[]) intent.getSerializableExtra("pdus");
        byte[] smsPdu = (byte[]) smsPdus[0];

        SmsMessage sms;
        String smsFormat = (String) intent.getSerializableExtra("format");
        sms = SmsMessage.createFromPdu(smsPdu, smsFormat);
        String phone = sms.getDisplayOriginatingAddress();
        DbHelper db = new DbHelper(context);

        if (db.isAgent(phone)) {

            if (!MainActivity.appOpenFlag) {
                try {
                    Context ctx = context; // or you can replace **'this'** with your **ActivityName.this**
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.example.phoenix.xlambton");
                    ctx.startActivity(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            Toast.makeText(context, "You have received a message from " + db.getAgentNameFromPhone(phone), Toast.LENGTH_SHORT).show();
            MediaPlayer mp = MediaPlayer.create(context, R.raw.capisci);
            mp.start();
            db.close();
        }

    }
}
