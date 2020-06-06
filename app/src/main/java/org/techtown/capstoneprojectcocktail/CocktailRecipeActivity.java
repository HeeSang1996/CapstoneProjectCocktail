package org.techtown.capstoneprojectcocktail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CocktailRecipeActivity extends AppCompatActivity {
    private static final String TAG = "CocktailRecipeActivity";
    CocktailCommentAdapter adapterForCocktailComment = new CocktailCommentAdapter();
    private FloatingActionButton floatingActionButtonForBookmark;
    private FloatingActionButton floatingActionButtonForGrade;
    private FloatingActionButton floatingActionButtonForReport;
    private FloatingActionButton floatingActionButtonForAnimation;
    private TextInputLayout textInputLayoutForComment;
    private RatingBar ratingBar;
    private int cocktailID;
    private String stringForCocktailComment;
    private FirebaseAuth mAuth;
    private ImageButton imageButtonForComment;
    private RecyclerView recyclerViewForComment;
    //이전에 북마크,평가,신고를 체크 했는지 안했는지 판단
    private boolean bookmarkChecked=false;
    private boolean gradeChecked=false;
    private boolean reportChecked=false;
    private String gradeScore;
    //플로팅 버튼 애니메이션
    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;


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
        floatingActionButtonForGrade = (FloatingActionButton) findViewById(R.id.floatingActionButton_grade_recipe);
        floatingActionButtonForReport = (FloatingActionButton) findViewById(R.id.floatingActionButton_report_recipe);
        floatingActionButtonForAnimation = (FloatingActionButton) findViewById(R.id.floatingActionButton_animation_recipe);
        textInputLayoutForComment = (TextInputLayout) findViewById(R.id.textInputLayout_comment_recipe);
        imageButtonForComment = (ImageButton) findViewById(R.id.imageButton_comment_recipe);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar_grading_recipe);
        //수정필
        gradeScore="0.0";
        LinearLayout linearLayoutForCommentTextInput = findViewById(R.id.linearLayout_cocktail_recipe);
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

        //플로팅 버튼 애니메이션
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

        //로그인 안한 경우
        if(currentUser == null){
            //로그인 하지 않으면
            //북마크, 평가, 신고, 댓글입력 버튼 안보이게
            //floatingActionButtonForBookmark.setVisibility(View.GONE);
            //floatingActionButtonForGrade.setVisibility(View.GONE);
            //floatingActionButtonForReport.setVisibility(View.GONE);

            //애니메이션 버튼만 안보이면 나머지는 안보이겠지 아마
            floatingActionButtonForAnimation.setVisibility(View.GONE);
            linearLayoutForCommentTextInput.setVisibility(View.GONE);
        }
        //로그인한 경우
        else{
            //로그인한 유저의 북마크와
            //평가, 신고 정보를 가져와서
            //북마크, 평가, 신고 버튼의 초기값 세팅
            //영진파트
            //checked의 값이 true면 버튼의 초기모양 변경
            if(bookmarkChecked==true){
                floatingActionButtonForBookmark.setImageResource(R.mipmap.baseline_bookmark_white_36dp);
            }
            if(gradeChecked==true){
                floatingActionButtonForGrade.setImageResource(R.mipmap.outline_star_white_36dp);
            }
            if(reportChecked==true){
                floatingActionButtonForReport.setImageResource(R.mipmap.baseline_feedback_white_36dp);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        recyclerViewForComment = findViewById(R.id.recyclerViewForComment_recipe);
        LinearLayoutManager layoutManagerForCocktailComment = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);

        //역순 출력
        layoutManagerForCocktailComment.setReverseLayout(true);
        layoutManagerForCocktailComment.setStackFromEnd(true);

        recyclerViewForComment.setLayoutManager(layoutManagerForCocktailComment);
//        for(int i=0; i<20; i++) {
//            adapterForCocktailComment.addItem(new Comment("hi"+i,"hi","hi","hi","ho"));
//        }
        recyclerViewForComment.setAdapter(adapterForCocktailComment);

        textInputLayoutForComment.setCounterEnabled(true);
        textInputLayoutForComment.setCounterMaxLength(150);
        final EditText editTextForCocktailComment = textInputLayoutForComment.getEditText();
        editTextForCocktailComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>150) {
                    editTextForCocktailComment.setError("150자 이하로 입력해주세요!");
                } else {
                    editTextForCocktailComment.setError(null);
                }
            }
        });

        imageButtonForComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringForCocktailComment = editTextForCocktailComment.getText().toString();
                if(stringForCocktailComment.getBytes().length==0){
                    Toast.makeText(getApplicationContext(),"빈칸을 남기지 말아주세요!",Toast.LENGTH_LONG).show();
                }else if(editTextForCocktailComment.getError()!=null){
                    Toast.makeText(getApplicationContext(),"문자 길이를 준수해주세요!",Toast.LENGTH_LONG).show();
                }
                //정상 상태일 경우
                //날짜 정보등과 함께
                //댓글 등록
                //영진파트
                else{
                    long now = System.currentTimeMillis();
                    Date date = new Date(now);
                    SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    String formatDate = sdfNow.format(date);
                    Toast.makeText(getApplicationContext(),"댓글 내용: " + stringForCocktailComment,Toast.LENGTH_LONG).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    adapterForCocktailComment.addItem(new Comment(user.getDisplayName(),"날짜: " + formatDate,stringForCocktailComment,user.getPhotoUrl().toString(),user.getUid()));
                    recyclerViewForComment.setAdapter(adapterForCocktailComment);
                    editTextForCocktailComment.getText().clear();
                }
            }
        });

        adapterForCocktailComment.setOnItemClickListener(new OnCocktailCommentItemClickListener() {
            @Override
            public void onItemClick(CocktailCommentAdapter.ViewHolder holder, View view, int position) {
                Comment item = adapterForCocktailComment.getItem(position);
                FirebaseUser user = mAuth.getCurrentUser();

                //자신이 올린 글을 선택했을 경우 삭제 가능

                //로그인을 하지 않은 경우
                if(user==null) {
                    Toast.makeText(getApplicationContext(), "본인의 댓글만 삭제 가능합니다! 로그인 필요", Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(),"삭제 가능한 칵테일 " + item.getName(),Toast.LENGTH_LONG).show();
                }
                //자신이 올린 글을 선택했을 경우
                //삭제 가능
                //영진 파트
                else if(item.getUid()==user.getUid()){
                    PopupMenu popup= new PopupMenu(getApplicationContext(), view);
                    final int positionForDelete=position;
                    getMenuInflater().inflate(R.menu.popup_menu_user_comment, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()){
                                case R.id.popup_delete:
                                    //댓글 삭제
                                    adapterForCocktailComment.removeItem(positionForDelete);
                                    recyclerViewForComment.setAdapter(adapterForCocktailComment);
                                    Toast.makeText(getApplication(),"삭제",Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getApplication(),"취소",Toast.LENGTH_SHORT).show();
                                    break;
                            }
                            return false;
                        }
                    });
                    popup.show();
                }
                //로그인을 했지만
                //다른사람의 댓글을 선택했을 경우
                else{
                    Toast.makeText(getApplicationContext(),"본인의 댓글만 삭제 가능합니다!",Toast.LENGTH_LONG).show();
                }
            }
        });


        floatingActionButtonForBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //영진 파트

                //북마크가 이미 선택되었던 경우
                if(bookmarkChecked==true){
                    Toast.makeText(getApplicationContext(),"북마크 취소",Toast.LENGTH_LONG).show();
                    floatingActionButtonForBookmark.setImageResource(R.mipmap.outline_bookmark_border_white_36dp);
                    bookmarkChecked=false;
                }
                //북마크를 추가하는 경우
                else{
                    Toast.makeText(getApplicationContext(),"북마크",Toast.LENGTH_LONG).show();
                    floatingActionButtonForBookmark.setImageResource(R.mipmap.baseline_bookmark_white_36dp);
                    bookmarkChecked=true;
                }
            }
        });

        floatingActionButtonForGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //평가는 계정당 한번만
                //평가는 취소 불가
                //평가는 수정만 가능
                //영진 파트

                //평가 수정을 원하는 경우
                if(gradeChecked==true){
                    Toast.makeText(getApplicationContext(),"평가 수정",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(v.getContext(), GradingPopupActivity.class);
                    intent.putExtra("gradeCheck",gradeChecked);
                    intent.putExtra("gradeScore",gradeScore);
                    startActivityForResult(intent, 1);
                }
                //처음으로 평가를 하는 경우
                else{
                    Toast.makeText(getApplicationContext(),"평가 시작",Toast.LENGTH_LONG).show();
                    //평가를 중간에 취소하면
                    //gradeChecked가 true로 변경되면 안됨
                    //수정 필
                    Intent intent = new Intent(v.getContext(), GradingPopupActivity.class);
                    intent.putExtra("gradeCheck",gradeChecked);
                    intent.putExtra("gradeScore",gradeScore);
                    startActivityForResult(intent, 1);
                    //floatingActionButtonForGrade.setImageResource(R.mipmap.outline_star_white_36dp);
                    //gradeChecked=true;
                }
            }
        });

        floatingActionButtonForReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //신고는 계정당 한번만
                //같은 게시물에 같은 계정이 여러번 신고 불가능
                //영진 파트

                //이미 신고를 한 경우
                //신고 거부 안내
                if(reportChecked==true){
                    Toast.makeText(getApplicationContext(),"이미 신고하신 게시물입니다",Toast.LENGTH_LONG).show();
                }
                //처음으로 신고를 하는 경우
                else{
                    Intent intent = new Intent(v.getContext(), ReportPopupActivity.class);
                    startActivityForResult(intent, 2);
                    //Toast.makeText(getApplicationContext(),"신고 접수",Toast.LENGTH_LONG).show();
                }
            }
        });

        floatingActionButtonForAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //애니메이션 시작
                anim();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //평가 팝업의 결과물
        //requestCode == 1
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                floatingActionButtonForGrade.setImageResource(R.mipmap.outline_star_white_36dp);
                gradeChecked=true;
                //data.getFloatExtra("rating");
                //ratingBar.setRating(data.getExtras().getFloat("rating"));
                String ratingString = data.getStringExtra("rating");
                float ratingFloat = Float.valueOf(ratingString);
                ratingBar.setRating(ratingFloat);
                gradeScore = ratingString;
                Toast.makeText(getApplicationContext(), "Result: " + data.getStringExtra("rating"), Toast.LENGTH_SHORT).show();
            }
        }
        //신고 팝업의 결과물
        //requestCode == 2
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                floatingActionButtonForReport.setImageResource(R.mipmap.baseline_feedback_white_36dp);
                reportChecked=true;
            }
        }
    }

    //플로팅 버튼 애니메이션을 위한 버튼
    public void anim() {
        if (isFabOpen) {
            floatingActionButtonForBookmark.startAnimation(fab_close);
            floatingActionButtonForGrade.startAnimation(fab_close);
            floatingActionButtonForReport.startAnimation(fab_close);
            floatingActionButtonForBookmark.setClickable(false);
            floatingActionButtonForGrade.setClickable(false);
            floatingActionButtonForReport.setClickable(false);
            isFabOpen = false;
        } else {
            floatingActionButtonForBookmark.startAnimation(fab_open);
            floatingActionButtonForGrade.startAnimation(fab_open);
            floatingActionButtonForReport.startAnimation(fab_open);
            floatingActionButtonForBookmark.setClickable(true);
            floatingActionButtonForGrade.setClickable(true);
            floatingActionButtonForReport.setClickable(true);
            isFabOpen = true;
        }
    }
}