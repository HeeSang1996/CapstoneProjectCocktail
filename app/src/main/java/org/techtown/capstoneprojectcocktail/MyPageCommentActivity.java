package org.techtown.capstoneprojectcocktail;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyPageCommentActivity extends AppCompatActivity {

    private CocktailCommentAdapter adapterForCocktailComment = new CocktailCommentAdapter();
    private RecyclerView recyclerViewForCocktailComment;

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
                System.out.println(item.getName());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        adapterForCocktailComment.clearAllForAdapter();
        adapterForCocktailComment.addItem(new Comment("안녕", "오늘", "대충 스까", "https://lh3.googleusercontent.com/-4v8ocWNWiHo/AAAAAAAAAAI/AAAAAAAAAAA/AMZuuckm7kpnOJ3hjgLMfRM9CGDcqhEPsg/s96-c/photo.jpg"
                , "작성자" ));
        for(int i=0;i<20;i++){
            adapterForCocktailComment.addItem(new Comment("안녕"+i, "오늘"+i, "대충 스까"+i, "https://lh3.googleusercontent.com/-4v8ocWNWiHo/AAAAAAAAAAI/AAAAAAAAAAA/AMZuuckm7kpnOJ3hjgLMfRM9CGDcqhEPsg/s96-c/photo.jpg"
                    , "작성자" ));        }
    }
}
