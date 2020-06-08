package org.techtown.capstoneprojectcocktail;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyPageGradingActivity extends AppCompatActivity {

    private CocktailAdapterForSearch adapterForCocktailGrading = new CocktailAdapterForSearch();
    private RecyclerView recyclerViewForCocktailGrading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_grading_activity);

        recyclerViewForCocktailGrading = findViewById(R.id.recyclerViewForCocktail_myPage_grading);
        LinearLayoutManager layoutManagerForCocktailGrading = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        layoutManagerForCocktailGrading.setReverseLayout(true);
        layoutManagerForCocktailGrading.setStackFromEnd(true);
        recyclerViewForCocktailGrading.setLayoutManager(layoutManagerForCocktailGrading);
        recyclerViewForCocktailGrading.setAdapter(adapterForCocktailGrading);

        //리사이클러뷰를 클릭했을 경우
        adapterForCocktailGrading.setOnItemClickListener(new OnCocktailItemClickListenerForSearch() {
            @Override
            public void onItemClick(CocktailAdapterForSearch.ViewHolder holder, View view, int position) {
                Cocktail item = adapterForCocktailGrading.getItem(position);
                System.out.println(item.getId());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        adapterForCocktailGrading.clearAllForAdapter();
        adapterForCocktailGrading.addItem(new Cocktail("안녕", 9998, "대충 스까", "대충 스까", "작성자" ,"gs://sbsimulator-96f70.appspot.com/Recipe/AMERICANO.jpg"));
        for(int i=0;i<20;i++){
            adapterForCocktailGrading.addItem(new Cocktail("안녕 그레이딩"+i, 9998+i, "대충 스까", "대충 스까", "작성자" + i,"gs://sbsimulator-96f70.appspot.com/Recipe/AMERICANO.jpg"));
        }
    }
}
