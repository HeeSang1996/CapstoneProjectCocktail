package org.techtown.capstoneprojectcocktail;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    private ArrayList Bookmark_uid;        //사용자 uid

    //db 레시피들 컬렉션의 데이터를 읽어와 저장할 리스트 선언
    String[] method = new String[200];      //self이면 설명
    String[] Recipe_Base = new String[200]; //self이면 만드는 방법
    String[] abv = new String[200];         //self이면 칵테일 만든이
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
        //받아오기위해 변수들 초기화 = new ArrayList();
        Bookmark_name = new ArrayList();       //레시피 이름
        Bookmark_id = new ArrayList();         //각 문서 이름
        Bookmark_ref = new ArrayList();        //레시피 ref
        Bookmark_uid = new ArrayList();        //사용자 uid
        method = new String[200];
        Recipe_Base = new String[200];
        abv = new String[200];

        //북마크 컬렉션에서 사용자 uid와 같은 문서들을 전부 불러온다.
        db.collection("Bookmark")
                .whereEqualTo("사용자 uid", currentUser.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Bookmark_name.add(document.get("레시피 이름").toString());       //레시피 이름
                                Bookmark_id.add(document.get("레시피 번호").toString());         //각 문서 이름
                                Bookmark_ref.add(document.get("레시피 ref").toString());        //레시피 ref
                                Bookmark_uid.add(document.get("사용자 uid").toString());        //사용자 uid
                            }
                            Set_first();
                        } else {
                            System.out.println("오류 발생 북마크 컬렉션에서 정상적으로 불러와지지 않음.");
                        }

                    }
                });

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
        //adapterForCocktailBookmark.addItem(new Cocktail("안녕", 9998, "대충 스까", "대충 스까", "작성자" ,"gs://sbsimulator-96f70.appspot.com/Recipe/AMERICANO.jpg"));
        //해당 북마크 갯수만큼 for구문을 돌려 아이템 add
        for(int i = 0; i < Bookmark_name.size(); i++)
        {
            adapterForCocktailBookmark.addItem(new Cocktail(Bookmark_name.get(count).toString(), Integer.parseInt((String) Bookmark_id.get(count)),
                    method[count], Recipe_Base[count], abv[count],Bookmark_ref.get(count).toString()));
        }
    }

    void Set_first()
    {
        //가져온 값 프린트
        System.out.println("first 들어옴" + Bookmark_name);
        for(int i=0; i < Bookmark_id.size(); i++)
        {
            count = i;
            if( Integer.parseInt(String.valueOf(Bookmark_id.get(i))) < 10000)
            {
                DocumentReference docRef = db.collection("Recipe").document(String.valueOf(Bookmark_id.get(i)));
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
                                recyclerViewForCocktailBookmark.setAdapter(adapterForCocktailBookmark);
                                adapterForCocktailBookmark.addItem(new Cocktail(Bookmark_name.get(count).toString(), Integer.parseInt((String) Bookmark_id.get(count)),
                                        method[count], Recipe_Base[count], abv[count],Bookmark_ref.get(count).toString()));
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
                DocumentReference docRef = db.collection("Self").document(String.valueOf(Bookmark_id.get(i)));
                final int finalI1 = i;
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                count = finalI1;
                                Recipe_Base[count] = String.valueOf(document.get("만드는 방법"));
                                method[count] = (document.get("설명").toString());
                                abv[count] = (document.get("칵테일 만든이").toString());
                                recyclerViewForCocktailBookmark.setAdapter(adapterForCocktailBookmark);
                                adapterForCocktailBookmark.addItem(new Cocktail(Bookmark_name.get(count).toString(), Integer.parseInt((String) Bookmark_id.get(count)),
                                        method[count], Recipe_Base[count], abv[count],Bookmark_ref.get(count).toString()));
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
//마이 페이지 북마크 액티비티 푸시