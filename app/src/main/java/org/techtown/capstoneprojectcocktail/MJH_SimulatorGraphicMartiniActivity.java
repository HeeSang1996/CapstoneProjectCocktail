package org.techtown.capstoneprojectcocktail;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MJH_SimulatorGraphicMartiniActivity extends AppCompatActivity {
    int setNumber = 0;
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.mjh_graphic_martini);

        try{
            //그라데이션
            if(MJH_SimulatorUiActivity.test.isGradient == 1){
                setNumber++;
                Toast myToast = Toast.makeText(this.getApplicationContext(),Integer.toString(setNumber), Toast.LENGTH_SHORT);
                myToast.show();

                Canvas canvas;
                Bitmap bitmap = Bitmap.createBitmap(720,1480, Bitmap.Config.ARGB_8888);
                canvas = new Canvas(bitmap);

                float volume;
                int s_red, s_green, s_blue;
                int red, green, blue;

                int s_height = 1320;
                int height = 1320;
                int prev_h = 1320;


                Paint paint = new Paint();

                Paint paint_gradient = new Paint();
                paint_gradient.setAntiAlias(true);

                RectF rect = new RectF();

                int setIndex = MJH_SimulatorUiActivity.test.simulatorStep.get(MJH_SimulatorUiActivity.test.simulatorStep.size()-1 ).isLayering;

                //시럽
                s_red = (int) MJH_SimulatorUiActivity.test.simulatorStep.get(MJH_SimulatorUiActivity.test.simulatorStep.size()-1 ).isColor.get(1).rgb_red;
                s_green = (int) MJH_SimulatorUiActivity.test.simulatorStep.get(MJH_SimulatorUiActivity.test.simulatorStep.size()-1 ).isColor.get(1).rgb_green;
                s_blue = (int) MJH_SimulatorUiActivity.test.simulatorStep.get(MJH_SimulatorUiActivity.test.simulatorStep.size()-1 ).isColor.get(1).rgb_blue;

                volume = MJH_SimulatorUiActivity.test.simulatorStep.get(MJH_SimulatorUiActivity.test.inGlassStep-1 ).eachVolume.get(1);

                ///바닥부
                paint.setColor(Color.rgb(s_red ,s_green ,s_blue));
                rect.set(110, 1270, 650, 1370);
                canvas.drawArc(rect, 0, 360, true, paint);


                // 한층 위
                red = (int) MJH_SimulatorUiActivity.test.simulatorStep.get(MJH_SimulatorUiActivity.test.simulatorStep.size()-1 ).isColor.get(0).rgb_red;
                green = (int) MJH_SimulatorUiActivity.test.simulatorStep.get(MJH_SimulatorUiActivity.test.simulatorStep.size()-1 ).isColor.get(0).rgb_green;
                blue = (int) MJH_SimulatorUiActivity.test.simulatorStep.get(MJH_SimulatorUiActivity.test.simulatorStep.size()-1 ).isColor.get(0).rgb_blue;

                ///그라데이션 높이
                s_height = (int)((float)height - ((float)4.0 * volume));
                height = s_height;
                prev_h = height;

                volume = MJH_SimulatorUiActivity.test.simulatorStep.get(MJH_SimulatorUiActivity.test.inGlassStep-1 ).eachVolume.get(0);

                ///전체사각
                height = (int)((float)height - ((float)4.0 * volume));
                paint.setColor(Color.rgb(red ,green ,blue));
                canvas.drawRect(110, height, 650, prev_h, paint);
                prev_h = height;

                ///그라데이션 출력
                paint_gradient.setShader(new LinearGradient(0, s_height - (int)(((float)4.0 * volume)/2), 0, 1320, Color.rgb(red ,green ,blue), Color.rgb(s_red ,s_green ,s_blue), Shader.TileMode.CLAMP));
                canvas.drawRect(110, s_height - (int)(((float)4.0 * volume)/2), 650, 1320, paint_gradient);






                if(MJH_SimulatorUiActivity.test.simulatorStep.get(MJH_SimulatorUiActivity.test.simulatorStep.size()-1 ).isLayering > 2){
                    for(int index = 2; index < MJH_SimulatorUiActivity.test.simulatorStep.get(MJH_SimulatorUiActivity.test.simulatorStep.size()-1 ).isLayering; index++){
                        red = (int) MJH_SimulatorUiActivity.test.simulatorStep.get(MJH_SimulatorUiActivity.test.simulatorStep.size()-1 ).isColor.get(index).rgb_red;
                        green = (int) MJH_SimulatorUiActivity.test.simulatorStep.get(MJH_SimulatorUiActivity.test.simulatorStep.size()-1 ).isColor.get(index).rgb_green;
                        blue = (int) MJH_SimulatorUiActivity.test.simulatorStep.get(MJH_SimulatorUiActivity.test.simulatorStep.size()-1 ).isColor.get(index).rgb_blue;

                        volume = MJH_SimulatorUiActivity.test.simulatorStep.get(MJH_SimulatorUiActivity.test.inGlassStep-1 ).eachVolume.get(index);

                        //전체사각
                        height = (int)((float)height - ((float)4.0 * volume));
                        paint.setColor(Color.rgb(red ,green ,blue));
                        canvas.drawRect(110, height, 650, prev_h, paint);
                        prev_h = height;
                    }
                }


                //얼음
                Bitmap bitmap2 = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.martini_glass);
                bitmap2 = resizeBitmapImg(bitmap2);
                canvas.drawBitmap(bitmap2, 0, 0, null);

                //빛반사
                paint.setColor(0x56FFFFFF);
                canvas.drawRect(150, 75, 300, 1370, paint);
                rect.set(150, 1350, 300, 1390);
                canvas.drawArc(rect, 90, 90, true, paint);
            }

            else if( MJH_SimulatorUiActivity.test.simulatorStep.get(MJH_SimulatorUiActivity.test.simulatorStep.size()-1 ).isLayering > 1){
                setNumber++;
                Toast myToast = Toast.makeText(this.getApplicationContext(),Integer.toString(setNumber), Toast.LENGTH_SHORT);
                myToast.show();

                Canvas canvas;
                Bitmap bitmap = Bitmap.createBitmap(720,1480, Bitmap.Config.ARGB_8888);
                canvas = new Canvas(bitmap);
                ImageView View = (ImageView) findViewById(R.id.martiniGlass);
                View.setImageBitmap(bitmap);

                float volume;
                int red, green, blue;
                int height = 1320;
                int prev_h = 1320;


                Paint paint = new Paint();
                Paint paint_gradient = new Paint();
                RectF rect = new RectF();

                for(int index = 0; index < MJH_SimulatorUiActivity.test.simulatorStep.get(MJH_SimulatorUiActivity.test.simulatorStep.size()-1 ).isLayering; index++){
                    red = (int) MJH_SimulatorUiActivity.test.simulatorStep.get(MJH_SimulatorUiActivity.test.simulatorStep.size()-1 ).isColor.get(index).rgb_red;
                    green = (int) MJH_SimulatorUiActivity.test.simulatorStep.get(MJH_SimulatorUiActivity.test.simulatorStep.size()-1 ).isColor.get(index).rgb_green;
                    blue = (int) MJH_SimulatorUiActivity.test.simulatorStep.get(MJH_SimulatorUiActivity.test.simulatorStep.size()-1 ).isColor.get(index).rgb_blue;

                    volume = MJH_SimulatorUiActivity.test.simulatorStep.get(MJH_SimulatorUiActivity.test.inGlassStep-1 ).eachVolume.get(index);

                    if(index == 0){
                        //바닥부
                        paint.setColor(Color.rgb(red ,green ,blue));
                        rect.set(110, 1270, 650, 1370);
                        canvas.drawArc(rect, 0, 360, true, paint);
                    }

                    //전체사각
                    height = (int)((float)height - ((float)4.0 * volume));
                    paint.setColor(Color.rgb(red ,green ,blue));
                    canvas.drawRect(110, height, 650, prev_h, paint);
                    prev_h = height;
                }


                //얼음
                Bitmap bitmap2 = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.martini_glass);
                bitmap2 = resizeBitmapImg(bitmap2);
                canvas.drawBitmap(bitmap2, 0, 0, null);

                //빛반사
                paint.setColor(0x56FFFFFF);
                canvas.drawRect(150, 75, 300, 1370, paint);
                rect.set(150, 1350, 300, 1390);
                canvas.drawArc(rect, 90, 90, true, paint);
            }
            else{
                Canvas canvas;
                Bitmap bitmap = Bitmap.createBitmap(500,700, Bitmap.Config.ARGB_8888);
                canvas = new Canvas(bitmap);
                ImageView View = (ImageView) findViewById(R.id.martiniGlass);
                View.setImageBitmap(bitmap);

                float volume;
                int height = 0;

                int red = (int) MJH_SimulatorUiActivity.test.simulatorStep.get(MJH_SimulatorUiActivity.test.simulatorStep.size()-1 ).isColor.get(0).rgb_red;
                int green = (int) MJH_SimulatorUiActivity.test.simulatorStep.get(MJH_SimulatorUiActivity.test.simulatorStep.size()-1 ).isColor.get(0).rgb_green;
                int blue = (int) MJH_SimulatorUiActivity.test.simulatorStep.get(MJH_SimulatorUiActivity.test.simulatorStep.size()-1 ).isColor.get(0).rgb_blue;

                volume = MJH_SimulatorUiActivity.test.simulatorStep.get(MJH_SimulatorUiActivity.test.inGlassStep-1 ).totalVolume;

                Paint paint = new Paint();
                Paint paint_gradient = new Paint();

                height = (int)((float)1320 - ((float)4.0 * volume));

                //전체사각
                paint.setColor(Color.rgb(red ,green ,blue));
                canvas.drawRect(110,0, 650, 285, paint);

                paint.setColor(Color.BLUE);
                Path path = new Path();

                private final float Width = 350;     // 길이 기준

                private float Height;                // 전체 길이

                private float centerX , centerY;     // 기준점

                private float WidthTriangle = 1.5F;  // 삼각형의 가중치가 1.5

                private float WidthRect = 2;         // 사각형의 가중치가 2 (2 : 1) 비율

                private float HeightTriangle;        // Triangle 높이



                출처: https://dudwk04.tistory.com/entry/Android-삼각형-사각형-타원-선긋기 [로리롱's]
                path.moveTo(centerX + Width / 2, centerY);
                path.lineTo(centerX + Width, centerY + HeightTriangle);
                path.lineTo(centerX, centerY + HeightTriangle);
                path.lineTo(centerX + Width / 2, centerY);
                path.close();
                canvas.drawPath(path, paint);


                //바닥부
                paint.setColor(Color.rgb(red ,green ,blue));
                RectF rect = new RectF();
                rect.set(110, 1270, 650, 1370);
                canvas.drawArc(rect, 0, 360, true, paint);

                //잔
                Bitmap bitmap2 = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.martini_glass_re);
                bitmap2 = resizeBitmapImg(bitmap2);
                canvas.drawBitmap(bitmap2, 0, 0, null);

                //빛반사
                paint.setColor(0x56FFFFFF);
                canvas.drawRect(150, 75, 300, 1370, paint);
                rect.set(150, 1350, 300, 1390);
                canvas.drawArc(rect, 90, 90, true, paint);
            }

        }catch (Exception e){e.printStackTrace(); }
    }


    //param source 원본 Bitmap 객체
    //param maxResolution 제한 해상도
    //return 리사이즈된 이미지 Bitmap 객체
    public Bitmap resizeBitmapImg(Bitmap source){
        int width = source.getWidth();
        int height = source.getHeight();
        int newWidth = 500;
        int newHeight = 700;

        return Bitmap.createScaledBitmap(source, newWidth, newHeight, true);
    }
}
