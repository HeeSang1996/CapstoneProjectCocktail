package org.techtown.capstoneprojectcocktail.ui.myPage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.techtown.capstoneprojectcocktail.CocktailSearchActivity;
import org.techtown.capstoneprojectcocktail.CocktailUploadActivity;
import org.techtown.capstoneprojectcocktail.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MyPageFragment extends Fragment {
    private FirebaseAuth mAuth;
    private ImageView profileImageView;
    private TextView profileTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Snackbar.make(get, "북마크 기능이 들어갈 예정입니다 로그인상태", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mypage, container, false);

        profileImageView=root.findViewById(R.id.profileImageView_myPage);
        profileTextView=root.findViewById(R.id.userNameText_myPage);

        Button bookmarkButtonMyPage = root.findViewById(R.id.button_bookmark_myPage);
        bookmarkButtonMyPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    Snackbar.make(view, "북마크 기능이 들어갈 예정입니다 로그인상태", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else {
                    Snackbar.make(view, "Bookmark 기능은 로그인한 유저만 이용할 수 있습니다.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        });

        Button commentButtonMyPage = root.findViewById(R.id.button_comment_myPage);
        commentButtonMyPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    Snackbar.make(view, "마이 코멘트 기능이 들어갈 예정입니다 로그인상태", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else {
                    Snackbar.make(view, "My Comment 기능은 로그인한 유저만 이용할 수 있습니다.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        });

        Button favoriteButtonMyPage = root.findViewById(R.id.button_favorite_myPage);
        favoriteButtonMyPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    Snackbar.make(view, "마이 페이버릿 기능이 들어갈 예정입니다 로그인상태", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else {
                    Snackbar.make(view, "My Favorite 기능은 로그인한 유저만 이용할 수 있습니다.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        });

        Button simulationButtonMyPage = root.findViewById(R.id.button_simulation_myPage);
        simulationButtonMyPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    Snackbar.make(view, "마이 칵테일 기능이 들어갈 예정입니다 로그인상태", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else {
                    Snackbar.make(view, "My Cocktail 기능은 로그인한 유저만 이용할 수 있습니다.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        });

        Button uploadButtonMyPage = root.findViewById(R.id.button_upload_myPage);
        uploadButtonMyPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "레시피 업로드 기능이 들어갈 예정입니다", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    Intent intent = new Intent(view.getContext(), CocktailUploadActivity.class);
                    startActivity(intent);
                } else {
                    Snackbar.make(view, "Recipe upload 기능은 로그인한 유저만 이용할 수 있습니다.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        });
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUIForMyPage(currentUser);
    }

    public void updateUIForMyPage(final FirebaseUser currentUser) {
        //hideProgressDialog();
        //유저가 로그인한 경우
        if (currentUser != null) {
            final Bitmap[] bitmap = new Bitmap[1];
            Thread mThread= new Thread(){
                @Override
                public void run() {
                    try{
                        URL url = new URL(currentUser.getPhotoUrl().toString());
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true);
                        conn.connect();
                        InputStream is = conn.getInputStream();
                        bitmap[0] = BitmapFactory.decodeStream(is);
                    } catch (MalformedURLException ee) {
                        ee.printStackTrace();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            };
            mThread.start();
            try{
                mThread.join();
                profileImageView.setImageBitmap(bitmap[0]);
                //myPageUserProfilePictureImageView.setImageBitmap(bitmap[0]);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            profileTextView.setText(currentUser.getDisplayName());
            //myPageUserNameTextView.setText(currentUser.getDisplayName());
        }
        //유저가 로그인 하지 않은 경우
        else {
            profileImageView.setImageResource(R.mipmap.ic_launcher_round);
            profileTextView.setText("Unknown");
        }
    }
}
