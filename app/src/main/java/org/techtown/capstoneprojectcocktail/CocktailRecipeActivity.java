package org.techtown.capstoneprojectcocktail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class CocktailRecipeActivity extends AppCompatActivity {
    private FloatingActionButton floatingActionButtonForBookmark;
    private FloatingActionButton floatingActionButtonForThumbUp;
    private FloatingActionButton floatingActionButtonForThumbDown;
    private int cocktailID;
    private FirebaseAuth mAuth;
    //이전에 북마크,좋아요.싫어요를 체크 했는지 안했는지 판단
    private boolean bookmarkChecked=false;
    private boolean thumbUpChecked=false;
    private boolean thumbDownChecked=false;

    @SuppressLint("RestrictedApi")
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.cocktail_recipe_activity);

        Intent intent = getIntent();

        TextView textForCocktailName = (TextView) findViewById(R.id.textView_cocktailName_recipe);
        TextView textForCocktailID = (TextView) findViewById(R.id.textView_ingredientText_recipe);
        TextView textForCocktailDescription = (TextView) findViewById(R.id.textView_cocktailDescription_recipe);
        TextView textForCocktailIngredient = (TextView) findViewById(R.id.textView_cocktailIngredient_recipe);
        TextView textForCocktailABV = (TextView) findViewById(R.id.textView_cocktailABV_recipe);
        floatingActionButtonForBookmark = (FloatingActionButton) findViewById(R.id.floatingActionButton_bookmark_recipe);
        floatingActionButtonForThumbUp = (FloatingActionButton) findViewById(R.id.floatingActionButton_thumbUp_recipe);
        floatingActionButtonForThumbDown = (FloatingActionButton) findViewById(R.id.floatingActionButton_thumbDown_recipe);
        final ImageView imageForCocktail = (ImageView) findViewById(R.id.imageView_cocktail_recipe);

        String cocktailName = intent.getExtras().getString("cocktailName");
        cocktailID = intent.getExtras().getInt("cocktailID");
        String cocktailDescription = intent.getExtras().getString("cocktailDescription");
        String cocktailIngredient = intent.getExtras().getString("cocktailIngredient");
        String cocktailABV = intent.getExtras().getString("cocktailABV");
        String cocktailRef = intent.getExtras().getString("cocktailRef");

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference gsReference = storage.getReferenceFromUrl(cocktailRef);

        gsReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    // Glide 이용하여 이미지뷰에 로딩
                    Glide.with(CocktailRecipeActivity.this)
                            .load(task.getResult())
                            .into(imageForCocktail);
                } else {
                    // URL을 가져오지 못하면 토스트 메세지
                }
            }
        });
        textForCocktailName.setText(cocktailName);
        //인텐트로 받은 값이
        //재료일 경우
        //재료대신 설탕 함유량 문구 출력
        if((cocktailID/1000)==5)
            textForCocktailID.setText("설탕 함유량");
        else
            textForCocktailID.setText("재료");
        textForCocktailDescription.setText(cocktailDescription);
        textForCocktailIngredient.setText(cocktailIngredient);
        textForCocktailABV.setText(cocktailABV);

        //로그인 하지 않거나 재료에 대한 설명이면
        //북마크, 좋아요, 싫어요. 댓글입력 버튼 안보이게
        if((cocktailID/1000)==5||currentUser == null){
            floatingActionButtonForBookmark.setVisibility(View.GONE);
            floatingActionButtonForThumbUp.setVisibility(View.GONE);
            floatingActionButtonForThumbDown.setVisibility(View.GONE);
        }
        //로그인한 유저의 북마크와
        //좋아요 싫어요 정보를 가져와서
        //북마크, 좋아요,싫어요 버튼의 초기값 세팅
        //checked의 값이 true이면 버튼의 초기모양 변경
        if(bookmarkChecked==true){
            floatingActionButtonForBookmark.setImageResource(R.mipmap.outline_star_white_18dp);
        }
        if(thumbUpChecked==true){
            floatingActionButtonForThumbUp.setImageResource(R.mipmap.baseline_thumb_up_white_18dp);
        }
        if(thumbDownChecked==true){
            floatingActionButtonForThumbDown.setImageResource(R.mipmap.baseline_thumb_down_white_18dp);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        floatingActionButtonForBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //북마크 버튼은 계정당 한번만
                //두번 누를시 북마크 취소
                //영진 파트
                if(bookmarkChecked==true){
                    Toast.makeText(getApplicationContext(),"북마크 취소",Toast.LENGTH_LONG).show();
                    floatingActionButtonForBookmark.setImageResource(R.mipmap.outline_star_border_white_18dp);
                    bookmarkChecked=false;
                }else{
                    Toast.makeText(getApplicationContext(),"북마크",Toast.LENGTH_LONG).show();
                    floatingActionButtonForBookmark.setImageResource(R.mipmap.outline_star_white_18dp);
                    bookmarkChecked=true;
                }
            }
        });

        floatingActionButtonForThumbUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //좋아요 버튼은 계정당 한번만
                //두번 누를시 좋아요 취소
                //싫어요 버튼이 눌린상태에서
                //좋아요 버튼 누를시 싫어요 버튼 취소
                //영진 파트
                if(thumbUpChecked==true){
                    Toast.makeText(getApplicationContext(),"좋아요 취소",Toast.LENGTH_LONG).show();
                    floatingActionButtonForThumbUp.setImageResource(R.mipmap.outline_thumb_up_white_18dp);
                    thumbUpChecked=false;
                }else{
                    Toast.makeText(getApplicationContext(),"좋아요",Toast.LENGTH_LONG).show();
                    floatingActionButtonForThumbUp.setImageResource(R.mipmap.baseline_thumb_up_white_18dp);
                    thumbUpChecked=true;
                    floatingActionButtonForThumbDown.setImageResource(R.mipmap.outline_thumb_down_white_18dp);
                    thumbDownChecked=false;
                }
            }
        });

        floatingActionButtonForThumbDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //싫어요 버튼은 계정당 한번만
                //두번 누를시 싫어요 취소
                //좋아요 버튼이 눌린상태에서
                //싫어요 버튼 누를시 좋아요 버튼 취소
                //영진 파트
                if(thumbDownChecked==true){
                    Toast.makeText(getApplicationContext(),"싫어요 취소",Toast.LENGTH_LONG).show();
                    floatingActionButtonForThumbDown.setImageResource(R.mipmap.outline_thumb_down_white_18dp);
                    thumbDownChecked=false;
                }else{
                    Toast.makeText(getApplicationContext(),"싫어요",Toast.LENGTH_LONG).show();
                    floatingActionButtonForThumbDown.setImageResource(R.mipmap.baseline_thumb_down_white_18dp);
                    thumbDownChecked=true;
                    floatingActionButtonForThumbUp.setImageResource(R.mipmap.outline_thumb_up_white_18dp);
                    thumbUpChecked=false;
                }
            }
        });
    }

}
