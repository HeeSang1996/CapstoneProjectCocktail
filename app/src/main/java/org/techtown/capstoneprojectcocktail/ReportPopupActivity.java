package org.techtown.capstoneprojectcocktail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ReportPopupActivity extends Activity {
    private EditText textForReport;
    private RadioButton r_btn1,r_btn2, r_btn3,r_btn4;
    private RadioGroup radioGroup;
    private InputMethodManager inputKeyboardHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.report_popup_activity);

        textForReport = (EditText) findViewById(R.id.editText_report_popup);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup_report);
        r_btn1 = (RadioButton) findViewById(R.id.radioButton1_report);
        r_btn2 = (RadioButton) findViewById(R.id.radioButton2_report);
        r_btn3 = (RadioButton) findViewById(R.id.radioButton3_report);
        r_btn4 = (RadioButton) findViewById(R.id.radioButton4_report);

        //키보드 숨기기
        inputKeyboardHide = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        //데이터 가져오기
        //Intent intent = getIntent();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.radioButton1_report:
                    case R.id.radioButton2_report:
                    case R.id.radioButton3_report:
                        textForReport.getText().clear();
                        inputKeyboardHide.hideSoftInputFromWindow(textForReport.getWindowToken(), 0);
                        textForReport.setVisibility(View.GONE);
                        break;
                    case R.id.radioButton4_report:
                        Toast.makeText(getApplicationContext(), "신고내용을 적어주세요", Toast.LENGTH_LONG).show();
                        textForReport.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }

    //확인 버튼 클릭
    public void reportPopupConfirm(View v){
        //데이터 전달하기
        Intent intent = new Intent();
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.radioButton1_report:
                Toast.makeText(getApplicationContext(),"신고 접수 완료\n신고내용: "+ r_btn1.getText().toString(),Toast.LENGTH_LONG).show();
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.radioButton2_report:
                Toast.makeText(getApplicationContext(),"신고 접수 완료\n신고내용: "+ r_btn2.getText().toString(),Toast.LENGTH_LONG).show();
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.radioButton3_report:
                Toast.makeText(getApplicationContext(),"신고 접수 완료\n신고내용: "+ r_btn3.getText().toString(),Toast.LENGTH_LONG).show();
                setResult(RESULT_OK, intent);
                finish();
                break;
            // 신고 내용이 기타일 경우
            // 신고 내용을 적어야함
            case R.id.radioButton4_report:
                String inputText = textForReport.getText().toString();
                if(inputText.getBytes().length==0){
                    Toast.makeText(getApplicationContext(),"신고내용을 적어주세요",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"신고 접수 완료\n신고내용: "+inputText,Toast.LENGTH_LONG).show();
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
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
