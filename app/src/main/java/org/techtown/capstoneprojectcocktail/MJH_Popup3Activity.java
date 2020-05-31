package org.techtown.capstoneprojectcocktail;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.techtown.capstoneprojectcocktail.CocktailAdapterForSearch.useByMinFlag;

public class MJH_Popup3Activity extends Activity {

    final CocktailAdapterForSearch adapterForCocktailSearch = new CocktailAdapterForSearch();

    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "DocSnippets";

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
    Number[] Ingredient_sugar = new Number[127];
    String[] Ingredient_Realsugar = new String[127];
    String[] Ingredient_flavour = new String[127];
    String[] Ingredient_specific_gravity = new String[127];
    int Ingredient_count;

    RecyclerView recyclerViewForCocktailSearch;
    ArrayList<MJH_Object_ingredient> bufferUpdateIngredient;

    /////////////////////////////////////////////
    MJH_SimulatorUiActivity simulatorUiAddress;
    public static Context uiThis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiThis = this;
        useByMinFlag = 1;

        bufferUpdateIngredient = new ArrayList<MJH_Object_ingredient>() ;

        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mjh_popup3);
        simulatorUiAddress = ((MJH_SimulatorUiActivity)MJH_SimulatorUiActivity.uiMain);


        ListView listview ;
        MJH_ListviewAdapter adapter;

        // Adapter 생성
        adapter = simulatorUiAddress.adapter;
        adapter.callByPopup = 1;

        ////////////////////////////
        setAdapterForIngredientSearch();


        final EditText textForSearch = (EditText) findViewById(R.id.editText_search);

        textForSearch.setImeOptions(EditorInfo.IME_ACTION_DONE);
        textForSearch.setText("");


        recyclerViewForCocktailSearch = findViewById(R.id.recyclerViewForCocktail_search);
        LinearLayoutManager layoutManagerForCocktailSearch = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerViewForCocktailSearch.setLayoutManager(layoutManagerForCocktailSearch);




        adapterForCocktailSearch.setOnItemClickListener(new OnCocktailItemClickListenerForSearch() {
            @Override
            public void onItemClick(CocktailAdapterForSearch.ViewHolder holder, View view, int position) {
                Cocktail item = adapterForCocktailSearch.getItem(position);
                //Toast.makeText(getActivity().getApplicationContext(),"선택된 칵테일: " + item.getName(),Toast.LENGTH_LONG).show();
                for(int i = 0; i < 127; i++){
                    if(simulatorUiAddress.ingredientList[i].name.equals(item.getName())){
                        Toast.makeText(uiThis,"선택된 재료: " + item.getName(),Toast.LENGTH_LONG).show();
                        bufferUpdateIngredient.add(simulatorUiAddress.ingredientList[i]);
                    }
                }
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    //확인 버튼 클릭
    public void mBefore(View v){
        //데이터 전달하기

        finish();
    }
    public void mNext(View v){
        //데이터 전달하기

        simulatorUiAddress.listUpdateFlag = 1;
        simulatorUiAddress.listUpdateIngredient = bufferUpdateIngredient;
        useByMinFlag = 0;
        finish();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK){
                String result = data.getStringExtra("result");
                if (result == "-1"){
                    Toast.makeText(this,"call", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }


    private void setAdapterForIngredientSearch(){
        for(int i=0; i < 127; i++)
        {
            List<String> list;

            DocumentReference docRef = db.collection("Ingredient").document(String.valueOf(i+5001));

            final int finalI = i;
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            //System.out.println("DocumentSnapshot data: " + document.getData());
                            //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            count = finalI;
                            Ingredient_name[count] = (String) document.get("Ingredient_name"); //재료 이름
                            //Log.d(TAG, "count"+ count + "Ingredient_name data: " +Ingredient_name[count]);
                            System.out.println("array_count : "+ count + "   Ingredient_name data: " +Ingredient_name[count]);
                            Ingredient_ID[count] = 5001+ count;
                            Ingredient_flavour[count] = (String) document.get("flavour"); //향(칵테일에선 설명 method)
                            //abv[0] = (String) document.get("abv");

                            //Recipe_Ingredient = (Map<String, Number>) document.get("Ingredient_content");
                            //Recipe_Base[count] = (String) document.get("Recipe_Base");
                            //map으로 받아온 정보를 string으로 치환한뒤 유저에게 보여줄 수 있도록 replaceall함({, }, = 삭제 ml 추가)
                            //형변환 올바른 예시 왠진 모르지만 (float)로 명시적 형변환 하면 터지고 저리 하면 안터짐
//                            Number abc;
//                            float abcd;
//                            abc  = (Number) document.get("sugar_rate");
//                            abcd = abc.floatValue();

                            Ingredient_sugar[count] = (Number) document.get("sugar_rate"); //suger_rate(칵테일에선 재료와 용량)
                            Ingredient_Realsugar[count] = Ingredient_sugar[count] + "%";
//                            Recipe_Base[count] = Recipe_Base[count].replaceAll("\\,", "ml ");
//                            Recipe_Base[count] = Recipe_Base[count].replaceAll("\\{", " ");
//                            Recipe_Base[count] = Recipe_Base[count].replaceAll("\\}", "ml ");
//                            Recipe_Base[count] = Recipe_Base[count].replaceAll("\\=", " ");
                            //long형태로 받은 abv를 유저에게 보여줄 수 있도록 %를 붙여 재저장
                            Ingredient_Realabv[count] = (long) document.get("abv");
                            Ingredient_abv[count] = Ingredient_Realabv[count] + "%";
                            Ingredient_ref[count] = (String) document.get("ref");
                            adapterForCocktailSearch.addItem(new Cocktail(Ingredient_name[count], Ingredient_ID[count], Ingredient_flavour[count], Ingredient_Realsugar[count], Ingredient_abv[count],Ingredient_ref[count]));
                            Log.d(TAG, "Ingredient_ID data: " +  Ingredient_ID[count]);
                            //refresh 해주는 함수(아마)
                            recyclerViewForCocktailSearch.setAdapter(adapterForCocktailSearch);

                            //배열에 잘드갔는지 테스트한 for 구문
                            for(int k=0; k < 127; k++)
                            {
                                System.out.println("array_count : "+ k + "   Ingredient_name data: " +Ingredient_name[k]);
                            }
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
