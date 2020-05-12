package org.techtown.capstoneprojectcocktail;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MJH_SimulatorActivity extends AppCompatActivity {

    public FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.min_cocktail_simulator_activity);

        MJH_Object_simulator test = new MJH_Object_simulator(0,0);

        Button button1 = (Button) findViewById(R.id.button1_mjh);
        Button button2 = (Button) findViewById(R.id.button2_mjh);
        Button button3 = (Button) findViewById(R.id.button3_mjh);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"1번 버튼 클릭",Toast.LENGTH_LONG).show();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"2번 버튼 클릭",Toast.LENGTH_LONG).show();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"3번 버튼 클릭",Toast.LENGTH_LONG).show();
            }
        });
        //sample ingredient
        MJH_Object_color color1 = new MJH_Object_color(255, 0, 0);
        MJH_Object_color color2 = new MJH_Object_color(255, 255, 0);
        MJH_Object_color color3 = new MJH_Object_color(155, 0, 100);
        MJH_Object_color color4 = new MJH_Object_color(155, 255, 111);

        /*
        MJH_Object_ingredient[] input = new MJH_Object_ingredient[100];
        float[] input_amount = new float[2];
        input[0] = new MJH_Object_ingredient((float)1.01, 20, 20, 20, 20, 20, color1);
        input_amount[0] = 20;
        input[1] = new MJH_Object_ingredient((float)1.1, 20, 20, 20, 20, 20, color2);
        input_amount[1] = 20;

        MJH_Object_ingredient[] input2 = new MJH_Object_ingredient[100];
        float[] input_amount2 = new float[2];
        input2[0] = new MJH_Object_ingredient((float)1.01, 20, 20, 20, 20, 20, color3);
        input_amount2[0] = 20;
        input2[1] = new MJH_Object_ingredient((float)1.1, 20, 20, 20, 20, 20, color4);
        input_amount2[1] = 20;

        test.add_step_layering(1, 0, 2, input, input_amount, 3);
        test.add_step_layering(2, 0, 2, input2, input_amount2, 3);
         */

        MJH_Object_ingredient[] input = new MJH_Object_ingredient[100];
        float[] input_amount = new float[2];
        input[0] = new MJH_Object_ingredient((float)1.01, 20, 20, 20, 20, 20, color1);
        input_amount[0] = 20;

        MJH_Object_ingredient[] input2 = new MJH_Object_ingredient[100];
        float[] input_amount2 = new float[2];
        input2[0] = new MJH_Object_ingredient((float)1.01, 20, 20, 20, 20, 20, color3);
        input_amount2[0] = 20;
        input2[1] = new MJH_Object_ingredient((float)1.1, 20, 20, 20, 20, 20, color4);
        input_amount2[1] = 20;

        test.add_step_layering(1, 0, 1, input, input_amount, 3);
        test.add_step_layering(1, 0, 1, input2, input_amount2, 0);



        /*
        TextView textView1 = (TextView) findViewById(R.id.textView) ;
        textView1.setText("Color is changed.") ;
        textView1.setBackgroundColor(Color.parseColor(test.simulator_step[test.in_glass_step - 1].is_color[1].get_android_color_type()));
        System.out.println(test.total_step);
        System.out.println(test.simulator_step[test.in_glass_step - 1].total_abv);
        System.out.println(test.simulator_step[test.in_glass_step - 1].total_volume);
        System.out.println(test.simulator_step[test.in_glass_step - 1].is_gradient);
         */



    }


    public void setDocument() {
        Map<String, Object> Ingredient_info = new HashMap<>();
        Ingredient_info.put("Ingredient_name", "테스트");
        Ingredient_info.put("Ingredient_type", "베이스");
        Ingredient_info.put("abv", 0);
        Ingredient_info.put("sugar_rate", 24);
        Ingredient_info.put("salty", 0);
        Ingredient_info.put("bitter", 0);
        Ingredient_info.put("sour", 0);
        Ingredient_info.put("flavour", "개같은 맛과 향");
        Ingredient_info.put("specific_gravity", 0.135);
        Map<String, Number> Ingredient_color = new HashMap<>();

        Ingredient_color.put("Red", 210);
        Ingredient_color.put("Green", 0);
        Ingredient_color.put("Blue", 0);

        Ingredient_info.put("Ingredient_color", Ingredient_color);

        db.collection("Ingredient").document("5006")
                .set(Ingredient_info)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
        // [END set_document]

        //Map<String, Object> data = new HashMap<>();
        // [START set_with_id]
        //db.collection("Ingredient").document("5006").set(data);
        // [END set_with_id]
    }

    public void getDocument(DocumentReference docRef){
        //가져오기
        docRef = db.collection("Ingredient").document("5006");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //해당 데이터 전부 읽어오기
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        //일부만 읽어오기
                        String name = (String) document.get("Ingredient_name");
                        Log.d(TAG, "DocumentSnapshot data: " + name);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
}