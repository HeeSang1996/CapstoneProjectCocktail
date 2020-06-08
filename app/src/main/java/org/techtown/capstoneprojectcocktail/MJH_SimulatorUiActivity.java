package org.techtown.capstoneprojectcocktail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
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

    public int isFirst = 0;

    public int listUpdateFlag = 0;
    public int lastStepTechFlag = 0;

    public static ArrayList<Integer> usingStep = new ArrayList<Integer>();
    public static ArrayList<Integer> usingStepNum = new ArrayList<Integer>();

    public static String listUpdateTech; // 팝업1에서 선택된 기술
    public static ArrayList<Integer> listUpdateStep = new ArrayList<Integer>();
    public static ArrayList<MJH_Object_ingredient> listUpdateIngredient = new ArrayList<MJH_Object_ingredient>();
    public static ArrayList<Float> listUpdateIngredientAmount = new ArrayList<Float>();

    public static int stepNum = 0;

    public static MJH_Object_simulator test;

    public MJH_Object_ingredient[] ingredientList = new MJH_Object_ingredient[200];
    public int listCount = 0;
    public MJH_Object_color colorBuffer;
    public Map<String, Number> ingredientRGB;

    public CheckBox cb1;
    public CheckBox cb2;

    Button button1;
    @Override
    public void onCreate(Bundle saveInstanceState){
        setAdapterForIngredientSearch();
        super.onCreate(saveInstanceState);
        setContentView(R.layout.mjh_test);
        uiMain = this;
        button1=(Button) findViewById(R.id.button);

        final TextView txt = (TextView)findViewById(R.id.textView);

        button1.setOnClickListener(new View.OnClickListener() {
            String resultTest = "";
            @Override
            public void onClick(View v){
                try{
                    resultTest = "현재 시뮬 스텝 사이즈" + Integer.toString(test.simulatorStep.size()) + "," + Integer.toString(test.totalStep)+ ": ";
                    for(int i = 0 ; i < test.simulatorStep.get(test.totalStep-2).eachVolume.size(); i++){
                        resultTest = resultTest + "/" + Float.toString(test.simulatorStep.get(test.totalStep-2).eachVolume.get(i));
                    /*
                    resultTest = resultTest + "/" + Float.toString(test.simulatorStep.get(2).isColor.get(i).rgb_red)
                            + "," + Float.toString(test.simulatorStep.get(2).isColor.get(i).rgb_green)
                            + "," + Float.toString(test.simulatorStep.get(2).isColor.get(i).rgb_blue);

                     */
                    }
                    txt.setText(resultTest);
                }catch(Exception e){
                    Toast myToast = Toast.makeText(uiMain, e.toString(), Toast.LENGTH_LONG);
                    myToast.show();
                }
            }
        });



        cb1 = (CheckBox)findViewById(R.id.checkBox1);
        cb2 = (CheckBox)findViewById(R.id.checkBox2);

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
            try{
                stepNum++;
                adapterMIN.addItem(Integer.toString(stepNum), listUpdateTech, listUpdateStep, listUpdateIngredient, listUpdateIngredientAmount);
                listview.setAdapter(adapterMIN);
                listUpdateFlag = 0;
                if(listUpdateTech.equals("Layering")){
                    Toast myToast = Toast.makeText(this.getApplicationContext(),"레이어링 작동", Toast.LENGTH_SHORT);
                    myToast.show();
                    if(listUpdateStep.size() == 0){
                        test.addStepLayering(stepNum, 0, listUpdateIngredient.get(0), listUpdateIngredientAmount.get(0), 0);
                    }
                    else{
                        test.addStepLayering(stepNum, listUpdateStep.get(0), null, 0, 0);
                    }
                }
                else{
                    test.addStepBuildings(stepNum, listUpdateStep, listUpdateIngredient, listUpdateIngredientAmount, true);
                }
            }catch(Exception e){
                Toast myToast = Toast.makeText(uiMain, e.toString(), Toast.LENGTH_LONG);
                myToast.show();
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
            case R.id.floatingActionButtonForAddList: // 시뮬 스텝 추가 팝업으로 넘어감
                if(cb1.isChecked() == true){ // 체크 버튼 점검
                    if(isFirst == 0){
                        isFirst = 1;
                        test = new MJH_Object_simulator(0, 1); // 글래스타입 0 -> 하이볼 잔
                    }
                    Toast myToast = Toast.makeText(this.getApplicationContext(),"하이볼잔", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else if(cb2.isChecked() == true){
                    if(isFirst == 0){
                        isFirst = 1;
                        test = new MJH_Object_simulator(1, 1); // 글래스타입 0 -> 하이볼 잔
                    }
                    Toast myToast = Toast.makeText(this.getApplicationContext(),"칵테일잔", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                Intent intent = new Intent(this,MJH_Popup1Activity.class);
                startActivityForResult(intent, 1);
                adapterMIN.callByPopup = 1;
                usingStepNum.add(0);
                break;
            case R.id.floatingActionButtonForAddList2: // 시뮬스텝 지우기
                if(stepNum != 0){
                    try{
                        int deleteBuff;
                        deleteBuff = adapterMIN.listViewItemList.size();
                        stepNum--;
                        adapterMIN.listViewItemList.remove(deleteBuff-1);
                        listview.setAdapter(adapterMIN);

                        //step 하나 지우기
                        test.simulatorStep.remove(deleteBuff-1);
                        test.totalStep--; // 전체 스텝 갯수
                        test.inGlassStep--;

                        listUpdateTech =""; // 팝업1에서 선택된 기술
                        listUpdateStep = new ArrayList<Integer>();
                        listUpdateIngredient = new ArrayList<MJH_Object_ingredient>();
                        listUpdateIngredientAmount = new ArrayList<Float>();

                        for(int i = 0; i <usingStepNum.get(usingStepNum.size() - 1); i++){
                            usingStep.remove(usingStep.size() - 1);
                        }
                    }catch(Exception e){
                        Toast myToast = Toast.makeText(this.getApplicationContext(), e.toString(), Toast.LENGTH_LONG);
                        myToast.show();
                    }
                }

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
            case R.id.button_simulation_action: // 시뮬레이션 작동시키기
                if(test.simulatorStep.size() == 0) {
                    Toast myToast = Toast.makeText(this.getApplicationContext(),"칵테일 시뮬레이션 스텝을 추가해 주세요!", Toast.LENGTH_SHORT);
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
