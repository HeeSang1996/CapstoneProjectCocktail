package org.techtown.capstoneprojectcocktail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class ReportPopupActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.report_popup_activity);

        //데이터 가져오기
        //Intent intent = getIntent();
    }

    //확인 버튼 클릭
    public void reportPopupConfirm(View v){
        //데이터 전달하기
        Toast.makeText(getApplicationContext(),"신고 접수 완료",Toast.LENGTH_LONG).show();
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    //취소 버튼 클릭
    public void reportPopupCancel(View v){
        //데이터 전달하기
        Toast.makeText(getApplicationContext(),"신고 접수 취소",Toast.LENGTH_LONG).show();
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
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
}
