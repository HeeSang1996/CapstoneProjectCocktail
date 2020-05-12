package org.techtown.capstoneprojectcocktail;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class CocktailSearchActivity extends AppCompatActivity{

    final CocktailAdapterForSearch adapterForCocktailSearch = new CocktailAdapterForSearch();

    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    String[] Recipe_name = new String[20];
    int[] ID = new int[20];
    String[] method = new String[20];
    String[] Recipe_Base = new String[20];
    String[] abv = new String[20];
    String[] ref = new String[20];
    long[] Realabv = new long[20];
    int count;

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
                                adapterForCocktailSearch.filter(inputText );
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

        final RecyclerView recyclerViewForCocktailSearch = findViewById(R.id.recyclerViewForCocktail_search);
        LinearLayoutManager layoutManagerForCocktailSearch = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerViewForCocktailSearch.setLayoutManager(layoutManagerForCocktailSearch);

        String initialText = textForSearch.getText().toString();
        if (textForSearch.getText().toString().length()==0){
            Toast.makeText(getApplicationContext(),"모든 칵테일 검색",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),initialText + " 칵테일 검색",Toast.LENGTH_SHORT).show();
        }

        //수정필 테스트용
        for(int i=0; i < 20; i++)
        {
            List<String> list;
            count = i;
            DocumentReference docRef = db.collection("Recipe").document(String.valueOf(i+6001));

            final int finalI = i;
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            Recipe_name[count] = (String) document.get("Recipe_name");
                            ID[count] = 6001+ count;
                            method[count] = (String) document.get("method");
                            Recipe_Base[count] = (String) document.get("Recipe_Base");
                            //abv[0] = (String) document.get("abv");
                            Realabv[count] = (long) document.get("abv");
                            abv[count] = Realabv[count] + "%";
                            ref[count] = (String) document.get("ref");
                            adapterForCocktailSearch.addItem(new Cocktail(Recipe_name[count], ID[count], method[count], Recipe_Base[count], abv[count],ref[count]));
                            //Log.d(TAG, "DocumentSnapshot data: " + Recipe_name[count] + ID[count]+ method[count]+ Recipe_Base[count]+ abv[count]+ref[count]);
                            //refresh 해주는 함수(아마)
                            recyclerViewForCocktailSearch.setAdapter(adapterForCocktailSearch);

                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        }
        //수정필 테스트용

        adapterForCocktailSearch.setOnItemClickListener(new OnCocktailItemClickListenerForSearch() {
            @Override
            public void onItemClick(CocktailAdapterForSearch.ViewHolder holder, View view, int position) {
                Cocktail item = adapterForCocktailSearch.getItem(position);
                //Toast.makeText(getActivity().getApplicationContext(),"선택된 칵테일: " + item.getName(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(view.getContext(), CocktailRecipeActivity.class);
                intent.putExtra("cocktailName", item.getName());
                intent.putExtra("cocktailDescription",item.getDescription());
                intent.putExtra("cocktailIngredient",item.getIngredient());
                intent.putExtra("cocktailRef",item.getImageUrl());
                startActivity(intent);
            }
        });
    }
}
