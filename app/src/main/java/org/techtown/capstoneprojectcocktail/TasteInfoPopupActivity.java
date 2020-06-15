package org.techtown.capstoneprojectcocktail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class TasteInfoPopupActivity extends Activity {

    private InputMethodManager inputKeyboardHide;
    private TextView textFor1Cocktail, textFor2Cocktail, textFor3Cocktail;
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

        //키보드 숨기기
        inputKeyboardHide = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        textFor1Cocktail = (TextView) findViewById(R.id.textView_taste_info_1);
        textFor2Cocktail = (TextView) findViewById(R.id.textView_taste_info_2);
        textFor3Cocktail = (TextView) findViewById(R.id.textView_taste_info_3);

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
        textFor1Cocktail.setText(Recipe_name.get(maxIndex).toString());
        textFor2Cocktail.setText(Recipe_name.get(secondMaxIndex).toString());
        textFor3Cocktail.setText(Recipe_name.get(thirdMaxIndex).toString());
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
