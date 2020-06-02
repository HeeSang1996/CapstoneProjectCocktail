package org.techtown.capstoneprojectcocktail;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MJH_SimulatorGraphicActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.mjh_graphic);

        float i;


        i = MJH_SimulatorUiActivity.test.simulatorStep.get(MJH_SimulatorUiActivity.test.simulatorStep.size()-1 ).totalVolume;
        TextView text;
        text = (TextView) findViewById(R.id.textView) ;
        text.setText(String.valueOf(i));
    }
}
