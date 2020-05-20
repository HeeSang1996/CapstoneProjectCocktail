package org.techtown.capstoneprojectcocktail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
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
    private FloatingActionButton floatingActionButtonForThumbUp;
    private FloatingActionButton floatingActionButtonForThumbDown;
    private TextInputLayout textInputLayoutForComment;
    private int cocktailID;
    private String stringForCocktailComment;
    private FirebaseAuth mAuth;
    private ImageButton imageButtonForComment;
    private RecyclerView recyclerViewForComment;
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
        textInputLayoutForComment = (TextInputLayout) findViewById(R.id.textInputLayout_comment_recipe);
        imageButtonForComment = (ImageButton) findViewById(R.id.imageButton_comment_recipe);
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

        //로그인 하지 않으면
        //북마크, 좋아요, 싫어요. 댓글입력 버튼 안보이게
        if(currentUser == null){
            floatingActionButtonForBookmark.setVisibility(View.GONE);
            floatingActionButtonForThumbUp.setVisibility(View.GONE);
            floatingActionButtonForThumbDown.setVisibility(View.GONE);
            linearLayoutForCommentTextInput.setVisibility(View.GONE);
        }

        //로그인한 유저의 북마크와
        //좋아요 싫어요 정보를 가져와서
        //북마크, 좋아요,싫어요 버튼의 초기값 세팅
        //영진파트
        //checked의 값이 true면 버튼의 초기모양 변경
        if(bookmarkChecked==true){
            floatingActionButtonForBookmark.setImageResource(R.mipmap.outline_star_white_36dp);
        }
        if(thumbUpChecked==true){
            floatingActionButtonForThumbUp.setImageResource(R.mipmap.baseline_thumb_up_white_36dp);
        }
        if(thumbDownChecked==true){
            floatingActionButtonForThumbDown.setImageResource(R.mipmap.baseline_thumb_down_white_36dp);
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
        final CocktailIngredientButtonAdapter adapterForIngredientButton = new CocktailIngredientButtonAdapter();
        for(int i=0; i<20; i++) {
            adapterForCocktailComment.addItem(new Comment("hi"+i,"hi","hi","hi","ho"));
        }
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
                else{
                    long now = System.currentTimeMillis();
                    Date date = new Date(now);
                    SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    String formatDate = sdfNow.format(date);
                    Toast.makeText(getApplicationContext(),stringForCocktailComment,Toast.LENGTH_LONG).show();
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
                if(user==null) {
                    Toast.makeText(getApplicationContext(), "본인의 댓글만 삭제 가능합니다! 로그인 필요", Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(),"삭제 가능한 칵테일 " + item.getName(),Toast.LENGTH_LONG).show();
                }
                //자신이 올린 글을 선택했을 경우 삭제 가능
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
                }else{
                    Toast.makeText(getApplicationContext(),"본인의 댓글만 삭제 가능합니다!",Toast.LENGTH_LONG).show();
                }
                /*
                Intent intent = new Intent(view.getContext(), CocktailRecipeActivity.class);
                intent.putExtra("cocktailName", item.getName());
                intent.putExtra("cocktailID",item.getId());
                intent.putExtra("cocktailDescription",item.getDescription());
                intent.putExtra("cocktailIngredient",item.getIngredient());
                intent.putExtra("cocktailABV",item.getAbvNum());
                intent.putExtra("cocktailRef",item.getImageUrl());
                startActivity(intent);
                 */
            }
        });

        floatingActionButtonForBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //북마크 버튼은 계정당 한번만
                //두번 누를시 북마크 취소
                //영진 파트
                if(bookmarkChecked==true){
                    Toast.makeText(getApplicationContext(),"북마크 취소",Toast.LENGTH_LONG).show();
                    floatingActionButtonForBookmark.setImageResource(R.mipmap.outline_star_border_white_36dp);
                    bookmarkChecked=false;
                }else{
                    Toast.makeText(getApplicationContext(),"북마크",Toast.LENGTH_LONG).show();
                    floatingActionButtonForBookmark.setImageResource(R.mipmap.outline_star_white_36dp);
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
                    floatingActionButtonForThumbUp.setImageResource(R.mipmap.outline_thumb_up_white_36dp);
                    thumbUpChecked=false;
                }else{
                    Toast.makeText(getApplicationContext(),"좋아요",Toast.LENGTH_LONG).show();
                    floatingActionButtonForThumbUp.setImageResource(R.mipmap.baseline_thumb_up_white_36dp);
                    thumbUpChecked=true;
                    floatingActionButtonForThumbDown.setImageResource(R.mipmap.outline_thumb_down_white_36dp);
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
                    floatingActionButtonForThumbDown.setImageResource(R.mipmap.outline_thumb_down_white_36dp);
                    thumbDownChecked=false;
                }else{
                    Toast.makeText(getApplicationContext(),"싫어요",Toast.LENGTH_LONG).show();
                    floatingActionButtonForThumbDown.setImageResource(R.mipmap.baseline_thumb_down_white_36dp);
                    thumbDownChecked=true;
                    floatingActionButtonForThumbUp.setImageResource(R.mipmap.outline_thumb_up_white_36dp);
                    thumbUpChecked=false;
                }
            }
        });
    }
}
