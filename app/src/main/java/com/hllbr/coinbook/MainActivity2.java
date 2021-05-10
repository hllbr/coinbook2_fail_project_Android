package com.hllbr.coinbook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class MainActivity2 extends AppCompatActivity {
    Bitmap selectedIcon;
    ImageView addIconVi;
    EditText coinnametext,captext,ceotext,yeartext,projetext,ortext;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        addIconVi = findViewById(R.id.addIconView);
        coinnametext = findViewById(R.id.conText);
        captext = findViewById(R.id.capnoText);
        ceotext =findViewById(R.id.ceoText);
        yeartext = findViewById(R.id.yearText);
        projetext = findViewById(R.id.projectText);
        ortext = findViewById(R.id.orText);
        button = findViewById(R.id.saveButton);

    }
    public void save(View view){
        String coinName = coinnametext.getText().toString();
        String cap = captext.getText().toString();
        String ceoname = ceotext.getText().toString();
        String yearName = yeartext.getText().toString();
        String project = projetext.getText().toString();
        String pozorneg = projetext.getText().toString();
        Bitmap smalImage = makeSmallerImage(selectedIcon,300);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        smalImage.compress(Bitmap.CompressFormat.PNG,50,outputStream);
        byte[] byteArray = outputStream.toByteArray();
        try{
            SQLiteDatabase database = this.openOrCreateDatabase("Coins",MODE_PRIVATE,null);
            database.execSQL("CREATE TABLE IF NOT EXISTS coins(id INTEGER PRIMARY KEY,coinname VARCHAR,capname VARCHAR,ceoname VARCHAR,yearname VARCHAR,project VARCHAR,orpn VARCHAR,image BLOB)");
            String sqlSting ="INSERT INTO coins(coinname,capname,ceoname,yearname,project,ornp,image) VALUES(?,?,?,?,?,?,?)";
            SQLiteStatement sqLiteStatement = database.compileStatement(sqlSting);
            sqLiteStatement.bindString(1,coinName);
            sqLiteStatement.bindString(2,cap);
            sqLiteStatement.bindString(3,ceoname);
            sqLiteStatement.bindString(4,yearName);
            sqLiteStatement.bindString(5,project);
            sqLiteStatement.bindString(6,pozorneg);
            sqLiteStatement.bindBlob(7,byteArray);
            sqLiteStatement.execute();


        }catch (Exception ex){
            ex.printStackTrace();
        }
            finish();
    }
    public Bitmap makeSmallerImage(Bitmap bitmap,int maksimumsize){
        int width = bitmap.getWidth();//genişlik
        int height = bitmap.getHeight();//uzunluk
        float bitmapRatio = (float)(width/height);
        if(bitmapRatio>1){
            width = maksimumsize;
            height=(int)(width/bitmapRatio);
        }else{
            height = maksimumsize;
            width = (int)(height*bitmapRatio);
        }
        return Bitmap.createScaledBitmap(bitmap,width,height,true);
    }
    public void addIcon(View view){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }else{
            Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentToGallery,2);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1 ){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentToGallery,2);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 2 && resultCode == RESULT_OK && data != null){

            Uri ıconData = data.getData();
            try{
                if(Build.VERSION.SDK_INT >= 28){
                    ImageDecoder.Source source2 = ImageDecoder.createSource(this.getContentResolver(),ıconData);
                    selectedIcon = ImageDecoder.decodeBitmap(source2);
                    addIconVi.setImageBitmap(selectedIcon);
                }else{
                    selectedIcon = MediaStore.Images.Media.getBitmap(this.getContentResolver(),ıconData);
                    addIconVi.setImageBitmap(selectedIcon);
                }

            }catch (Exception ex){
                ex.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}