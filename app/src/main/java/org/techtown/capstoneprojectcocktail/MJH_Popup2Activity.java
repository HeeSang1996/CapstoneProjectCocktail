package org.techtown.capstoneprojectcocktail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import static org.techtown.capstoneprojectcocktail.MJH_SimulatorUiActivity.adapterMIN;

public class MJH_Popup2Activity extends Activity {
    ListView listview;
    MJH_ListviewAdapter adapter;

    MJH_SimulatorUiActivity simulatorUiAddress;
    public ArrayList<Integer> bufferUpdateStep = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mjh_popup2);
        simulatorUiAddress = ((MJH_SimulatorUiActivity)MJH_SimulatorUiActivity.uiMain);


        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listviewPopup2);

        adapterMIN.callByPopup = 1;


        listview.setAdapter(adapterMIN);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                MJH_ListviewItem item = (MJH_ListviewItem) parent.getItemAtPosition(position) ;
                bufferUpdateStep.add(position+1);

                // TODO : use item data.
            }
        }) ;
    }

    public void mClose(View v){
        finish();
    }

    //확인 버튼 클릭
    public void mBefore(View v){
        Intent intent = new Intent(this,MJH_Popup1Activity.class);
        startActivityForResult(intent, 1);
        finish();
    }
    public void mNext(View v){
        //데이터 전달하기
        Intent intent = new Intent(this,MJH_Popup3Activity.class);
        startActivityForResult(intent, 1);
        simulatorUiAddress.listUpdateStep = bufferUpdateStep;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK){
                String result = data.getStringExtra("result");
                if (result == "-1"){
                    Toast.makeText(this,"call", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

}
