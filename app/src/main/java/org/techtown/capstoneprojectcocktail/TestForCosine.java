package org.techtown.capstoneprojectcocktail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TestForCosine extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_cosine_activity);

        Button testButton1 = findViewById(R.id.buttonForTest1_test);
        testButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("버튼 1");
            }
        });

        Button testButton2 = findViewById(R.id.buttonForTest2_test);
        testButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("버튼 2");
            }
        });

        Button testButton3 = findViewById(R.id.buttonForTest3_test);
        testButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("버튼 3");
            }
        });
    }

    public static double cosineSimilarity(ArrayList<Double> vectorA, ArrayList<Double> vectorB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        double result = 0.0;
        for (int i = 0; i < vectorA.size(); i++) {
            dotProduct += vectorA.get(i) * vectorB.get(i);
            normA += Math.pow(vectorA.get(i), 2);
            normB += Math.pow(vectorB.get(i), 2);
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
