package org.techtown.capstoneprojectcocktail;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MJH_SimulatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.min_cocktail_simulator_activity);

        // 그라데이션 커스텀뷰
        //CustomView myView = new CustomView(this);
        //setContentView(myView);


        //FrameLayout fl = (FrameLayout)findViewById(R.id.frameLayout1);
        //MyView m = new MyView(fl.getContext());
       // setContentView(m);

        //시뮬 start
        //Object_simulator s1 = new Object_simulator("하이볼", 1); // 하이볼잔, 얼음은 각 얼음 선택

        //시뮬 step1


        /*
        System.out.println(c1.rgb_red);
        System.out.println(c1.rgb_green);
        System.out.println(c1.rgb_blue);

        System.out.println(c1.cmyk_black_key);
        System.out.println(c1.cmyk_cyan);
        System.out.println(c1.cmyk_magenta);
        System.out.println(c1.cmyk_yellow);

        TextView textView1 = (TextView) findViewById(R.id.textView) ;
        textView1.setText("Text is changed.") ;
        textView1.setTextColor(Color.parseColor(c1.get_android_color_type()));
        System.out.println("안드컬러값" + c1.get_android_color_type());
        */

        MJH_Object_color c1 = new MJH_Object_color(255, 204, 51);
        MJH_Object_color c2 = new MJH_Object_color(102, 102, 102);
        MJH_Object_color result;

/*
        result = add_color(c1, c2, 50, 50);

        System.out.println(result.get_android_color_type());



        TextView textView1 = (TextView) findViewById(R.id.textView) ;
        textView1.setText("Color is changed.") ;
        textView1.setBackgroundColor(Color.parseColor(result.get_android_color_type()));
        */

        int[] a = new int[5];

        float[] amount = new float[2];
        amount[0] = 30;
        amount[1] = 30;


        MJH_Object_ingredient[] input = new MJH_Object_ingredient[2];
        input[0] = new MJH_Object_ingredient((float)0.9, (float)0.3, 5, 1, 1, 1, c1);
        input[1] = new MJH_Object_ingredient((float)0.8, (float)0.4, 5, 1, 1, 1, c1);
        MJH_Object_simulator test = new MJH_Object_simulator(0, 0);
        test.add_step_buildings(1, 0, a, 2, input,  amount);

        System.out.println(test.simulator_step[0].is_color[0].get_android_color_type());
        System.out.println(test.simulator_step[0].total_volume);
        System.out.println(test.simulator_step[0].total_abv);
        System.out.println(test.simulator_step[0].specific_gravity[0]);


        MJH_Object_ingredient[] input2 = new MJH_Object_ingredient[1];
        input2[0] = new MJH_Object_ingredient((float)0.8, (float)0.4, 5, 1, 1, 1, c2);
        float[] amount2 = new float[1];
        amount2[0] = 50;

        System.out.println("---------step2-------");
        test.add_step_buildings(2, 1, a, 1, input2,  amount2);
        System.out.println(test.simulator_step[1].is_color[0].get_android_color_type());
        System.out.println(test.simulator_step[1].total_volume);
        System.out.println(test.simulator_step[1].total_abv);
        System.out.println(test.simulator_step[1].specific_gravity[0]);


        MJH_Object_ingredient[] input3 = new MJH_Object_ingredient[2];
        input3[0] = new MJH_Object_ingredient((float)0.8, (float)0.4, 5, 1, 1, 1, c1);
        float[] amount3 = new float[2];
        amount3[0] = 50;

        int[] b = new int[5];

        b[0] = 0;
        b[1] = 1;

        System.out.println("---------step3-------");
        test.add_step_buildings(3, 2, b, 1, input3,  amount3);
        System.out.println(test.simulator_step[2].is_color[0].get_android_color_type());
        System.out.println(test.simulator_step[2].total_volume);
        System.out.println(test.simulator_step[2].total_abv);
        System.out.println(test.simulator_step[2].specific_gravity[0]);

        TextView textView1 = (TextView) findViewById(R.id.textView) ;
        textView1.setText("Color is changed.") ;
        textView1.setBackgroundColor(Color.parseColor(test.simulator_step[1].is_color[0].get_android_color_type()));
    }


    /*
    public Object_color add_color(Object_color color_one, Object_color color_two, float vol_one, float vol_two){
        float result_red;
        float result_blue;
        float result_green;

        result_red = ((color_one.rgb_red * vol_one) + (color_two.rgb_red * vol_two)) / (vol_one + vol_two);
        result_green = ((color_one.rgb_green * vol_one) + (color_two.rgb_green * vol_two)) / (vol_one + vol_two);
        result_blue = ((color_one.rgb_blue * vol_one) + (color_two.rgb_blue* vol_two)) / (vol_one + vol_two);

        Object_color color_result = new Object_color(result_red, result_green, result_blue);
        return color_result;
    }
    */
}