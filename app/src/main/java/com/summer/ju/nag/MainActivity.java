package com.summer.ju.nag;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView mPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPhoto = (ImageView) findViewById(R.id.photo);

    }

    public void sendNag(View view) {
        SmsManager smsManager = SmsManager.getDefault();
        String sendTo = "5412314575";
        String myMessage = "Android supports programmatic SMS messaging!";
        smsManager.sendTextMessage(sendTo, null, myMessage, null, null);



//        Uri number = Uri.parse("smsto:5412314575");
//
//        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, number);
//        smsIntent.putExtra("sms_body", "hello there! IF YOU SEE THIS, IT WORKS!");
//        try {
//            startActivity(smsIntent);
//        } catch (Exception ex) {
//            Toast.makeText(MainActivity.this, "Failed...", Toast.LENGTH_LONG).show();
//            ex.printStackTrace();
//        }
    }

    public void sendEvent(View view){

        Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        //WORK ON CREATING EVENTS AT DIFFERENT TIMES
        intent.putExtra("beginTime", cal.getTimeInMillis());
        intent.putExtra("allDay", true);
        intent.putExtra("rrule", "FREQ=YEARLY");
        intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
        intent.putExtra("title", "A Test Event from android app");
        startActivity(intent);
//
//        Intent intent = new Intent(Intent.ACTION_INSERT);
//        intent.setType("vnd.android.cursor.item/event");
//        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, 1447146000);
//        //intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,1447146000);
//        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
//        intent.putExtra(CalendarContract.Events.TITLE, "Neel Birthday");
//        intent.putExtra(CalendarContract.Events.DESCRIPTION, "This is a sample description");
//        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "My Guest House");

    }

    public void sendEmail(View view){

        String[] TO = {"sbrochtrup@gmail.com"};
        String[] CC = {"jsayumi@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void call(View view) {
        Uri number = Uri.parse("tel:5412314575");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);
    }

    public void map(View view){
        // Map point based on address
        Uri location = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
        startActivity(mapIntent);
    }

    public void badDirection(View view) {
        Uri webpage = Uri.parse("http://www.onedirectionmusic.com/#");
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(webIntent);
    }

    public void takePhoto(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mPhoto.setImageBitmap(imageBitmap);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
