package org.techtown.capstoneprojectcocktail;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;

public class TasteInfoPopupActivity extends Activity {

    private InputMethodManager inputKeyboardHide;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.taste_info_popup_activity);

        //키보드 숨기기
        inputKeyboardHide = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    }

    public void tastePopupClose(View v){
        finish();
    }

    public void tasteInfoPopupCancel(View v){
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
