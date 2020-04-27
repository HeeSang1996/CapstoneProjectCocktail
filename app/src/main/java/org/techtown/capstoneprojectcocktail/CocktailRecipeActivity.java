package org.techtown.capstoneprojectcocktail;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CocktailRecipeActivity extends AppCompatActivity {

    @Override
    protected  void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.cocktail_recipe_activity);

        Intent intent = getIntent();

        TextView textForCocktailName = (TextView) findViewById(R.id.textView_cocktailName_recipe);
        String cocktailName = intent.getExtras().getString("cocktailName");
        textForCocktailName.setText(cocktailName);
    }
}
