package org.techtown.capstoneprojectcocktail;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CocktailRecipeActivity extends AppCompatActivity {

    @Override
    protected  void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.cocktail_recipe);
    }
}
