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

public class MyPageGradingActivity extends AppCompatActivity {

    private CocktailGradingAdapter adapterForCocktailGrading = new CocktailGradingAdapter();
    private RecyclerView recyclerViewForCocktailGrading;

    //db 북마크 컬렉션의 데이터를 읽어와 저장할 리스트 선언
    private ArrayList Grading_name;       //레시피 이름
    private ArrayList Grading_id;         //각 문서 이름
    private ArrayList Grading_ref;        //레시피 ref
    private ArrayList Grading_uid;        //사용자 uid

    //db 레시피들 컬렉션의 데이터를 읽어와 저장할 리스트 선언
    String[] method = new String[200];      //self이면 설명
    String[] Recipe_Base = new String[200]; //self이면 만드는 방법
    String[] abv = new String[200];         //self이면 칵테일 만든이
    String[] Score = new String[200];       //평가한 점수 받아오기
    int count = 0;  //Recipe collection
    int count2 = 0; //Grading collection

    FirebaseAuth mAuth  = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();

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
        adapterForCocktailGrading.setOnItemClickListener(new OnCocktailGradingClickListener() {
            @Override
            public void onItemClick(CocktailGradingAdapter.ViewHolder holder, View view, int position) {
                Cocktail item = adapterForCocktailGrading.getItem(position);
                System.out.println(item.getId());
                Intent intent = new Intent(view.getContext(), CocktailRecipeActivity.class);
                intent.putExtra("cocktailName", item.getName());
                intent.putExtra("cocktailID",item.getId());
                intent.putExtra("cocktailDescription",item.getDescription());
                intent.putExtra("cocktailIngredient",item.getIngredient());
                intent.putExtra("cocktailABV",item.getAbvNum());
                intent.putExtra("cocktailRef",item.getImageUrl());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        adapterForCocktailGrading.clearAllForAdapter();
        //받아오기위해 변수들 초기화 = new ArrayList();
        Grading_name = new ArrayList();       //레시피 이름
        Grading_id = new ArrayList();         //각 문서 이름
        Grading_ref = new ArrayList();        //레시피 ref
        Grading_uid = new ArrayList();        //사용자 uid
        method = new String[200];
        Recipe_Base = new String[200];
        abv = new String[200];

        //북마크 컬렉션에서 사용자 uid와 같은 문서들을 전부 불러온다.
        db.collection("Grading")
                .whereEqualTo("사용자 uid", currentUser.getUid()).orderBy("레시피 이름", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Grading_name.add(document.get("레시피 이름").toString());       //레시피 이름
                                Grading_id.add(document.get("레시피 번호").toString());         //각 문서 이름
                                Grading_ref.add(document.get("레시피 ref").toString());        //레시피 ref
                                Grading_uid.add(document.get("사용자 uid").toString());        //사용자 uid
                            }
                            Set_first();
                        } else {
                            System.out.println("오류 발생 Grading 컬렉션에서 정상적으로 불러와지지 않음.");
                        }

                    }
                });
    }

    void Set_first()
    {
        //가져온 값 프린트
        System.out.println("first 들어옴" + Grading_name);
        for(int i=0; i < Grading_id.size(); i++)
        {
            count = i;
            if( Integer.parseInt(String.valueOf(Grading_id.get(i))) < 10000)
            {
                DocumentReference docRef = db.collection("Recipe").document(String.valueOf(Grading_id.get(i)));
                final int finalI = i; //Recipe
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
                                DocumentReference Grading_doc_ref = db.collection("Grading").document(currentUser.getUid()+String.valueOf(Grading_id.get(count)));
                                Grading_doc_ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                count2 = finalI;
                                                Score[count2] = String.valueOf(document.get("점수"));
                                                recyclerViewForCocktailGrading.setAdapter(adapterForCocktailGrading);
                                                adapterForCocktailGrading.addItem(new Cocktail(Grading_name.get(count2).toString(), Integer.parseInt((String) Grading_id.get(count2)),
                                                        method[count2], Recipe_Base[count2], abv[count2],Grading_ref.get(count2).toString(),Score[count2]));
                                            } else {
                                                System.out.println("오류 발생 해당 컬렉션에 문서가 존재하지 않음.");
                                            }
                                        } else {
                                            System.out.println("오류 발생 셀프 컬렉션에서 정상적으로 불러와지지 않음.");
                                        }
                                    }
                                });

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
                DocumentReference docRef = db.collection("Self").document(String.valueOf(Grading_id.get(i)));
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
                                DocumentReference Grading_doc_ref = db.collection("Grading").document(currentUser.getUid()+String.valueOf(Grading_id.get(count)));
                                Grading_doc_ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                count2 = finalI1;
                                                Score[count2] = String.valueOf(document.get("점수"));
                                                recyclerViewForCocktailGrading.setAdapter(adapterForCocktailGrading);
                                                adapterForCocktailGrading.addItem(new Cocktail(Grading_name.get(count2).toString(), Integer.parseInt((String) Grading_id.get(count2)),
                                                        method[count2], Recipe_Base[count2], abv[count2],Grading_ref.get(count2).toString(),Score[count2]));
                                            } else {
                                                System.out.println("오류 발생 해당 컬렉션에 문서가 존재하지 않음.");
                                            }
                                        } else {
                                            System.out.println("오류 발생 셀프 컬렉션에서 정상적으로 불러와지지 않음.");
                                        }
                                    }
                                });
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

