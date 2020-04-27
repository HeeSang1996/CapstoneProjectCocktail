package org.techtown.capstoneprojectcocktail.ui.myPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;

import org.techtown.capstoneprojectcocktail.R;

public class MyPageFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mypage, container, false);

        Button bookmarkButtonMyPage = root.findViewById(R.id.button_bookmark_myPage);
        bookmarkButtonMyPage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Snackbar.make(view, "북마크 기능이 들어갈 예정입니다", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        Button commentButtonMyPage = root.findViewById(R.id.button_comment_myPage);
        commentButtonMyPage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Snackbar.make(view, "마이 코멘트 기능이 들어갈 예정입니다", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        Button favoriteButtonMyPage = root.findViewById(R.id.button_favorite_myPage);
        favoriteButtonMyPage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Snackbar.make(view, "마이 페이버릿 기능이 들어갈 예정입니다", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        Button simulationButtonMyPage = root.findViewById(R.id.button_simulation_myPage);
        simulationButtonMyPage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Snackbar.make(view, "마이 시뮬레이션 기능이 들어갈 예정입니다", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        Button uploadButtonMyPage = root.findViewById(R.id.button_upload_myPage);
        uploadButtonMyPage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Snackbar.make(view, "레시피 업로드 기능이 들어갈 예정입니다", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        return root;
    }

}
