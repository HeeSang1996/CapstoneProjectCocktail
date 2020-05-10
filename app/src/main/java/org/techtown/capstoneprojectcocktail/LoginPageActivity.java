package org.techtown.capstoneprojectcocktail;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class LoginPageActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page_activity);

        TextInputLayout textInputLayoutForLoginID = findViewById(R.id.textInputLayoutForID_login);
        final EditText editTextForLoginID = textInputLayoutForLoginID.getEditText();

        TextInputLayout textInputLayoutForLoginPassWord = findViewById(R.id.textInputLayoutForPassWord_login);
        final EditText editTextForLoginPassWord = textInputLayoutForLoginPassWord.getEditText();

        Button loginButtonLoginPage = findViewById(R.id.buttonForLogin_login);
        loginButtonLoginPage.setOnClickListener(new OnSingleClickListener(){
            @Override
            public void onSingleClick(View view){
                //영진 여기다가 로그인 하는 함수 박아주셈
                //email == ID, passWord == 패스워드
                //둘다 스트링타입
                //프로필 사진 불러와서 이 함수 안에서 bitmap포맷으로 여기다가 채워주셈
                //할수 있으면 profileImageView_login에 사진 직접 박아줘 ㅎ
                String email = editTextForLoginID.getText().toString();
                String passWord = editTextForLoginPassWord.getText().toString();
                Snackbar.make(view, "로그인ID "+email + " 로그인패스워드 " + passWord, Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        Button loginCheckButtonLoginPage = findViewById(R.id.buttonForLoginCheck_login);
        loginCheckButtonLoginPage.setOnClickListener(new OnSingleClickListener(){
            @Override
            public void onSingleClick(View view){
                //영진 여기다가 로그인 체크 출력하는 함수 박아주셈
                Snackbar.make(view, "로그인체크: ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        Button logoutButtonLoginPage = findViewById(R.id.buttonForLogout_login);
        logoutButtonLoginPage.setOnClickListener(new OnSingleClickListener(){
            @Override
            public void onSingleClick(View view){
                //영진 여기다가 로그아웃 하는 함수 박아주셈
                Snackbar.make(view, "로그아웃", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }
}
