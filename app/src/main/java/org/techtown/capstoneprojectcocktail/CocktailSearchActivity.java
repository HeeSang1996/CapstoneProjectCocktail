package org.techtown.capstoneprojectcocktail;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

public class CocktailSearchActivity extends AppCompatActivity{

    final CocktailAdapterForSearch adapterForCocktailSearch = new CocktailAdapterForSearch();

    public FirebaseFirestore db = FirebaseFirestore.getInstance();

    //레시피 정보를 받기 위한 변수들
    String[] Recipe_name = new String[81];
    int[] ID = new int[81];
    String[] method = new String[81];
    String[] Recipe_Base = new String[81];
    String[] abv = new String[81];
    String[] ref = new String[81];
    Map<String, Number> Recipe_Ingredient;
    long[] Realabv = new long[81];
    int count;

    //재료 정보를 받기 위한 변수들
    String[] Ingredient_name = new String[127];
    int[] Ingredient_ID = new int[127];
    long[] Ingredient_Realabv = new long[127];
    String[] Ingredient_abv = new String[127];
    String[] Ingredient_ref = new String[127];
    String[] Ingredient_sugar = new String[127];
    String[] Ingredient_flavour = new String[127];
    String[] Ingredient_specific_gravity = new String[127];
    int Ingredient_count;

    RecyclerView recyclerViewForCocktailSearch;

    @Override
    protected  void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.cocktail_search_activity);


        final ToggleButton toggleForCocktailOrIngredient = findViewById(R.id.switch_ingredient_check);
        final Switch switchForUserMade = findViewById(R.id.switch_userRecipe_search);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner_orderBy_search);
        final EditText textForSearch = (EditText) findViewById(R.id.editText_search);
        //final RecyclerView recyclerViewForCocktailSearch = findViewById(R.id.recyclerViewForCocktail_search);
        recyclerViewForCocktailSearch = findViewById(R.id.recyclerViewForCocktail_search);
        LinearLayoutManager layoutManagerForCocktailSearch = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerViewForCocktailSearch.setLayoutManager(layoutManagerForCocktailSearch);

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
                    Toast.makeText(getApplicationContext(),"재료 검색 모드",Toast.LENGTH_LONG).show();
                    adapterForCocktailSearch.clearAllForAdapter();
                    switchForUserMade.setVisibility(View.INVISIBLE);
                    spinner.setVisibility(View.INVISIBLE);
                }
                else{
                    Toast.makeText(getApplicationContext(),"칵테일 검색 모드",Toast.LENGTH_LONG).show();
                    adapterForCocktailSearch.clearAllForAdapter();
                    switchForUserMade.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.VISIBLE);
                    setAdapterForCocktailSearchMethod();
                }
            }
        });

        Intent intent = getIntent();
        String ingredientName = intent.getExtras().getString("ingredientName");
        textForSearch.setImeOptions(EditorInfo.IME_ACTION_DONE);
        textForSearch.setText(ingredientName);
        textForSearch.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String inputText = textForSearch.getText().toString();
                    //재료 검색 모드에서 검색
                    if (toggleForCocktailOrIngredient.isChecked()) {
                        Toast.makeText(getApplicationContext(), inputText + " 재료 검색", Toast.LENGTH_SHORT).show();
                    }
                    //칵테일 검색 모드에서 검색
                    else {
                        //사용자들이 올린 칵테일 검색
                        if (switchForUserMade.isChecked()) {
                            Toast.makeText(getApplicationContext(), "사용자들이 올린 " + inputText + " 칵테일 검색", Toast.LENGTH_SHORT).show();
                        }
                        //기존에 있는 칵테일 레시피 검색
                        else {
                            Toast.makeText(getApplicationContext(), inputText + " 칵테일 검색", Toast.LENGTH_SHORT).show();
                            adapterForCocktailSearch.filterForCocktail(inputText);
                            recyclerViewForCocktailSearch.setAdapter(adapterForCocktailSearch);
                        }
                    }
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

        String initialText = textForSearch.getText().toString();
        if (textForSearch.getText().toString().length()==0){
            Toast.makeText(getApplicationContext(),"모든 칵테일 검색",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),initialText + " 칵테일 검색",Toast.LENGTH_SHORT).show();
        }

        setAdapterForCocktailSearchMethod();
        //수정필 테스트용

        /*
        for(int i=0; i < 81; i++)
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
                            //abv[0] = (String) document.get("abv");

                            Recipe_Ingredient = (Map<String, Number>) document.get("Ingredient_content");
                            //Recipe_Base[count] = (String) document.get("Recipe_Base");

                            //map으로 받아온 정보를 string으로 치환한뒤 유저에게 보여줄 수 있도록 replaceall함({, }, = 삭제 ml 추가)
                            Recipe_Base[count] = String.valueOf(Recipe_Ingredient);
                            Recipe_Base[count] = Recipe_Base[count].replaceAll("\\,", "ml ");
                            Recipe_Base[count] = Recipe_Base[count].replaceAll("\\{", " ");
                            Recipe_Base[count] = Recipe_Base[count].replaceAll("\\}", "ml ");
                            Recipe_Base[count] = Recipe_Base[count].replaceAll("\\=", " ");
                            //long형태로 받은 abv를 유저에게 보여줄 수 있도록 %를 붙여 재저장
                            Realabv[count] = (long) document.get("abv");
                            abv[count] = Realabv[count] + "%";
                            ref[count] = (String) document.get("ref");
                            adapterForCocktailSearch.addItem(new Cocktail(Recipe_name[count], ID[count], method[count], Recipe_Base[count], abv[count],ref[count]));
                            //Log.d(TAG, "DocumentSnapshot data: " + Recipe_name[count] + ID[count]+ method[count]+ Recipe_Base[count]+ abv[count]+ref[count]);
                            //refresh 해주는 함수(아마)
                            recyclerViewForCocktailSearch.setAdapter(adapterForCocktailSearch);

                        } else {
                            //Log.d(TAG, "No such document");
                        }
                    } else {
                        //Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        }
        */
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
                intent.putExtra("cocktailABV",item.getAbvNum());
                intent.putExtra("cocktailRef",item.getImageUrl());
                startActivity(intent);
            }
        });
    }

    private void setAdapterForCocktailSearchMethod(){
        for(int i=0; i < 81; i++)
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
                            //abv[0] = (String) document.get("abv");

                            Recipe_Ingredient = (Map<String, Number>) document.get("Ingredient_content");
                            //Recipe_Base[count] = (String) document.get("Recipe_Base");

                            //map으로 받아온 정보를 string으로 치환한뒤 유저에게 보여줄 수 있도록 replaceall함({, }, = 삭제 ml 추가)
                            Recipe_Base[count] = String.valueOf(Recipe_Ingredient);
                            Recipe_Base[count] = Recipe_Base[count].replaceAll("\\,", "ml ");
                            Recipe_Base[count] = Recipe_Base[count].replaceAll("\\{", " ");
                            Recipe_Base[count] = Recipe_Base[count].replaceAll("\\}", "ml ");
                            Recipe_Base[count] = Recipe_Base[count].replaceAll("\\=", " ");
                            //long형태로 받은 abv를 유저에게 보여줄 수 있도록 %를 붙여 재저장
                            Realabv[count] = (long) document.get("abv");
                            abv[count] = Realabv[count] + "%";
                            ref[count] = (String) document.get("ref");
                            adapterForCocktailSearch.addItem(new Cocktail(Recipe_name[count], ID[count], method[count], Recipe_Base[count], abv[count],ref[count]));
                            //Log.d(TAG, "DocumentSnapshot data: " + Recipe_name[count] + ID[count]+ method[count]+ Recipe_Base[count]+ abv[count]+ref[count]);
                            //refresh 해주는 함수(아마)
                            recyclerViewForCocktailSearch.setAdapter(adapterForCocktailSearch);

                        } else {
                            //Log.d(TAG, "No such document");
                        }
                    } else {
                        //Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        }
    }

    private void setAdapterForIngredientSearch(){
        for(int i=0; i < 81; i++)
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
                            //abv[0] = (String) document.get("abv");

                            Recipe_Ingredient = (Map<String, Number>) document.get("Ingredient_content");
                            //Recipe_Base[count] = (String) document.get("Recipe_Base");

                            //map으로 받아온 정보를 string으로 치환한뒤 유저에게 보여줄 수 있도록 replaceall함({, }, = 삭제 ml 추가)
                            Recipe_Base[count] = String.valueOf(Recipe_Ingredient);
                            Recipe_Base[count] = Recipe_Base[count].replaceAll("\\,", "ml ");
                            Recipe_Base[count] = Recipe_Base[count].replaceAll("\\{", " ");
                            Recipe_Base[count] = Recipe_Base[count].replaceAll("\\}", "ml ");
                            Recipe_Base[count] = Recipe_Base[count].replaceAll("\\=", " ");
                            //long형태로 받은 abv를 유저에게 보여줄 수 있도록 %를 붙여 재저장
                            Realabv[count] = (long) document.get("abv");
                            abv[count] = Realabv[count] + "%";
                            ref[count] = (String) document.get("ref");
                            adapterForCocktailSearch.addItem(new Cocktail(Recipe_name[count], ID[count], method[count], Recipe_Base[count], abv[count],ref[count]));
                            //Log.d(TAG, "DocumentSnapshot data: " + Recipe_name[count] + ID[count]+ method[count]+ Recipe_Base[count]+ abv[count]+ref[count]);
                            //refresh 해주는 함수(아마)
                            recyclerViewForCocktailSearch.setAdapter(adapterForCocktailSearch);

                        } else {
                            //Log.d(TAG, "No such document");
                        }
                    } else {
                        //Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        }
    }
}
