package org.techtown.capstoneprojectcocktail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static org.techtown.capstoneprojectcocktail.MJH_SimulatorUiActivity.test;

public class TasteInfoPopupActivity extends Activity {

    private InputMethodManager inputKeyboardHide;
    private TextView textFor1Cocktail, textFor2Cocktail, textFor3Cocktail;
    private TextView textForTasteInfo, textForFlavorInfo;
    CocktailTasteInfo cocktailTasteInfo = new CocktailTasteInfo();
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    String[] Cocktail_name = new String[81];
    int maxIndex = 0;
    int secondMaxIndex = 0;
    int thirdMaxIndex = 0;
    double sugarValue;
    double bitterValue;
    double sourValue;
    double saltyValue;
    double spicyValue;

    double[] tempArray = new double[81];
    double max = tempArray[0]; //최대값
    double secondMax = tempArray[0]; //최대값
    double thirdMax = tempArray[0]; //최대값

    //재선언
    private ArrayList Recipe_name;

    //맛 선언
    private ArrayList Recipe_sugar;
    private ArrayList Recipe_hot;
    private ArrayList Recipe_sour;
    private ArrayList Recipe_bitter;
    private ArrayList Recipe_salty;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.taste_info_popup_activity);


        //준홍예제
        int ingredSize = test.simulatorStep.get(test.simulatorStep.size() - 1).ingredListForFlavour.size();
        for(int i = 0; i < ingredSize; i++){
            String name = test.simulatorStep.get(test.simulatorStep.size() - 1).ingredListForFlavour.get(i).name;
            String flavour = test.simulatorStep.get(test.simulatorStep.size() - 1).ingredListForFlavour.get(i).flavour;
            String type = test.simulatorStep.get(test.simulatorStep.size() - 1).ingredListForFlavour.get(i).type;
            double inputVol = test.simulatorStep.get(test.simulatorStep.size() - 1).ingredListForFlavour.get(i).volForFlavour;
        }

        //키보드 숨기기
        inputKeyboardHide = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        textFor1Cocktail = (TextView) findViewById(R.id.textView_taste_info_1);
        textFor2Cocktail = (TextView) findViewById(R.id.textView_taste_info_2);
        textFor3Cocktail = (TextView) findViewById(R.id.textView_taste_info_3);

        textForTasteInfo = (TextView) findViewById(R.id.textView_taste_taste_info);
        textForFlavorInfo = (TextView) findViewById(R.id.textView_flavor_taste_info);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        //넘겨받은 칵테일 아아디
        sugarValue = intent.getDoubleExtra("sugarValue",0);
        bitterValue = intent.getDoubleExtra("bitterValue",0);
        sourValue = intent.getDoubleExtra("sourValue",0);
        saltyValue = intent.getDoubleExtra("saltyValue",0);
        spicyValue = intent.getDoubleExtra("spicyValue",0);

        tempArray = new double[81];
        max = tempArray[0]; //최대값
        secondMax = tempArray[0]; //최대값
        thirdMax = tempArray[0]; //최대값

        //재선언 초기화
        Recipe_name = new ArrayList();

        //맛 선언 초기화
        Recipe_sugar = new ArrayList();
        Recipe_hot = new ArrayList();
        Recipe_sour = new ArrayList();
        Recipe_bitter = new ArrayList();
        Recipe_salty = new ArrayList();


        db.collection("Recipe")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Recipe_name.add(document.get("Recipe_name").toString());       //레시피 이름
                                Recipe_sugar.add(document.get("단맛").toString());             //레시피의 단맛
                                Recipe_hot.add(document.get("매운맛").toString());             //레시피의 매운맛
                                Recipe_sour.add(document.get("신맛").toString());              //레시피의 신맛
                                Recipe_bitter.add(document.get("쓴맛").toString());            //레시피의 쓴맛
                                Recipe_salty.add(document.get("짠맛").toString());             //레시피의 짠맛
                            }
                            //여기에 밑에 함수 넣듯이 넣어서 써먹으면 무조건 다 들어간 이후에 작동하는거라 null값 안들어가요(예시 SetDocument)
                            SetDocument();
                        } else {
                            System.out.println("오류 발생 Grading 컬렉션에서 정상적으로 불러와지지 않음.");
                        }
                    }
                });

    }

    public void SetDocument(){
        //단맛, 쓴맛, 신맛,짠맛,매운맛
        double recipe_sugar_sum = 0.0;
        double recipe_bitter_sum = 0.0;
        double recipe_sour_sum = 0.0;
        double recipe_salty_sum = 0.0;
        double recipe_hot_sum = 0.0;

        String recipe_sugar_sum_string;
        String recipe_bitter_sum_string;
        String recipe_sour_sum_string;
        String recipe_salty_sum_string;
        String recipe_hot_sum_string;

        for (int i =0;i<81;i++){
            //단맛, 쓴맛, 신맛,짠맛,매운맛
            double[] tasteInfo = {sugarValue,bitterValue,sourValue,saltyValue,spicyValue};
            tempArray[i]=cosineSimilarity(cocktailTasteInfo.cocktailInfo[i],tasteInfo);
            //영진 여기 로그로 바꾸든지 알아서
        }
        for(int i=0;i<tempArray.length;i++) {
            if(max<tempArray[i]) {
                max = tempArray[i];
                maxIndex = i;
            }
        }
        //최대값 0.0으로 값 줄임
        tempArray[maxIndex]=0.0;
        for(int i=0;i<tempArray.length;i++) {
            if(secondMax<tempArray[i]) {
                secondMax = tempArray[i];
                secondMaxIndex = i;
            }
        }
        //두번째 최대값 0.0으로 값 줄임
        tempArray[secondMaxIndex]=0.0;
        for(int i=0;i<tempArray.length;i++) {
            if(thirdMax<tempArray[i]) {
                thirdMax = tempArray[i];
                thirdMaxIndex = i;
            }
        }
        //유사도가 떨어지는 칵테일은 출력하지 않음
        //단맛, 쓴맛, 신맛,짠맛,매운맛
        if (max<0.92){
            textFor1Cocktail.setText("1순위 : 유사한 칵테일 없음");
            textFor2Cocktail.setText("2순위 : 유사한 칵테일 없음");
            textFor3Cocktail.setText("3순위 : 유사한 칵테일 없음");
        }
        else if(secondMax <0.92){
            textFor1Cocktail.setText("1순위 : " + Recipe_name.get(maxIndex).toString());
            textFor2Cocktail.setText("2순위 : 유사한 칵테일 없음");
            textFor3Cocktail.setText("3순위 : 유사한 칵테일 없음");

            recipe_sugar_sum = Double.parseDouble(Recipe_sugar.get(maxIndex).toString()) *1;
            recipe_bitter_sum = Double.parseDouble(Recipe_bitter.get(maxIndex).toString()) *1;
            recipe_sour_sum = Double.parseDouble(Recipe_sour.get(maxIndex).toString()) *1;
            recipe_salty_sum = Double.parseDouble(Recipe_salty.get(maxIndex).toString()) *1;
            recipe_hot_sum = Double.parseDouble(Recipe_hot.get(maxIndex).toString()) *1;
        }
        else if (thirdMax <0.92){
            textFor1Cocktail.setText("1순위 : " + Recipe_name.get(maxIndex).toString());
            textFor2Cocktail.setText("2순위 : " + Recipe_name.get(secondMaxIndex).toString());
            textFor3Cocktail.setText("3순위 : 유사한 칵테일 없음");

            recipe_sugar_sum = Double.parseDouble(Recipe_sugar.get(maxIndex).toString()) *1+Double.parseDouble(Recipe_sugar.get(secondMaxIndex).toString())*0.5;
            recipe_bitter_sum = Double.parseDouble(Recipe_bitter.get(maxIndex).toString()) *1+Double.parseDouble(Recipe_bitter.get(secondMaxIndex).toString())*0.5;
            recipe_sour_sum = Double.parseDouble(Recipe_sour.get(maxIndex).toString()) *1 + Double.parseDouble(Recipe_sour.get(secondMaxIndex).toString())*0.5;
            recipe_salty_sum = Double.parseDouble(Recipe_salty.get(maxIndex).toString()) *1 +Double.parseDouble(Recipe_salty.get(secondMaxIndex).toString())*0.5;
            recipe_hot_sum = Double.parseDouble(Recipe_hot.get(maxIndex).toString()) *1 + Double.parseDouble(Recipe_hot.get(secondMaxIndex).toString())*0.5;
        }
        else{
            textFor1Cocktail.setText("1순위 : " + Recipe_name.get(maxIndex).toString());
            textFor2Cocktail.setText("2순위 : " + Recipe_name.get(secondMaxIndex).toString());
            textFor3Cocktail.setText("3순위 : " + Recipe_name.get(thirdMaxIndex).toString());

            recipe_sugar_sum = Double.parseDouble(Recipe_sugar.get(maxIndex).toString()) *1+Double.parseDouble(Recipe_sugar.get(secondMaxIndex).toString())*0.5+Double.parseDouble(Recipe_sugar.get(thirdMaxIndex).toString())*0.3;
            recipe_bitter_sum = Double.parseDouble(Recipe_bitter.get(maxIndex).toString()) *1+Double.parseDouble(Recipe_bitter.get(secondMaxIndex).toString())*0.5+Double.parseDouble(Recipe_bitter.get(thirdMaxIndex).toString())*0.3;
            recipe_sour_sum = Double.parseDouble(Recipe_sour.get(maxIndex).toString()) *1 + Double.parseDouble(Recipe_sour.get(secondMaxIndex).toString())*0.5+ Double.parseDouble(Recipe_sour.get(thirdMaxIndex).toString())*0.3;
            recipe_salty_sum = Double.parseDouble(Recipe_salty.get(maxIndex).toString()) *1 +Double.parseDouble(Recipe_salty.get(secondMaxIndex).toString())*0.5+Double.parseDouble(Recipe_salty.get(thirdMaxIndex).toString())*0.3;
            recipe_hot_sum = Double.parseDouble(Recipe_hot.get(maxIndex).toString()) *1 + Double.parseDouble(Recipe_hot.get(secondMaxIndex).toString())*0.5+ Double.parseDouble(Recipe_hot.get(thirdMaxIndex).toString())*0.3;;
        }

        recipe_sugar_sum_string = Integer.toString((int)recipe_sugar_sum);
        recipe_bitter_sum_string = Integer.toString((int)recipe_bitter_sum);
        recipe_sour_sum_string = Integer.toString((int)recipe_sour_sum);
        recipe_salty_sum_string=Integer.toString((int)recipe_salty_sum);
        recipe_hot_sum_string = Integer.toString((int)recipe_hot_sum);
        textForTasteInfo.setText("단맛 : "+recipe_sugar_sum_string+
                "\n쓴맛 : "+recipe_bitter_sum_string+
                "\n신맛 : "+recipe_sour_sum_string+
                "\n짠맛 : "+recipe_salty_sum_string+
                "\n매운맛 : "+recipe_hot_sum_string);
}

    public void tastePopupClose(View v){
        finish();
    }

    public void tasteInfoPopupCancel(View v){
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

    public static double cosineSimilarity(double[] vectorA, double[] vectorB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        double result = 0.0;
        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            normA += Math.pow(vectorA[i], 2);
            normB += Math.pow(vectorB[i], 2);
        }
        result = dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
        if(Double.isNaN(result)) {
            return 0.0;
        }
        else {
            return result;
        }
    }
}
