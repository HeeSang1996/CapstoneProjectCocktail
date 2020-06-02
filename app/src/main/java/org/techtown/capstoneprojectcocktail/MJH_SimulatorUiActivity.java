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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MJH_SimulatorUiActivity extends AppCompatActivity implements View.OnClickListener {
    public static Context uiMain;

    public FloatingActionButton floatingActionButtonForAddList, floatingActionButtonForAddList2, button_simulation_action;
    public static MJH_ListviewAdapter adapterMIN;
    public ListView listview;

    public int listUpdateFlag = 0;
    public int lastStepTechFlag = 0;
    public static String listUpdateTech;
    public static ArrayList<Integer> listUpdateStep = new ArrayList<Integer>();
    public static ArrayList<MJH_Object_ingredient> listUpdateIngredient = new ArrayList<MJH_Object_ingredient>();
    public static ArrayList<Float> listUpdateIngredientAmount = new ArrayList<Float>();

    public static int stepNum = 0;

    public static MJH_Object_simulator test = new MJH_Object_simulator(1, 1);

    public MJH_Object_ingredient[] ingredientList = new MJH_Object_ingredient[200];
    public int listCount = 0;
    public MJH_Object_color colorBuffer;
    public Map<String, Number> ingredientRGB;

    @Override
    public void onCreate(Bundle saveInstanceState){
        setAdapterForIngredientSearch();
        super.onCreate(saveInstanceState);
        setContentView(R.layout.mjh_test);
        uiMain = this;

        floatingActionButtonForAddList = (FloatingActionButton) findViewById(R.id.floatingActionButtonForAddList);
        floatingActionButtonForAddList.setOnClickListener(this);
        floatingActionButtonForAddList2 = (FloatingActionButton) findViewById(R.id.floatingActionButtonForAddList2);
        floatingActionButtonForAddList2.setOnClickListener(this);

        button_simulation_action = (FloatingActionButton) findViewById(R.id.button_simulation_action);
        button_simulation_action.setOnClickListener(this);

        // Adapter 생성
        adapterMIN = new MJH_ListviewAdapter() ;
        adapterMIN.callByPopup = 0;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapterMIN);

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
            stepNum++;
            adapterMIN.addItem(Integer.toString(stepNum), listUpdateTech, listUpdateStep, listUpdateIngredient, listUpdateIngredientAmount);
            listview.setAdapter(adapterMIN);
            listUpdateFlag = 0;

            try{
                test.addStepBuildings(stepNum, listUpdateStep, listUpdateIngredient, listUpdateIngredientAmount, true);
            }catch(Exception e){
                Log.w(TAG, "App error!", e);
            }
            if(listUpdateTech.equals("Layering") || listUpdateTech.equals("Gradient") ){
                lastStepTechFlag = 1;
            }
        }
        adapterMIN.callByPopup = 0;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.floatingActionButtonForAddList:
                //anim();
                Intent intent = new Intent(this,MJH_Popup1Activity.class);
                startActivityForResult(intent, 1);
                adapterMIN.callByPopup = 1;
                break;
            case R.id.floatingActionButtonForAddList2:
                //anim();
                if(stepNum != 0)
                    stepNum--;
                adapterMIN.listViewItemList.remove(adapterMIN.listViewItemList.size()-1);
                listview.setAdapter(adapterMIN);

                if(stepNum == 0){
                    lastStepTechFlag = 0;
                }
                else if(adapterMIN.listViewItemList.get(adapterMIN.listViewItemList.size()-1).getTech().equals("Layering") || adapterMIN.listViewItemList.get(adapterMIN.listViewItemList.size()-1).getTech().equals("Gradient") ){
                    lastStepTechFlag = 1;
                }
                else{
                    lastStepTechFlag = 0;
                }
                break;
            case R.id.button_simulation_action:
                if(test.simulatorStep.size() == 0) {
                    Toast myToast = Toast.makeText(this.getApplicationContext(),"칵테일 시뮬레이션 스템을 추가해 주세요!", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else{
                    Intent intent2 = new Intent(this, MJH_SimulatorGraphicActivity.class);
                    startActivityForResult(intent2, 1);
                    break;
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //데이터 받기
                //String result = data.getStringExtra("result");
                //Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setAdapterForIngredientSearch(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef;

        for(int i=0; i < 140; i++) {
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
