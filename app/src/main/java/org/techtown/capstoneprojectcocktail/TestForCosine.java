package org.techtown.capstoneprojectcocktail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TestForCosine extends AppCompatActivity {

    CocktailTasteInfo cocktailTasteInfo = new CocktailTasteInfo();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_cosine_activity);


        Button testButton1 = findViewById(R.id.buttonForTest1_test);
        testButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("버튼 1");
                double[] tempArray = new double[81];
                double max = tempArray[0]; //최대값
                double secondMax = tempArray[0]; //최대값
                double thirdMax = tempArray[0]; //최대값
                int maxIndex = 0;
                int secondMaxIndex = 0;
                int thirdMaxIndex = 0;
                for (int i =0;i<81;i++){
                    //골든드림
                    //단맛, 쓴맛, 신맛,짠맛,매운맛
                    double[] test1 = {300.0,40.0,60.0,0.0,0.0};
                    tempArray[i]=cosineSimilarity(cocktailTasteInfo.cocktailInfo[i],test1);
                    //영진 여기 로그로 바꾸든지 알아서
                    System.out.println(tempArray[i]);
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
                System.out.println("1위 유사도");
                System.out.println(max);
                System.out.println("1위 칵테일 인덱스");
                System.out.println(maxIndex+1);
                System.out.println("2위 유사도");
                System.out.println(secondMax);
                System.out.println("2위 칵테일 인덱스");
                System.out.println(secondMaxIndex+1);
                System.out.println("3위 유사도");
                System.out.println(thirdMax);
                System.out.println("3위 칵테일 인덱스");
                System.out.println(thirdMaxIndex+1);
                Toast.makeText(getApplicationContext(),"1위 칵테일 인덱스: "+(maxIndex+1) + "\n2위 칵테일 인덱스: "+(secondMaxIndex+1) + "\n3위 칵테일 인덱스: "+(thirdMaxIndex+1),Toast.LENGTH_LONG).show();
            }
        });

        Button testButton2 = findViewById(R.id.buttonForTest2_test);
        testButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("버튼 2");
                double[] tempArray = new double[81];
                double max = tempArray[0]; //최대값
                double secondMax = tempArray[0]; //최대값
                double thirdMax = tempArray[0]; //최대값
                int maxIndex = 0;
                int secondMaxIndex = 0;
                int thirdMaxIndex = 0;
                for (int i =0;i<81;i++){
                    //롱아일랜드 아이스티
                    //단맛, 쓴맛, 신맛,짠맛,매운맛
                    double[] test2 = {405.0,195.0,255.0,0.0,285.0};
                    tempArray[i]=cosineSimilarity(cocktailTasteInfo.cocktailInfo[i],test2);
                    //영진 여기 로그로 바꾸든지 알아서
                    System.out.println(tempArray[i]);
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
                System.out.println("1위 유사도");
                System.out.println(max);
                System.out.println("1위 칵테일 인덱스");
                System.out.println(maxIndex+1);
                System.out.println("2위 유사도");
                System.out.println(secondMax);
                System.out.println("2위 칵테일 인덱스");
                System.out.println(secondMaxIndex+1);
                System.out.println("3위 유사도");
                System.out.println(thirdMax);
                System.out.println("3위 칵테일 인덱스");
                System.out.println(thirdMaxIndex+1);
                Toast.makeText(getApplicationContext(),"1위 칵테일 인덱스: "+(maxIndex+1) + "\n2위 칵테일 인덱스: "+(secondMaxIndex+1) + "\n3위 칵테일 인덱스: "+(thirdMaxIndex+1),Toast.LENGTH_LONG).show();
            }
        });

        Button testButton3 = findViewById(R.id.buttonForTest3_test);
        testButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("버튼 3");
                double[] tempArray = new double[81];
                double max = tempArray[0]; //최대값
                double secondMax = tempArray[0]; //최대값
                double thirdMax = tempArray[0]; //최대값
                int maxIndex = 0;
                int secondMaxIndex = 0;
                int thirdMaxIndex = 0;
                for (int i =0;i<81;i++){
                    //다이키리
                    //단맛, 쓴맛, 신맛,짠맛,매운맛
                    double[] test3 = {120.0,80.0,200.0,0.0,60.0};
                    tempArray[i]=cosineSimilarity(cocktailTasteInfo.cocktailInfo[i],test3);
                    //영진 여기 로그로 바꾸든지 알아서
                    System.out.println(tempArray[i]);
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
                System.out.println("1위 유사도");
                System.out.println(max);
                System.out.println("1위 칵테일 인덱스");
                System.out.println(maxIndex+1);
                System.out.println("2위 유사도");
                System.out.println(secondMax);
                System.out.println("2위 칵테일 인덱스");
                System.out.println(secondMaxIndex+1);
                System.out.println("3위 유사도");
                System.out.println(thirdMax);
                System.out.println("3위 칵테일 인덱스");
                System.out.println(thirdMaxIndex+1);
                Toast.makeText(getApplicationContext(),"1위 칵테일 인덱스: "+(maxIndex+1) + "\n2위 칵테일 인덱스: "+(secondMaxIndex+1) + "\n3위 칵테일 인덱스: "+(thirdMaxIndex+1),Toast.LENGTH_LONG).show();
            }
        });
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

    //어레이 리스트 버전
//    public static double cosineSimilarity(ArrayList<Double> vectorA, ArrayList<Double> vectorB) {
//        double dotProduct = 0.0;
//        double normA = 0.0;
//        double normB = 0.0;
//        double result = 0.0;
//        for (int i = 0; i < vectorA.size(); i++) {
//            dotProduct += vectorA.get(i) * vectorB.get(i);
//            normA += Math.pow(vectorA.get(i), 2);
//            normB += Math.pow(vectorB.get(i), 2);
//        }
//        result = dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
//        if(Double.isNaN(result)) {
//            return 0.0;
//        }
//        else {
//            return result;
//        }
//    }
}
