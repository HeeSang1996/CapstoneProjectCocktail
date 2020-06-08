package org.techtown.capstoneprojectcocktail;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyPageMyRecipeActivity extends AppCompatActivity {

    private CocktailAdapterForSearch adapterForCocktailMyRecipe = new CocktailAdapterForSearch();
    private RecyclerView recyclerViewForCocktailMyRecipe;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_myrecipe_activity);

        recyclerViewForCocktailMyRecipe = findViewById(R.id.recyclerViewForCocktail_myPage_myRecipe);
        LinearLayoutManager layoutManagerForCocktailMyRecipe = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        layoutManagerForCocktailMyRecipe.setReverseLayout(true);
        layoutManagerForCocktailMyRecipe.setStackFromEnd(true);
        recyclerViewForCocktailMyRecipe.setLayoutManager(layoutManagerForCocktailMyRecipe);
        recyclerViewForCocktailMyRecipe.setAdapter(adapterForCocktailMyRecipe);

        //리사이클러뷰를 클릭했을 경우
        adapterForCocktailMyRecipe.setOnItemClickListener(new OnCocktailItemClickListenerForSearch() {
            @Override
            public void onItemClick(CocktailAdapterForSearch.ViewHolder holder, View view, int position) {
                Cocktail cocktail = adapterForCocktailMyRecipe.getItem(position);

                PopupMenu popup= new PopupMenu(getApplicationContext(), view);
                getMenuInflater().inflate(R.menu.popup_menu_my_recipe, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.popup_myRecipe_delete:
                                Toast.makeText(getApplication(),"게시물 삭제",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.popup_myRecipe_recipe:
                                Toast.makeText(getApplication(),"게시물 보기",Toast.LENGTH_SHORT).show();
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
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        adapterForCocktailMyRecipe.clearAllForAdapter();
        adapterForCocktailMyRecipe.addItem(new Cocktail("안녕", 9998, "대충 스까", "대충 스까", "작성자" ,"gs://sbsimulator-96f70.appspot.com/Recipe/AMERICANO.jpg"));
        for(int i=0;i<20;i++){
            adapterForCocktailMyRecipe.addItem(new Cocktail("안녕 나의 레시피"+i, 9998+i, "대충 스까", "대충 스까", "작성자" + i,"gs://sbsimulator-96f70.appspot.com/Recipe/AMERICANO.jpg"));
        }
    }
}
