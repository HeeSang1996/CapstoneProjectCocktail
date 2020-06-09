package org.techtown.capstoneprojectcocktail;

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
import com.google.firebase.firestore.FirebaseFirestore;
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

        Comment_name = new ArrayList();
        Comment_date = new ArrayList();
        Comment_contents = new ArrayList();
        Comment_url = new ArrayList();
        Comment_uid = new ArrayList();

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
                                System.out.println("first 들어옴" + Comment_contents);
                            }
                            recyclerViewForCocktailComment.setAdapter(adapterForCocktailComment);
                            for(int i=0;i<Comment_name.size();i++){
                                //name, date, contents, url, uid
                                adapterForCocktailComment.addItem(new Comment((String) Comment_name.get(i), (String) Comment_date.get(i), (String) Comment_contents.get(i),(String) Comment_url.get(i), (String) Comment_uid.get(i) ));
                            }
                        } else {
                            System.out.println("오류 발생 북마크 컬렉션에서 정상적으로 불러와지지 않음.");
                        }

                    }
                });

        //리사이클러뷰를 클릭했을 경우
        adapterForCocktailComment.setOnItemClickListener(new OnCocktailCommentItemClickListener() {
            @Override
            public void onItemClick(CocktailCommentAdapter.ViewHolder holder, View view, int position) {
                Comment item = adapterForCocktailComment.getItem(position);
                System.out.println(item.getName());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapterForCocktailComment.clearAllForAdapter();
        for(int i=0;i<Comment_name.size();i++){
            //name, date, contents, url, uid
            adapterForCocktailComment.addItem(new Comment((String) Comment_name.get(i), (String) Comment_date.get(i), (String) Comment_contents.get(i),(String) Comment_url.get(i), (String) Comment_uid.get(i) ));
        }
    }
}
//마이 코멘트 액티비티