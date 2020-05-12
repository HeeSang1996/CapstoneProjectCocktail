package org.techtown.capstoneprojectcocktail;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class CocktailSearchActivity extends AppCompatActivity{

    CocktailAdapterForSearch adapterForCocktailSearchChanges;

    @Override
    protected  void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.cocktail_search_activity);


        final ToggleButton toggleForCocktailOrIngredient = findViewById(R.id.switch_ingredient_check);
        final Switch switchForUserMade = findViewById(R.id.switch_userRecipe_search);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner_orderBy_search);
        switchForUserMade.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    //Toast.makeText(getApplicationContext(),"사용자 레시피 검색 ON",Toast.LENGTH_LONG).show();
                }else{
                    //Toast.makeText(getApplicationContext(),"사용자 레시피 검색 OFF",Toast.LENGTH_LONG).show();
                }
            }
        });

        toggleForCocktailOrIngredient.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    //Toast.makeText(getApplicationContext(),"재료 검색 모드",Toast.LENGTH_LONG).show();
                    switchForUserMade.setVisibility(View.INVISIBLE);
                    spinner.setVisibility(View.INVISIBLE);
                }
                else{
                    //Toast.makeText(getApplicationContext(),"칵테일 검색 모드",Toast.LENGTH_LONG).show();
                    switchForUserMade.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.VISIBLE);
                }
            }
        });

        Intent intent = getIntent();
        final EditText textForSearch = (EditText) findViewById(R.id.editText_search);
        String ingredientName = intent.getExtras().getString("ingredientName");
        textForSearch.setImeOptions(EditorInfo.IME_ACTION_DONE);
        textForSearch.setText(ingredientName);
        textForSearch.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if(actionId == EditorInfo.IME_ACTION_DONE)
                {
                    String inputText = textForSearch.getText().toString();
                    if(inputText.length()==0){
                        if (toggleForCocktailOrIngredient.isChecked()){
                            Toast.makeText(getApplicationContext(),"모든 재료 검색",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if(switchForUserMade.isChecked()){
                                Toast.makeText(getApplicationContext(),"사용자들이 올린 모든 칵테일 검색",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(),"모든 칵테일 검색",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    else {
                        if (toggleForCocktailOrIngredient.isChecked()){
                            Toast.makeText(getApplicationContext(),inputText + " 재료 검색",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if(switchForUserMade.isChecked()){
                                Toast.makeText(getApplicationContext(),"사용자들이 올린 " + inputText + " 칵테일 검색",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(),inputText + " 칵테일 검색",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    return false;
                }
                return false;
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(),"선택된 정렬순서: " + parent.getItemAtPosition(position),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        RecyclerView recyclerViewForCocktailSearch = findViewById(R.id.recyclerViewForCocktail_search);
        LinearLayoutManager layoutManagerForCocktailSearch = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerViewForCocktailSearch.setLayoutManager(layoutManagerForCocktailSearch);
        final CocktailAdapterForSearch adapterForCocktailSearch = new CocktailAdapterForSearch();

        String initialText = textForSearch.getText().toString();
        if (textForSearch.getText().toString().length()==0){
            Toast.makeText(getApplicationContext(),"모든 칵테일 검색",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),initialText + " 칵테일 검색",Toast.LENGTH_SHORT).show();
        }

        //수정필 테스트용
        for(int i=0; i<20; i++) {
            if( i ==5){
                adapterForCocktailSearch.addItem(new Cocktail("맛있는 칵테일 " + i, i, "맛있는 칵테일 " + i + "의 설명 정말 맛있다 맛있는 칵테일" + i + "의 설명 정말 맛있다",
                        "Whisky1", i*10 + " %","url"));
            }
            else{
                adapterForCocktailSearch.addItem(new Cocktail("맛있는 칵테일 " + i, i, "맛있는 칵테일 " + i + "의 설명 정말 맛있다 맛있는 칵테일" + i + "의 설명 정말 맛있다",
                        "Whisky0", i*10 + " %","url"));
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
}
