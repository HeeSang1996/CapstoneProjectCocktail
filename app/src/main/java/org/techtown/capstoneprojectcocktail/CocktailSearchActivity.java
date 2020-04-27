package org.techtown.capstoneprojectcocktail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class CocktailSearchActivity extends AppCompatActivity {

    @Override
    protected  void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.cocktail_search_activity);

        Intent intent = getIntent();

        EditText textForSearch = (EditText) findViewById(R.id.editText_search);
        String ingredientName = intent.getExtras().getString("ingredientName");
        textForSearch.setText(ingredientName);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_orderBy_search);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Snackbar.make(view, "선택된 정렬순서: " + parent.getItemAtPosition(position), Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
