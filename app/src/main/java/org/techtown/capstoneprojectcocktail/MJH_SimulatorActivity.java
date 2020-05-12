package org.techtown.capstoneprojectcocktail;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
    Canvas canvas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.min_cocktail_simulator_activity);

        MJH_Object_simulator test = new MJH_Object_simulator(0,0);

        Bitmap bitmap = Bitmap.createBitmap(720,1480, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        ImageView View = (ImageView) findViewById(R.id.imageView);
        View.setImageBitmap(bitmap);

        Paint paint = new Paint();
        Paint paint_gradient = new Paint();


        Button button1 = (Button) findViewById(R.id.button1_mjh);
        Button button2 = (Button) findViewById(R.id.button2_mjh);
        Button button3 = (Button) findViewById(R.id.button3_mjh);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = Bitmap.createBitmap(720,1480, Bitmap.Config.ARGB_8888);
                canvas = new Canvas(bitmap);
                ImageView View = (ImageView) findViewById(R.id.imageView);
                View.setImageBitmap(bitmap);
                rendering2(canvas);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = Bitmap.createBitmap(720,1480, Bitmap.Config.ARGB_8888);
                canvas = new Canvas(bitmap);
                ImageView View = (ImageView) findViewById(R.id.imageView);
                View.setImageBitmap(bitmap);
                rendering1(canvas);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = Bitmap.createBitmap(720,1480, Bitmap.Config.ARGB_8888);
                canvas = new Canvas(bitmap);
                ImageView View = (ImageView) findViewById(R.id.imageView);
                View.setImageBitmap(bitmap);
                rendering3(canvas);
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



    public void rendering1(Canvas canvas){
        Paint paint = new Paint();
        Paint paint_gradient = new Paint();

        //바닥부
        paint.setColor(Color.YELLOW);
        RectF rect1 = new RectF();
        rect1.set(110, 350, 650, 430);
        canvas.drawArc(rect1, 180, 180, true, paint);

        //위 사각
        paint.setColor(Color.YELLOW);
        canvas.drawRect(110, 380, 650, 1320, paint);

        //그라데이션
        paint_gradient.setShader(new LinearGradient(640, 620, 640, 1020, Color.YELLOW, Color.RED, Shader.TileMode.CLAMP));
        canvas.drawRect(110, 620, 650, 1020, paint_gradient);

        //아래 사각
        paint.setColor(Color.RED);
        canvas.drawRect(110, 1020, 650, 1320, paint);

        //바닥부
        paint.setColor(Color.RED);
        RectF rect = new RectF();
        rect.set(110, 1270, 650, 1370);
        canvas.drawArc(rect, 0, 360, true, paint);


        Bitmap bitmap2 = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.highball_glass_test_ice);
        bitmap2 = resizeBitmapImg(bitmap2, 1480);
        canvas.drawBitmap(bitmap2, 0, 0, null);

        //빛반사
        paint.setColor(0x56FFFFFF);

        canvas.drawRect(150, 75, 300, 1370, paint);

        rect.set(150, 1350, 300, 1390);
        canvas.drawArc(rect, 90, 90, true, paint);
    }

    public void rendering2(Canvas canvas){
        Paint paint = new Paint();
        Paint paint_gradient = new Paint();

        //바닥부
        paint.setColor(0xEF0099FF);
        RectF rect1 = new RectF();
        rect1.set(110, 350, 650, 430);
        canvas.drawArc(rect1, 180, 180, true, paint);

        //위 사각
        paint.setColor(0xEF0099FF);
        canvas.drawRect(110, 380, 650, 1320, paint);


        //바닥부
        paint.setColor(0xEF0099FF);
        RectF rect = new RectF();
        rect.set(110, 1270, 650, 1370);
        canvas.drawArc(rect, 0, 180, true, paint);


        Bitmap bitmap2 = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.highball_glass_test_ice);
        bitmap2 = resizeBitmapImg(bitmap2, 1480);
        canvas.drawBitmap(bitmap2, 0, 0, null);

        //빛반사
        paint.setColor(0x56FFFFFF);

        canvas.drawRect(150, 75, 300, 1370, paint);

        rect.set(150, 1350, 300, 1390);
        canvas.drawArc(rect, 90, 90, true, paint);
    }

    public void rendering3(Canvas canvas){
        Paint paint = new Paint();
        Paint paint_gradient = new Paint();

        //바닥부
        paint.setColor(0xE6FFFF00);
        RectF rect1 = new RectF();
        rect1.set(110, 350, 650, 430);
        canvas.drawArc(rect1, 180, 180, true, paint);

        //위 사각
        paint.setColor(0xE6FFFF00);
        canvas.drawRect(110, 380, 650, 620, paint);

        //그라데이션
        paint_gradient.setShader(new LinearGradient(640, 620, 640, 1020, 0xE6FFFF00, 0x96FFFFFF, Shader.TileMode.CLAMP));
        canvas.drawRect(110, 620, 650, 1020, paint_gradient);

        //아래 사각
        paint.setColor(0x96FFFFFF);
        canvas.drawRect(110, 1020, 650, 1320, paint);

        //바닥부
        paint.setColor(0x96FFFFFF);
        RectF rect = new RectF();
        rect.set(110, 1270, 650, 1370);
        canvas.drawArc(rect, 0, 360, true, paint);


        Bitmap bitmap2 = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.highball_glass_test_ice);
        bitmap2 = resizeBitmapImg(bitmap2, 1480);
        canvas.drawBitmap(bitmap2, 0, 0, null);

        //빛반사
        paint.setColor(0x56FFFFFF);

        canvas.drawRect(150, 75, 300, 1370, paint);

        rect.set(150, 1350, 300, 1390);
        canvas.drawArc(rect, 90, 90, true, paint);
    }
    //param source 원본 Bitmap 객체
    //param maxResolution 제한 해상도
    //return 리사이즈된 이미지 Bitmap 객체
    public Bitmap resizeBitmapImg(Bitmap source, int maxResolution){
        int width = source.getWidth();
        int height = source.getHeight();
        int newWidth = width;
        int newHeight = height;
        float rate = 0.0f;

        if(width > height){
            if(maxResolution < width){
                rate = maxResolution / (float) width;
                newHeight = (int) (height * rate);
                newWidth = maxResolution;
            }
        }else{
            if(maxResolution < height){
                rate = maxResolution / (float) height;
                newWidth = (int) (width * rate);
                newHeight = maxResolution;
            }
        }
        return Bitmap.createScaledBitmap(source, newWidth, newHeight, true);
    }
}