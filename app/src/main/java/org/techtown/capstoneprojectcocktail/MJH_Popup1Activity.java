package org.techtown.capstoneprojectcocktail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MJH_Popup1Activity extends Activity {
    FrameLayout layout;
    Button bt;
    int buttonFlag = 0;
    int selectButtonId = -1;
    String selectButtonText;

    MJH_SimulatorUiActivity simulatorUiAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mjh_popup1);
        simulatorUiAddress = ((MJH_SimulatorUiActivity)MJH_SimulatorUiActivity.uiMain);

    }

    public void mClose(View v){
        finish();
    }

    public void selectButton(View v){

        bt = (Button) findViewById(v.getId());
        selectButtonText = (String) bt.getText();

        if(simulatorUiAddress.lastStepTechFlag == 1 && !selectButtonText.equals("Layering")){
            Toast myToast = Toast.makeText(this.getApplicationContext(),"Layering이나 Gradient 다음에는 \'" + selectButtonText + "\'룰 선택할 수 없습니다!", Toast.LENGTH_SHORT);
            myToast.show();
            selectButtonText = "";
        }
        else if (buttonFlag == 0){
            bt.setBackgroundColor(Color.parseColor("#ffffff"));
            bt.setTextColor(Color.parseColor("#00B0FF"));
            selectButtonId = v.getId();
            buttonFlag = 1;

            simulatorUiAddress.listUpdateTech = selectButtonText;
            Toast myToast = Toast.makeText(this.getApplicationContext(),selectButtonText + " 선택", Toast.LENGTH_SHORT);
            myToast.show();
        }
        else if(selectButtonId == v.getId()){
            bt = (Button) findViewById(v.getId());
            bt.setBackgroundColor(Color.parseColor("#00B0FF"));
            bt.setTextColor(Color.parseColor("#ffffff"));
            buttonFlag = 0;

            selectButtonText = "";
            simulatorUiAddress.listUpdateTech = selectButtonText;
            Toast myToast = Toast.makeText(this.getApplicationContext(),selectButtonText + " 취소", Toast.LENGTH_SHORT);
            myToast.show();
        }
        else{
            Toast myToast = Toast.makeText(this.getApplicationContext(),"이미 \'" + simulatorUiAddress.listUpdateTech + "\'이 선택되어있습니다.", Toast.LENGTH_SHORT);
            myToast.show();
        }

    }
    public void mBuild(View v){
        Toast myToast = Toast.makeText(this.getApplicationContext(),"mBuild", Toast.LENGTH_SHORT);
        myToast.show();
        selectButton(v);
    }
    public void mStir(View v){
        Toast myToast = Toast.makeText(this.getApplicationContext(),"mStir", Toast.LENGTH_SHORT);
        myToast.show();
        selectButton(v);
    }
    public void mShake(View v){
        Toast myToast = Toast.makeText(this.getApplicationContext(),"mShake", Toast.LENGTH_SHORT);
        myToast.show();
        selectButton(v);
    }
    public void mLayering(View v){
        Toast myToast = Toast.makeText(this.getApplicationContext(),"mLayering", Toast.LENGTH_SHORT);
        myToast.show();
        selectButton(v);
    }
    public void mGradient(View v){
        Toast myToast = Toast.makeText(this.getApplicationContext(),"mGradient", Toast.LENGTH_SHORT);
        myToast.show();
        selectButton(v);
    }
    public void mBlend(View v){
        Toast myToast = Toast.makeText(this.getApplicationContext(),"mBlend", Toast.LENGTH_SHORT);
        myToast.show();
        selectButton(v);
    }
    public void mPour(View v){
        Toast myToast = Toast.makeText(this.getApplicationContext(),"mPour", Toast.LENGTH_SHORT);
        myToast.show();
        selectButton(v);
    }
    public void mMuddle(View v){
        Toast myToast = Toast.makeText(this.getApplicationContext(),"mMuddle", Toast.LENGTH_SHORT);
        myToast.show();
        selectButton(v);
    }
    public void mRolling(View v){
        Toast myToast = Toast.makeText(this.getApplicationContext(),"mRolling", Toast.LENGTH_SHORT);
        myToast.show();
        selectButton(v);
    }
    public void mHelp(View v){
        Toast myToast = Toast.makeText(this.getApplicationContext(),"mHelp", Toast.LENGTH_SHORT);
        myToast.show();
        selectButton(v);
    }

    //확인 버튼 클릭
    public void mBefore(View v){
        //액티비티(팝업) 닫기
        finish();
    }
    public void mNext(View v){
        if(buttonFlag == 1){
            //데이터 전달하기
            Intent intent = new Intent(this,MJH_Popup2Activity.class);
            startActivityForResult(intent, 1);
            finish();
        }
        else{
            Toast.makeText(this,"테크닉을 선택하여 주세요!", Toast.LENGTH_SHORT).show();
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
