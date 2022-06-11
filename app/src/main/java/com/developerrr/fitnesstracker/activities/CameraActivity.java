package com.developerrr.fitnesstracker.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.developerrr.fitnesstracker.R;
//Activity für die Kamera um Fotos aufnehmen zu können / Filter verwenden
//Wird von Home Fragment aufgerufen
public class CameraActivity extends AppCompatActivity {

    ImageView img_cam;

    private int REQUEST_CODE_PERMISSIONS=101;
    private final String[] REQUIRED_PERMISSION=new String [] {
            "android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.MANAGE_EXTERNAL_STORAGE"
    };


    @Override
    //Verknüpfung mit activity_camera (Layout und Funktionen)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        img_cam=(ImageView) findViewById(R.id.img_cam);

        if(allPermissionsGranted())
        {
            //======you can write code if granted
        }
        else
        {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSION,REQUEST_CODE_PERMISSIONS);
        }

        img_cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //===Toast for click conformation or jump to new Activity named CameraActivity2
                Intent intent= new Intent (CameraActivity.this, CameraActivity2.class);
                startActivity(intent);
            }
        });


    }

    private boolean allPermissionsGranted(){

        for (String permission: REQUIRED_PERMISSION)
        {
            if(ContextCompat.checkSelfPermission(this,permission)!= PackageManager.PERMISSION_GRANTED)
            {
                return false;
            }

        }
        return true;
    }

}
