package maheshwari.developer.android.nirbhaya.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.IOException;

import maheshwari.developer.android.nirbhaya.HelperClass.AndroidGPSTrackingActivity;
import maheshwari.developer.android.nirbhaya.HelperClass.ContactDb;
import maheshwari.developer.android.nirbhaya.HelperClass.DatabaseHelper;
import maheshwari.developer.android.nirbhaya.R;

public class AlertActivity extends Activity
{
    Button sendButton;
    String s = "";
    double longitude,latitude;
    AndroidGPSTrackingActivity gps;
    ContactDb mDb;
    Cursor c;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
        sendButton = (Button) findViewById(R.id.sendButton);
        mDb= new ContactDb(getApplicationContext());
        mDb.init();
        sendButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                gps = new AndroidGPSTrackingActivity(AlertActivity.this);
                if (gps.canGetLocation())
                {
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    s = "http://maps.google.com/maps?q=" + latitude + "," + longitude;
                }
                else
                {
                    gps.showSettingsAlert();
                }
                mDb = new ContactDb(getApplicationContext());
                mDb.init();
                mDb.getWritableDatabase();
                String msg = "Emergency! I am in trouble. Please help.";
                String Data = msg + "My current location link is : " + s;
                System.out.println("Location : " + s);
                sendMessage(Data, s);
            }
            private void sendMessage(String msg,String s)
            {
                Cursor c = mDb.getValue();
                c.moveToFirst();
                for(int i=0; i<c.getCount(); i++)
                {
                    c.moveToPosition(i);
                    String number = c.getString(2);
                    System.out.println("user number : " +number);
                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage(number, null, msg, null, null);
                    System.out.println("Message : " + msg);
                    Toast.makeText(AlertActivity.this,"Message sent successfully!",Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        });
    }
}