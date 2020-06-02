package org.techtown.capstoneprojectcocktail;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


public class MJH_SimulatorGraphicActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.mjh_graphic);
        try{
            float i;

            int red = (int) MJH_SimulatorUiActivity.test.simulatorStep.get(MJH_SimulatorUiActivity.test.simulatorStep.size()-1 ).isColor.get(0).rgb_red;
            int green = (int) MJH_SimulatorUiActivity.test.simulatorStep.get(MJH_SimulatorUiActivity.test.simulatorStep.size()-1 ).isColor.get(0).rgb_green;
            int blue = (int) MJH_SimulatorUiActivity.test.simulatorStep.get(MJH_SimulatorUiActivity.test.simulatorStep.size()-1 ).isColor.get(0).rgb_blue;

            i = MJH_SimulatorUiActivity.test.simulatorStep.get(MJH_SimulatorUiActivity.test.simulatorStep.size()-1 ).totalVolume;
            TextView text;
            text = (TextView) findViewById(R.id.textView) ;
            text.setText(Float.toString(red) + "/" + Float.toString(green) + "/" + Float.toString(blue));


            Canvas canvas;
            Bitmap bitmap = Bitmap.createBitmap(720,1480, Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bitmap);
            ImageView View = (ImageView) findViewById(R.id.highballGlass);
            View.setImageBitmap(bitmap);

            Paint paint = new Paint();
            Paint paint_gradient = new Paint();


            //바닥부
            paint.setColor(Color.rgb(red ,green ,blue));
            RectF rect1 = new RectF();
            rect1.set(110, 350, 650, 430);
            canvas.drawArc(rect1, 180, 180, true, paint);

            //위 사각
            paint.setColor(Color.rgb(red ,green ,blue));
            canvas.drawRect(110, 380, 650, 1320, paint);



            //바닥부
            paint.setColor(Color.rgb(red ,green ,blue));
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
        }catch (Exception e){e.printStackTrace(); }
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
