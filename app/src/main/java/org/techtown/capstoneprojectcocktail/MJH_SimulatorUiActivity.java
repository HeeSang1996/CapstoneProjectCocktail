package org.techtown.capstoneprojectcocktail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MJH_SimulatorUiActivity extends AppCompatActivity implements View.OnClickListener {
    public FloatingActionButton floatingActionButtonForAddList;

    public static Context uiMain;
    public MJH_ListviewAdapter adapter;

    public int listUpdateFlag = 0;
    public String listUpdateTech;
    public ArrayList<Integer> listUpdateStep = new ArrayList<Integer>();
    public ArrayList<MJH_Object_ingredient> listUpdateIngredient = new ArrayList<MJH_Object_ingredient>();


    ListView listview;

    public MJH_Object_ingredient[] ingredientList = new MJH_Object_ingredient[200];
    public int listCount = 0;
    public MJH_Object_color colorBuffer;
    public Map<String, Number> ingredientRGB;

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.mjg_test);
        uiMain = this;
        setAdapterForIngredientSearch();


        Intent intent = getIntent();

        floatingActionButtonForAddList = (FloatingActionButton) findViewById(R.id.floatingActionButtonForAddList);
        floatingActionButtonForAddList.setOnClickListener(this);





        // Adapter 생성
        adapter = new MJH_ListviewAdapter() ;
        adapter.callByPopup = 0;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);



        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                MJH_ListviewItem item = (MJH_ListviewItem) parent.getItemAtPosition(position) ;

                //Snackbar.make(v, titleStep, Snackbar.LENGTH_LONG).setAction("Action", null).show();

                // TODO : use item data.
            }
        }) ;

    }

    @Override
    protected void onResume() {
        super.onResume();


        if (listUpdateFlag == 1) {
            adapter.addItem("1", listUpdateTech, listUpdateStep, listUpdateIngredient);
            listUpdateFlag = 0;
            listview.setAdapter(adapter);
        }
    }



    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.floatingActionButtonForAddList:
                //anim();
                Toast.makeText(this, "Floating Action Button", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,MJH_Popup1Activity.class);
                intent.putExtra("data", "Test Popup");
                startActivityForResult(intent, 1);
                break;
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //데이터 받기
                String result = data.getStringExtra("result");
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();

            }
        }
    }



    public void setAdapterForIngredientSearch(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef;

        for(int i=0; i < 127; i++) {
            ingredientList[i] = new MJH_Object_ingredient();
            docRef = db.collection("Ingredient").document(String.valueOf(5001 + i));

            final int finalI = i;
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            listCount = finalI;
                            try{
                                ingredientList[listCount].name = (String)document.get("Ingredient_name");
                                ingredientList[listCount].id = 5001+ listCount;
                                ingredientList[listCount].abv = Float.parseFloat(document.get("abv").toString());
                                ingredientList[listCount].sugar = Float.parseFloat(document.get("sugar_rate").toString());
                                ingredientList[listCount].sour = Float.parseFloat(document.get("sour").toString());
                                ingredientList[listCount].salty = Float.parseFloat(document.get("salty").toString());
                                ingredientList[listCount].bitter = Float.parseFloat(document.get("bitter").toString());
                                ingredientList[listCount].specific_gravity = Float.parseFloat(document.get("specific_gravity").toString());

                                ingredientRGB = (Map<String, Number>) document.getData().get("Ingredient_color");
                                colorBuffer = new MJH_Object_color(Float.parseFloat(ingredientRGB.get("Red").toString()), Float.parseFloat(ingredientRGB.get("Green").toString()),
                                        Float.parseFloat(ingredientRGB.get("Blue").toString()));
                                ingredientList[listCount].my_color = colorBuffer;
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }

                }
            });
        }
        listCount = 0;

    }

}
