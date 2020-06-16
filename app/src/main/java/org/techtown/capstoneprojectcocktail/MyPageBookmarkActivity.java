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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MyPageBookmarkActivity extends AppCompatActivity {

    private CocktailAdapterForSearch adapterForCocktailBookmark = new CocktailAdapterForSearch();
    private RecyclerView recyclerViewForCocktailBookmark;
    //db 북마크 컬렉션의 데이터를 읽어와 저장할 리스트 선언
    private ArrayList Bookmark_name;       //레시피 이름
    private ArrayList Bookmark_id;         //각 문서 이름
    private ArrayList Bookmark_ref;        //레시피 ref

    //db 레시피들 컬렉션의 데이터를 읽어와 저장할 리스트 선언
    String[] method = new String[200];      //self이면 설명
    String[] Recipe_Base = new String[200]; //self이면 만드는 방법
    String[] abv = new String[200];         //self이면 칵테일 만든이
    String[] Recipe_name = new String[200]; //self이면 만드는 방법
    String[] Recipe_id = new String[200]; //self이면 만드는 방법
    String[] Recipe_ref = new String[200]; //self이면 만드는 방법
    int count = 0;

    FirebaseAuth mAuth  = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();

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
                Intent intent = new Intent(view.getContext(), CocktailRecipeActivity.class);
                intent.putExtra("cocktailName", item.getName());
                intent.putExtra("cocktailID",item.getId());
                intent.putExtra("cocktailDescription",item.getDescription());
                intent.putExtra("cocktailIngredient",item.getIngredient());
                intent.putExtra("cocktailABV",item.getAbvNum());
                intent.putExtra("cocktailRef",item.getImageUrl());
                startActivity(intent);
                System.out.println(item.getId());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapterForCocktailBookmark.clearAllForAdapter();
        //받아오기위해 변수들 초기화 = new ArrayList();
        Bookmark_name = new ArrayList();       //레시피 이름
        Bookmark_id = new ArrayList();         //각 문서 이름
        Bookmark_ref = new ArrayList();        //레시피 ref
        method = new String[200];
        Recipe_Base = new String[200];
        abv = new String[200];
        count = 0;

        //북마크 컬렉션에서 사용자 uid와 같은 문서들을 전부 불러온다.
        db.collection("Bookmark")
                .whereEqualTo("사용자 uid", currentUser.getUid()).orderBy("북마크 날짜")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Bookmark_name.add(document.get("레시피 이름").toString());       //레시피 이름
                                Bookmark_id.add(document.get("레시피 번호").toString());         //각 문서 이름
                                Bookmark_ref.add(document.get("레시피 ref").toString());        //레시피 ref

                                method[count] = document.get("method").toString();
                                Recipe_Base[count] = document.get("base").toString();
                                if(Integer.parseInt(Bookmark_id.get(count).toString()) < 7000 && Integer.parseInt(Bookmark_id.get(count).toString()) > 6000)
                                {
                                    abv[count] = document.get("abv").toString() + "%";
                                }
                                else
                                {
                                    abv[count] = document.get("abv").toString();
                                }
                                recyclerViewForCocktailBookmark.setAdapter(adapterForCocktailBookmark);
                                adapterForCocktailBookmark.addItem(new Cocktail((String) Bookmark_name.get(count), Integer.parseInt((String) Bookmark_id.get(count)),
                                        method[count], Recipe_Base[count], abv[count],Bookmark_ref.get(count).toString()));
                                count++;
                            }
                        } else {
                            System.out.println("오류 발생 북마크 컬렉션에서 정상적으로 불러와지지 않음.");
                        }
                    }
                });
    }

}
//마이 페이지 북마크 액티비티 푸시