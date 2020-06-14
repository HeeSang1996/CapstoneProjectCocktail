package org.techtown.capstoneprojectcocktail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MyPageCommentActivity extends AppCompatActivity {

    private CocktailCommentAdapter adapterForCocktailComment = new CocktailCommentAdapter();
    private RecyclerView recyclerViewForCocktailComment;

    //db 코멘트 컬렉션의 데이터를 불러서 저장할 리스트 선언
    //name, date, contents, url, uid
    private ArrayList Comment_name;
    private ArrayList Comment_date;
    private ArrayList Comment_contents;
    private ArrayList Comment_url;
    private ArrayList Comment_uid;
    private ArrayList Comment_RecipeName;
    private ArrayList Comment_RecipeID;
    private ArrayList Comment_RecipeRef;

    private String[] method = new String[200];      //self이면 설명
    private String[] Recipe_Base = new String[200]; //self이면 만드는 방법
    private String[] abv = new String[200];         //self이면 칵테일 만든이
    int count = 0;

    FirebaseAuth mAuth  = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_comment_activity);

        recyclerViewForCocktailComment = findViewById(R.id.recyclerViewForComment_myPage_comment);
        LinearLayoutManager layoutManagerForCocktailComment = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        layoutManagerForCocktailComment.setReverseLayout(true);
        layoutManagerForCocktailComment.setStackFromEnd(true);
        recyclerViewForCocktailComment.setLayoutManager(layoutManagerForCocktailComment);
        recyclerViewForCocktailComment.setAdapter(adapterForCocktailComment);


        //리사이클러뷰를 클릭했을 경우
        adapterForCocktailComment.setOnItemClickListener(new OnCocktailCommentItemClickListener() {
            @Override
            public void onItemClick(CocktailCommentAdapter.ViewHolder holder, View view, int position) {
                Comment item = adapterForCocktailComment.getItem(position);
                Intent intent = new Intent(view.getContext(), CocktailRecipeActivity.class);
                intent.putExtra("cocktailName", item.getCocktailName());
                intent.putExtra("cocktailID",item.getCocktailID());
                intent.putExtra("cocktailDescription",item.getCocktailDescription());
                intent.putExtra("cocktailIngredient",item.getCocktailIngredient());
                intent.putExtra("cocktailABV",item.getCocktailAbvNum());
                intent.putExtra("cocktailRef",item.getCocktailImageUri());
                startActivity(intent);
                System.out.println(item.getName());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapterForCocktailComment.clearAllForAdapter();
        //내용물 초기화
        Comment_name = new ArrayList();
        Comment_date = new ArrayList();
        Comment_contents = new ArrayList();
        Comment_url = new ArrayList();
        Comment_uid = new ArrayList();
        Comment_RecipeName = new ArrayList();
        Comment_RecipeID = new ArrayList();
        Comment_RecipeRef = new ArrayList();
        method = new String[200];
        Recipe_Base = new String[200];
        abv = new String[200];

        db.collection("Comment")
                .whereEqualTo("사용자 uid", currentUser.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Comment_name.add(document.get("사용자 이름").toString());       //사용자 이름
                                Comment_date.add(document.get("댓글 날짜").toString());         //날짜
                                Comment_contents.add(document.get("내용").toString());          //댓글 내용
                                Comment_url.add(document.get("사용자 url").toString());         //사용자 이미지 url
                                Comment_uid.add(document.get("사용자 uid").toString());         //사용자 uid
                                //추가되는 사항
                                Comment_RecipeName.add(document.get("레시피 이름").toString());       //칵테일 이름
                                Comment_RecipeID.add(document.get("레시피 번호").toString());         //칵테일 번호
                                Comment_RecipeRef.add(document.get("레시피 ref").toString());         //칵테일 이미지

                                System.out.println("first 들어옴" + Comment_contents);
                            }
                            Set_first();
                        } else {
                            System.out.println("오류 발생 북마크 컬렉션에서 정상적으로 불러와지지 않음.");
                        }

                    }
                });
    }
    void Set_first()
    {
        //가져온 값 프린트
        System.out.println("first 들어옴" + Comment_RecipeName);
        for(int i=0; i < Comment_RecipeName.size(); i++)
        {
            count = i;
            if( Integer.parseInt(String.valueOf(Comment_RecipeID.get(i))) < 10000)
            {
                DocumentReference docRef = db.collection("Recipe").document(String.valueOf(Comment_RecipeID.get(i)));
                final int finalI = i;
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                count = finalI;
                                Recipe_Base[count] = String.valueOf(document.get("Ingredient_content"));
                                Recipe_Base[count] = Recipe_Base[count].replaceAll("\\,", "ml ");
                                Recipe_Base[count] = Recipe_Base[count].replaceAll("\\{", " ");
                                Recipe_Base[count] = Recipe_Base[count].replaceAll("\\}", "ml ");
                                Recipe_Base[count] = Recipe_Base[count].replaceAll("\\=", " ");
                                method[count] = (document.get("method").toString());
                                abv[count] = (document.get("abv").toString()) + "%";
                                recyclerViewForCocktailComment.setAdapter(adapterForCocktailComment);
                                adapterForCocktailComment.addItem(new Comment((String) Comment_name.get(count), (String) Comment_date.get(count),
                                        (String) Comment_contents.get(count),(String) Comment_url.get(count), (String) Comment_uid.get(count),
                                        Comment_RecipeName.get(count).toString(), Integer.parseInt((String) Comment_RecipeID.get(count)),
                                        method[count], Recipe_Base[count], abv[count],Comment_RecipeRef.get(count).toString()));
                            } else {
                                System.out.println("오류 발생 해당 컬렉션에 문서가 존재하지 않음.");
                            }
                        } else {
                            System.out.println("오류 발생 레시피 컬렉션에서 정상적으로 불러와지지 않음.");
                        }
                    }
                });
            }
            else
            {
                DocumentReference docRef = db.collection("Self").document(String.valueOf(Comment_RecipeID.get(i)));
                final int finalI1 = i;
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                count = finalI1;
                                Recipe_Base[count] = String.valueOf(document.get("만드는 방법"));
                                method[count] = (document.get("칵테일 설명").toString());
                                abv[count] = (document.get("칵테일 만든이").toString());
                                recyclerViewForCocktailComment.setAdapter(adapterForCocktailComment);
                                adapterForCocktailComment.addItem(new Comment((String) Comment_name.get(count), (String) Comment_date.get(count),
                                        (String) Comment_contents.get(count),(String) Comment_url.get(count), (String) Comment_uid.get(count),
                                        Comment_RecipeName.get(count).toString(), Integer.parseInt((String) Comment_RecipeID.get(count)),
                                        method[count], Recipe_Base[count], abv[count],Comment_RecipeRef.get(count).toString()));
                            } else {
                                System.out.println("오류 발생 해당 컬렉션에 문서가 존재하지 않음.");
                            }
                        } else {
                            System.out.println("오류 발생 셀프 컬렉션에서 정상적으로 불러와지지 않음.");
                        }
                    }
                });
            }
        }
    }
}
//마이 코멘트 액티비티