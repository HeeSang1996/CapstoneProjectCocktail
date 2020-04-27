package org.techtown.capstoneprojectcocktail;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CocktailSearchActivity extends AppCompatActivity {

    @Override
    protected  void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.cocktail_search_activity);

        Intent intent = getIntent();

        EditText textForSearch = (EditText) findViewById(R.id.editText_search);
        String ingredientName = intent.getExtras().getString("ingredientName");
        textForSearch.setText(ingredientName);
    }
}
