package org.techtown.capstoneprojectcocktail.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.techtown.capstoneprojectcocktail.Cocktail;
import org.techtown.capstoneprojectcocktail.CocktailAdapterForHome;
import org.techtown.capstoneprojectcocktail.CocktailIngredientButton;
import org.techtown.capstoneprojectcocktail.CocktailIngredientButtonAdapter;
import org.techtown.capstoneprojectcocktail.CocktailRecipeActivity;
import org.techtown.capstoneprojectcocktail.CocktailSearchActivity;
import org.techtown.capstoneprojectcocktail.MJH_SimulatorUiActivity;
import org.techtown.capstoneprojectcocktail.OnCocktailIngredientButtonItemClickListener;
import org.techtown.capstoneprojectcocktail.OnCocktailItemClickListenerForHome;
import org.techtown.capstoneprojectcocktail.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HomeFragment extends Fragment {
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    String[] Recipe_name = new String[20];
    int[] ID = new int[20];
    String[] method = new String[20];
    String[] Recipe_Base = new String[20];
    String[] abv = new String[20];
    String[] ref = new String[20];
    long[] Realabv = new long[20];
    int count;
    Map<String, Number> Recipe_Ingredient;

    public void setDocument() {
        //새로운 테이블 생성하기
//        for(int i = 6001; i < 6082; i++)
//        {
//            DocumentReference UpdateRef = db.collection("Recipe").document(String.valueOf(i));
//            UpdateRef
//                    .update("bad_number", "0")
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Log.d(TAG, "DocumentSnapshot successfully updated!");
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.w(TAG, "Error updating document", e);
//                        }
//                    });
//        }
        //새로운 도큐먼트 생성하기
//        Map<String, Object> Ingredient_info = new HashMap<>();
//        Ingredient_info.put("Ingredient_name", "테스트");
//        Ingredient_info.put("Ingredient_type", "베이스");
//        Ingredient_info.put("abv", 0);
//        Ingredient_info.put("sugar_rate", 24);
//        Ingredient_info.put("salty", 0);
//        Ingredient_info.put("bitter", 0);
//        Ingredient_info.put("sour", 0);
//        Ingredient_info.put("flavour", "개같은 맛과 향");
//        Ingredient_info.put("specific_gravity", 0.135);
//        Map<String, Number> Ingredient_color = new HashMap<>();
//
//        Ingredient_color.put("Red", 210);
//        Ingredient_color.put("Green", 0);
//        Ingredient_color.put("Blue", 0);
//
//        Ingredient_info.put("Ingredient_color", Ingredient_color);
//
//        db.collection("Ingredient").document("5006")
//                .set(Ingredient_info)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.d(TAG, "DocumentSnapshot successfully written!");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "Error writing document", e);
//                    }
//                });
        // [END set_document]

        //Map<String, Object> data = new HashMap<>();
        // [START set_with_id]
        //db.collection("Ingredient").document("5006").set(data);
        // [END set_with_id]

        //특정 도큐먼트 가져오기
//        DocumentReference docRef = db.collection("Ingredient").document("5006");
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        //해당 데이터 전부 읽어오기
//                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
//                        //일부만 읽어오기
//                        String name = (String) document.get("Ingredient_name");
//                        Log.d(TAG, "DocumentSnapshot data: " + name);
//                    } else {
//                        Log.d(TAG, "No such document");
//                    }
//                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
//                }
//            }
//        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        Button searchButtonHome = root.findViewById(R.id.button_search_home);
        //서치 빈칸
        searchButtonHome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), CocktailSearchActivity.class);
                intent.putExtra("ingredientName", "");
                startActivity(intent);
            }
        });

        FloatingActionButton simulationFloatingButtonHome = root.findViewById(R.id.button_simulation_action);
        simulationFloatingButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "시뮬레이션 기능이 들어갈 예정입니다.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                //////////////// By MJH
                Intent intent = new Intent(view.getContext(), // 현재 화면의 제어권자
                        MJH_SimulatorUiActivity.class); // 다음 넘어갈 클래스 지정
                startActivity(intent); // 다음 화면으로 넘어간다
                //////////////// By MJH
                setDocument();
            }
        });

        RecyclerView recyclerViewForIngredientButton = root.findViewById(R.id.recyclerViewForIngredient);
        LinearLayoutManager layoutManagerForIngredientButton = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerViewForIngredientButton.setLayoutManager(layoutManagerForIngredientButton);
        final CocktailIngredientButtonAdapter adapterForIngredientButton = new CocktailIngredientButtonAdapter();

        //영진 여기 확인
        //String[] main_keyword = {"가니쉬", "데킬라", "럼", "리큐르", "맥주", "베르무트", "보드카", "브랜디", "와인", "위스키", "주스", "진"};
        List<String> main_keyword = new ArrayList<>(Arrays.asList("가니쉬", "데킬라", "럼", "리큐르", "맥주", "베르무트", "보드카", "브랜디", "와인", "위스키", "주스", "진"));
        Collections.shuffle(main_keyword);
        for(int i = 0; i < main_keyword.size() ; i ++) {
            adapterForIngredientButton.addItem(new CocktailIngredientButton(main_keyword.get(i)));
        }

        recyclerViewForIngredientButton.setAdapter(adapterForIngredientButton);
        adapterForIngredientButton.setOnItemClickListener(new OnCocktailIngredientButtonItemClickListener() {
            @Override
            public void onItemClick(CocktailIngredientButtonAdapter.ViewHolder holder, View view, int position) {
                CocktailIngredientButton item = adapterForIngredientButton.getItem(position);

                Intent intent = new Intent(view.getContext(), CocktailSearchActivity.class);
                intent.putExtra("ingredientName", item.getIngredientCategorizedName());
                startActivity(intent);
            }
        });

        final RecyclerView recyclerViewForCocktailHome = root.findViewById(R.id.recyclerViewForCocktail_home);
        LinearLayoutManager layoutManagerForCocktailHome = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerViewForCocktailHome.setLayoutManager(layoutManagerForCocktailHome);
        final CocktailAdapterForHome adapterForCocktailHome = new CocktailAdapterForHome();

        //수정필 테스트용
        //영진 여기 확인
        for(int i=0; i < 20; i++)
        {
            List<String> list;
            count = i;
            DocumentReference docRef = db.collection("Recipe").document(String.valueOf(i+6001));

            final int finalI = i;
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    Recipe_name[count] = (String) document.get("Recipe_name");
                    ID[count] = 6001+ count;
                    method[count] = (String) document.get("method");
                    Recipe_Ingredient = (Map<String, Number>) document.get("Ingredient_content");
                    //Recipe_Base[count] = (String) document.get("Recipe_Base");

                    //map으로 받아온 정보를 string으로 치환한뒤 유저에게 보여줄 수 있도록 replaceall함({, }, = 삭제 ml 추가)
                    Recipe_Base[count] = String.valueOf(Recipe_Ingredient);
                    Recipe_Base[count] = Recipe_Base[count].replaceAll("\\,", "ml ");
                    Recipe_Base[count] = Recipe_Base[count].replaceAll("\\{", " ");
                    Recipe_Base[count] = Recipe_Base[count].replaceAll("\\}", "ml ");
                    Recipe_Base[count] = Recipe_Base[count].replaceAll("\\=", " ");
                    //abv[0] = (String) document.get("abv");
                    Realabv[count] = (long) document.get("abv");
                    abv[count] = Realabv[count] + "%";
                    ref[count] = (String) document.get("ref");
                    adapterForCocktailHome.addItem(new Cocktail(Recipe_name[count], ID[count], method[count], Recipe_Base[count], abv[count],ref[count]));
                    //Log.d(TAG, "DocumentSnapshot data: " + Recipe_name[count] + ID[count]+ method[count]+ Recipe_Base[count]+ abv[count]+ref[count]);
                    //refresh 해주는 함수(아마)
                    recyclerViewForCocktailHome.setAdapter(adapterForCocktailHome);

                } else {
                    Log.d(TAG, "No such document");
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        }
        });
        }
        adapterForCocktailHome.setOnItemClickListener(new OnCocktailItemClickListenerForHome() {
            @Override
            public void onItemClick(CocktailAdapterForHome.ViewHolder holder, View view, int position) {
                Cocktail item = adapterForCocktailHome.getItem(position);
                //Toast.makeText(getActivity().getApplicationContext(),"선택된 칵테일: " + item.getName(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(view.getContext(), CocktailRecipeActivity.class);
                intent.putExtra("cocktailName", item.getName());
                intent.putExtra("cocktailDescription",item.getDescription());
                intent.putExtra("cocktailIngredient",item.getIngredient());
                intent.putExtra("cocktailABV",item.getAbvNum());
                intent.putExtra("cocktailRef",item.getImageUrl());
                startActivity(intent);
            }
        });

//        for(int i=0; i<20; i++) {
//                adapterForCocktailHome.addItem(new Cocktail(Recipe_name[i], i, "맛있는 칵테일 " + i + "의 설명 정말 맛있다 맛있는 칵테일" + i +
//                        "의 설명 정말 맛있다 ", "Whisky0",i*10 + " %","gs://sbsimulator-96f70.appspot.com/Recipe/BETWEEN THE SHEETS.jpg"));
//        }

//        for(int i=0; i < 20; i++)
//        {
//            final int finalI = i;
//            DocumentReference docRef = db.collection("Recipe").document(String.valueOf(i+6001));
//            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                    if (task.isSuccessful()) {
//                        DocumentSnapshot document = task.getResult();
//                        if (document.exists()) {
//                            //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
//                            Recipe_name[0] = (String) document.get("Recipe_name");
//                            ID[0] = 6001+ finalI;
//                            method[0] = (String) document.get("method");
//                            Recipe_Base[0] = (String) document.get("Recipe_Base");
//                            //abv[0] = (String) document.get("abv");
//                            abv[0] = "시발";
//                            ref[0] = (String) document.get("ref");
//                            Log.d(TAG, "DocumentSnapshot data: " + Recipe_name[0] + ID[0]+ method[0]+ Recipe_Base[0]+ abv[0]+ref[0]);
//                            adapterForCocktailHome.addItem(new Cocktail(Recipe_name[0], ID[0], method[0], Recipe_Base[0], abv[0],ref[0]));
//                        } else {
//                            Log.d(TAG, "No such document");
//                        }
//                    } else {
//                        Log.d(TAG, "get failed with ", task.getException());
//                    }
//                }
//            });
//        }
        return root;
    }
}
