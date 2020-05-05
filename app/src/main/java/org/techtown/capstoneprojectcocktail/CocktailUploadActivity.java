package org.techtown.capstoneprojectcocktail;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CocktailUploadActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 672;
    private String imageFilePath;
    private Uri photoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cocktail_upload_activity);

        Button takePictureButtonCocktailUpload = findViewById(R.id.button_takePicture_cocktail_upload);
        takePictureButtonCocktailUpload.setOnClickListener(new OnSingleClickListener(){
            @Override
            public void onSingleClick(View view){
                //Snackbar.make(view, "사진을 찍자", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                sendTakePhotoIntent();
            }
        });

        Button uploadButtonCocktailUpload = findViewById(R.id.button_upload_cocktail_upload);
        uploadButtonCocktailUpload.setOnClickListener(new OnSingleClickListener(){
            @Override
            public void onSingleClick(View view){
                //영진 여기 채워줘 나중에 ㅎ
                Snackbar.make(view, "업로드를 하자", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        Button cancelButtonCocktailUpload = findViewById(R.id.button_cancel_cocktail_upload);
        cancelButtonCocktailUpload.setOnClickListener(new OnSingleClickListener(){
            @Override
            public void onSingleClick(View view){
                //Snackbar.make(view, "으악 쥬금", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                finish();
            }
        });

        TextInputLayout textInputLayoutForCocktailName = findViewById(R.id.textInputLayout_cocktailName_cocktail_upload);
        textInputLayoutForCocktailName.setCounterEnabled(true);
        textInputLayoutForCocktailName.setCounterMaxLength(20);

        TextInputLayout textInputLayoutForCocktailHowToMake = findViewById(R.id.textInputLayout_cocktailHowToMake_cocktail_upload);
        textInputLayoutForCocktailHowToMake.setCounterEnabled(true);
        textInputLayoutForCocktailHowToMake.setCounterMaxLength(300);

        TextInputLayout textInputLayoutForCocktailDescription = findViewById(R.id.textInputLayout_cocktailDescription_cocktail_upload);
        textInputLayoutForCocktailDescription.setCounterEnabled(true);
        textInputLayoutForCocktailDescription.setCounterMaxLength(300);


        /*
        ChipGroup chipGroup = findViewById(R.id.chipGroup_cocktail_upload);
        Chip chip = new Chip(this); // Must contain context in parameter
        chip.setText("Apple");
        chip.setCheckable(true);
        chipGroup.addView(chip);
         */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);
            ExifInterface exif = null;

            try {
                exif = new ExifInterface(imageFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            int exifOrientation;
            int exifDegree;

            if (exif != null) {
                exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                exifDegree = exifOrientationToDegrees(exifOrientation);
            } else {
                exifDegree = 0;
            }

            ((ImageView) findViewById(R.id.imageView_cocktail_upload)).setImageBitmap(rotate(bitmap, exifDegree));
        }
    }

    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    private Bitmap rotate(Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private void sendTakePhotoIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }

            if (photoFile != null) {
                photoUri = FileProvider.getUriForFile(this, getPackageName(), photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "TEST_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,      /* prefix */
                ".jpg",         /* suffix */
                storageDir          /* directory */
        );
        imageFilePath = image.getAbsolutePath();
        return image;
    }
}
