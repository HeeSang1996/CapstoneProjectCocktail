package org.techtown.capstoneprojectcocktail;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyPageBookmarkActivity extends AppCompatActivity {

    private CocktailAdapterForSearch adapterForCocktailBookmark = new CocktailAdapterForSearch();
    private RecyclerView recyclerViewForCocktailBookmark;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_bookmark_activity);

        recyclerViewForCocktailBookmark = findViewById(R.id.recyclerViewForCocktail_myPage_bookmark);
        LinearLayoutManager layoutManagerForCocktailBookmark = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        layoutManagerForCocktailBookmark.setReverseLayout(true);
        layoutManagerForCocktailBookmark.setStackFromEnd(true);
        recyclerViewForCocktailBookmark.setLayoutManager(layoutManagerForCocktailBookmark);
        recyclerViewForCocktailBookmark.setAdapter(adapterForCocktailBookmark);

        //리사이클러뷰를 클릭했을 경우
        adapterForCocktailBookmark.setOnItemClickListener(new OnCocktailItemClickListenerForSearch() {
            @Override
            public void onItemClick(CocktailAdapterForSearch.ViewHolder holder, View view, int position) {
                Cocktail item = adapterForCocktailBookmark.getItem(position);
                System.out.println(item.getId());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        adapterForCocktailBookmark.clearAllForAdapter();
        adapterForCocktailBookmark.addItem(new Cocktail("안녕", 9998, "대충 스까", "대충 스까", "작성자" ,"gs://sbsimulator-96f70.appspot.com/Recipe/AMERICANO.jpg"));
        for(int i=0;i<20;i++){
            adapterForCocktailBookmark.addItem(new Cocktail("안녕 북마크"+i, 9998+i, "대충 스까", "대충 스까", "작성자" + i,"gs://sbsimulator-96f70.appspot.com/Recipe/AMERICANO.jpg"));
        }
    }
}
