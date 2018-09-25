package com.haris.savongroceries;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_PICK_STORE = 2;
    String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onSelectStoreClick(View view) {
        Intent intent = new Intent(MainActivity.this, StoresActivity.class);
        startActivityForResult(intent, REQUEST_PICK_STORE);
    }

    public void onTakePictureClick(View view) {
        dispatchTakePictureIntent();
    }

    public void onScanCodeClick(View view) {
        IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setCameraId(0);
        intentIntegrator.setPrompt("SCAN");
        intentIntegrator.setBarcodeImageEnabled(false);
        intentIntegrator.initiateScan();
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

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
            /*Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");*/
                //mImageView.setImageBitmap(imageBitmap);
                SharedPreferences.Editor editor = getSharedPreferences("my_prefs", MODE_PRIVATE).edit();
                editor.putString("img", mCurrentPhotoPath);
                editor.apply();

            } else if (requestCode == REQUEST_PICK_STORE) {
                String storeId = data.getStringExtra("store_id");
                SharedPreferences.Editor editor = getSharedPreferences("my_prefs", MODE_PRIVATE).edit();
                editor.putString("store_id", storeId);
                editor.apply();

            } else {
                IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                if (Result != null) {
                    if (Result.getContents() == null) {
                        Log.d("MainActivity", "cancelled scan");
                        Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Scanned -> " + Result.getContents(), Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = getSharedPreferences("my_prefs", MODE_PRIVATE).edit();
                        editor.putString("product_sku", Result.getContents());
                        editor.apply();
                    }
                } else {
                    super.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
    }
}
