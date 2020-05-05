package org.techtown.capstoneprojectcocktail;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;

public class CocktailUploadActivity extends AppCompatActivity {
    ImageView imageView;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cocktail_upload_activity);

        Button takePictureButtonCocktailUpload = findViewById(R.id.button_takePicture_cocktail_upload);
        takePictureButtonCocktailUpload.setOnClickListener(new OnSingleClickListener(){
            @Override
            public void onSingleClick(View view){
                Snackbar.make(view, "사진을 찍자", Snackbar.LENGTH_LONG).setAction("Action", null).show();
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
                Snackbar.make(view, "취소를 하자", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }
}
