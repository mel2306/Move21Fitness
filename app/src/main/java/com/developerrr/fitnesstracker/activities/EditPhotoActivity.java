package com.developerrr.fitnesstracker.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.developerrr.fitnesstracker.R;

import java.io.File;

public class EditPhotoActivity extends AppCompatActivity {

    ImageView imgedit;

    String path="";
    Uri inputImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo);

        imgedit=(ImageView) findViewById(R.id.imgedit);

        path=getIntent().getExtras().getString("path");
        File imgfile = new File(path);

        if(imgfile.exists())
        {
            Bitmap mybitmap= BitmapFactory.decodeFile(imgfile.getAbsolutePath());
            imgedit.setImageBitmap(RotateBitmap(mybitmap, -90));
        }

        inputImageUri=Uri.fromFile(new File(path));
        edit_trail();
    }
    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix= new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source,0,0,source.getWidth(),source.getHeight(),matrix, true);

    }
    //hier können wir dann unsere Sticker die wir auf dem Bild haben wollen zuweisen/machen
    public void edit_trail()
    {


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode,data);
        if(resultCode==RESULT_OK)
        {
            switch(requestCode)
            {
                case 200:
                    //===we can handle result uri as we want====
                    Uri outputUri=data.getData();
                    imgedit.setImageURI(outputUri);
                    //==set edited image===
                    break;
            }
        }
    }
}