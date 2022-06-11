package com.developerrr.fitnesstracker.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.developerrr.fitnesstracker.R;
import com.felipecsl.gifimageview.library.GifImageView;

public class SplashActivity extends AppCompatActivity {


    private static final int MY_PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION = 101;
    final int SPLASH_DELAY_TIME=3;

    //GifImageView erzeugt Variable, zu der Foto dann zugewiesen wird
    GifImageView imageView;

    //Activity Splash visible setzen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //API des Geräts returnen und vergleichen ob größer/gleich Android 6 (M)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        //Gifimage (Wo Hin soll das Bild?)
        imageView=findViewById(R.id.gifimage);
        //Hintergrunfarbe GifImage festlegen
        imageView.setBackgroundColor(getColor(R.color.yellowbg));
        //Grafik in imageView
        Glide.with(this).load(R.raw.gif).into(imageView);

        //Methodenaufruf askForPermission
      askForPermission();

    }

    private void askForPermission() {

        //API des Geräts returnen und vergleichen ob größer/gleich API29(Q)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // manifest.permission: Ermöglicht es einer Anwendung, körperliche Aktivität zu erkennen.
            String[] permissions = new String[]{Manifest.permission.ACTIVITY_RECOGNITION};
            ActivityCompat.requestPermissions(this, permissions, MY_PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION);
        }
        else
        {
            //Activity Starten Methode aufrufen
            startActivityNow();
        }
    }

    @Override
    //???
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION) {
            String permission = permissions[0];
            int grantResult = grantResults[0];

            if (permission.equals(Manifest.permission.ACTIVITY_RECOGNITION)) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    startActivityNow();
                } else {
                    requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, MY_PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION);
                }
            }
        }
    }

    private void startActivityNow() {
        //Thread im Hintergrund erzeugen
        Thread background = new Thread() {
            public void run() {
                try {
                    //sleep: Zeit von Spalsh bis zum Haupbildschirm
                    sleep(SPLASH_DELAY_TIME*1000);

                    Intent i=new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                } catch (Exception e) {

                }
            }
        };
        // Thread starten
        background.start();
    }

}