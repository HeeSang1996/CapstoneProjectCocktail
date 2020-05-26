package org.techtown.capstoneprojectcocktail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class ReportPopupActivity extends Activity {
    private EditText textForReport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.report_popup_activity);

        textForReport = (EditText) findViewById(R.id.editText_report_popup);
        //데이터 가져오기
        //Intent intent = getIntent();
    }

    //확인 버튼 클릭
    public void reportPopupConfirm(View v){
        //데이터 전달하기
        String inputText = textForReport.getText().toString();
        //신고내용이 빈칸인 경우
        if(inputText.getBytes().length==0){
            Toast.makeText(getApplicationContext(),"신고내용을 적어주세요",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"신고 접수 완료\n신고내용: "+inputText,Toast.LENGTH_LONG).show();
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
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
