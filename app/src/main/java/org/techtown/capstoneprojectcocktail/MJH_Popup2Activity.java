package org.techtown.capstoneprojectcocktail;

import android.app.Activity;
import android.content.Context;
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

import static org.techtown.capstoneprojectcocktail.CocktailAdapterForSearch.useByMinFlag;
import static org.techtown.capstoneprojectcocktail.MJH_SimulatorUiActivity.adapterMIN;
import static org.techtown.capstoneprojectcocktail.MJH_SimulatorUiActivity.usingStepNum;

public class MJH_Popup2Activity extends Activity {
    public Context uiMe;

    ListView listview;
    int updateTotalVol = 0;

    MJH_SimulatorUiActivity simulatorUiAddress;
    public ArrayList<Integer> bufferUpdateStep = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiMe = this;
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mjh_popup2);
        simulatorUiAddress = ((MJH_SimulatorUiActivity)MJH_SimulatorUiActivity.uiMain);

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listviewPopup2);
        adapterMIN.callByPopup = 1; // 어댑터 변수에서 팝업에서 콜했다고 셋팅
        listview.setAdapter(adapterMIN); //

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                if(simulatorUiAddress.listUpdateTech.equals("Layering") && bufferUpdateStep.size() > 0){
                    Toast myToast = Toast.makeText(uiMe,"Layering의 인풋은 한 스텝당 하나 입니다!", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else{

                    try{
                        MJH_ListviewItem item = (MJH_ListviewItem) parent.getItemAtPosition(position) ;
                        if(!simulatorUiAddress.usingStep.contains(position+1)){
                            simulatorUiAddress.usingStep.add(position+1);
                            bufferUpdateStep.add(position+1);
                            usingStepNum.set(usingStepNum.size() - 1, usingStepNum.get(usingStepNum.size() - 1) + 1);
                            Toast myToast = Toast.makeText(uiMe,"스텝 " + Integer.toString(position+1) + " 추가", Toast.LENGTH_SHORT);
                            myToast.show();

                            //updateTotalVol
                        }
                        else{
                            Toast myToast = Toast.makeText(uiMe,"이미 사용된 스텝입니다.", Toast.LENGTH_SHORT);
                            myToast.show();
                        }
                    }catch(Exception e){
                        Toast myToast = Toast.makeText(uiMe, e.toString(), Toast.LENGTH_LONG);
                        myToast.show();
                    }
                }
            }
        }) ;
    }

    public void mClose(View v){
        usingStepNum.remove(usingStepNum.size() - 1);
        finish();
    }

    //이전 버튼 클릭
    public void mBefore(View v){
        Intent intent = new Intent(this,MJH_Popup1Activity.class);
        startActivityForResult(intent, 1);
        usingStepNum.set(usingStepNum.size() - 1, 0);
        finish();
    }
    //다음 버튼 클릭
    public void mNext(View v){
        //데이터 전달하기
        if(simulatorUiAddress.listUpdateTech.equals("Layering") && bufferUpdateStep.size() > 0){
            simulatorUiAddress.listUpdateStep = bufferUpdateStep;
            simulatorUiAddress.listUpdateIngredient = new ArrayList<MJH_Object_ingredient>();
            simulatorUiAddress.listUpdateFlag = 1;
            useByMinFlag = 0;
            finish();
        }
        else{
            Intent intent = new Intent(this,MJH_Popup3Activity.class);
            startActivityForResult(intent, 1);
            simulatorUiAddress.listUpdateStep = bufferUpdateStep;
            finish();
        }
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
