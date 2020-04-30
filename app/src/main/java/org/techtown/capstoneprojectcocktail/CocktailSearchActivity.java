package org.techtown.capstoneprojectcocktail;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class CocktailSearchActivity extends AppCompatActivity implements TextWatcher{

    CocktailAdapterForSearch adapterForCocktailSearchChanges;

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

        RecyclerView recyclerViewForCocktailSearch = findViewById(R.id.recyclerViewForCocktail_search);
        LinearLayoutManager layoutManagerForCocktailSearch = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerViewForCocktailSearch.setLayoutManager(layoutManagerForCocktailSearch);
        final CocktailAdapterForSearch adapterForCocktailSearch = new CocktailAdapterForSearch();

        //수정필 테스트용
        for(int i=0; i<20; i++) {
            if( i ==5){
                adapterForCocktailSearch.addItem(new Cocktail("맛있는 칵테일 " + i, i, "맛있는 칵테일 " + i + "의 설명 정말 맛있다 맛있는 칵테일" + i + "의 설명 정말 맛있다",
                        "Whisky1", i*10 + " %"));
            }
            else{
                adapterForCocktailSearch.addItem(new Cocktail("맛있는 칵테일 " + i, i, "맛있는 칵테일 " + i + "의 설명 정말 맛있다 맛있는 칵테일" + i + "의 설명 정말 맛있다",
                        "Whisky0", i*10 + " %"));
            }
        }
        //수정필 테스트용

        recyclerViewForCocktailSearch.setAdapter(adapterForCocktailSearch);
        adapterForCocktailSearch.setOnItemClickListener(new OnCocktailItemClickListenerForSearch() {
            @Override
            public void onItemClick(CocktailAdapterForSearch.ViewHolder holder, View view, int position) {
                Cocktail item = adapterForCocktailSearch.getItem(position);
                //Toast.makeText(getActivity().getApplicationContext(),"선택된 칵테일: " + item.getName(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(view.getContext(), CocktailRecipeActivity.class);
                intent.putExtra("cocktailName", item.getName());
                intent.putExtra("cocktailDescription",item.getDescription());
                intent.putExtra("cocktailIngredient",item.getIngredient());
                startActivity(intent);
            }
        });
    }



    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

}
