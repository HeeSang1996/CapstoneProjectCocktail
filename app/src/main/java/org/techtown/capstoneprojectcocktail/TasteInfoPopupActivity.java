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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.taste_info_popup_activity);

        for(int i = 0; i< 81; i++)
        {
            DocumentReference docRef = db.collection("Recipe").document(String.valueOf(i+6001));
            final int finalI = i;
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Cocktail_name[finalI]= document.get("Recipe_name").toString();
                            System.out.println("값 확인용 " + Cocktail_name[finalI]);
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        }
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
        double sugarValue = intent.getDoubleExtra("sugarValue",0);
        double bitterValue = intent.getDoubleExtra("bitterValue",0);
        double sourValue = intent.getDoubleExtra("sourValue",0);
        double saltyValue = intent.getDoubleExtra("saltyValue",0);
        double spicyValue = intent.getDoubleExtra("spicyValue",0);

        double[] tempArray = new double[81];
        double max = tempArray[0]; //최대값
        double secondMax = tempArray[0]; //최대값
        double thirdMax = tempArray[0]; //최대값
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
        System.out.println("나와야 하는 값 " +Cocktail_name[maxIndex]);
        System.out.println(Cocktail_name[secondMaxIndex]);
        System.out.println(Cocktail_name[thirdMaxIndex]);

        System.out.println(maxIndex);
        System.out.println(secondMaxIndex);
        System.out.println(thirdMaxIndex);

        textFor1Cocktail.setText(Cocktail_name[maxIndex]);
        textFor2Cocktail.setText(Cocktail_name[secondMaxIndex]);
        textFor3Cocktail.setText(Cocktail_name[thirdMaxIndex]);
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
