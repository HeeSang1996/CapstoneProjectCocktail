package org.techtown.capstoneprojectcocktail;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class CocktailUploadActivity extends AppCompatActivity {
    ImageView imageView;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cocktail_upload_activity);
    }
}
