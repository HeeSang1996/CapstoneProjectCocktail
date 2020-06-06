package org.techtown.capstoneprojectcocktail.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
import org.techtown.capstoneprojectcocktail.TestForCosine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static java.lang.Integer.parseInt;
import static java.util.Date.parse;

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
    //레시피에 들어가는 재료들을 {}형식의 스트링 형태로 저장
    String[] Recipe_list = new String[81];
    //레시피에 들어가는 재료의 갯수 및 각 5미의 총합을 int형으로 저장(계산 편의를 위하여)
    int[] Recipe_count = new int[81];
    int[] Sum_sugar = new int[81];
    int[] Sum_bitter = new int[81];
    int[] Sum_sour = new int[81];
    int[] Sum_salty = new int[81];
    int[] Sum_hot = new int[81];
    //레시피에 들어가는 재료 이름, 양을 스트링, 넘버 형태로 저장
    String[][] Recipe_namelist = new String[81][10];
    Number[][] Recipe_volumelist = new Number[81][10];
    //레시피에 들어간 각 재료들의 5미를 스트링 형태로 저장, 한 레시피당 최대 재료갯수를 10으로 임의지정
    String[][] Sugar_Value = new String[81][10];
    String[][] Bitter_Value = new String[81][10];
    String[][] Sour_Value = new String[81][10];
    String[][] Salty_Value = new String[81][10];
    String[][] Hot_Value = new String[81][10];

    public void setDocument() {
        //기존 레시피들에 대한 재료, 양 가져오기 테스트용
        for(int i=0; i < 81; i++)
        {
            count = i;
            DocumentReference docRef = db.collection("Recipe").document(String.valueOf(i+6001));
            final int finalI = i;
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //기존레시피의 재료 구성들을 Map형태로 저장한다.
                        Map<String, Number> Recipe_Ingredient = (Map<String, Number>) document.get("Ingredient_content");
                        Recipe_list[finalI] = Recipe_Ingredient.toString();
                        //해당 레시피의 재료 갯수를 int배열형태에 저장한다.
                        Recipe_count[finalI] = Recipe_Ingredient.size();
                        //해당 레시피의 이름을 string 형태로 저장한다.
                        String Name = document.get("Recipe_name").toString();
                        //각각의 배열에 Map의 키값(재료이름), 밸류값(재료양)의 값들을 따로따로 얻는 방법
                        String[] Ingredient_key = Recipe_Ingredient.keySet().toArray(new String[0]);
                        Number[] Ingredient_Value = Recipe_Ingredient.values().toArray(new Number[0]);
                        Recipe_namelist[finalI] = Recipe_Ingredient.keySet().toArray(new String[0]);
                        Recipe_volumelist[finalI] = Recipe_Ingredient.values().toArray(new Number[0]);
                        //Log.d(TAG, (finalI+6001)+ "번 레시피 이름: " + Name + " , " + "재료 이름: " +  Recipe_namelist[finalI][0]+", 재료 양: " + Recipe_volumelist[finalI][0].toString());
                        //배열에 정상적으로 재료이름과 양의 값이 들어갔는지 재료갯수만큼 for구문을 돌려 로그로 확인
                        for(int j=0; j < Recipe_Ingredient.size(); j++)
                        {
                            final int finalJ = j;
                            //몇번 레시피의 재료이름, 양이 제대로 들어갔는지 로그 확인
                            //Log.d(TAG, (finalI+6001)+ "번 레시피 이름: " + Name + " , " + "재료 이름: " + Ingredient_key[j]+", 재료 양: " + Ingredient_Value[j].toString());
                            db.collection("Ingredient").whereEqualTo("Ingredient_name",Ingredient_key[j]).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Sugar_Value[finalI][finalJ] = String.valueOf(document.getData().get("단맛"));
                                            Bitter_Value[finalI][finalJ] = String.valueOf(document.getData().get("쓴맛"));
                                            Sour_Value[finalI][finalJ] = String.valueOf(document.getData().get("신맛"));
                                            Salty_Value[finalI][finalJ] = String.valueOf(document.getData().get("짠맛"));
                                            Hot_Value[finalI][finalJ] = String.valueOf(document.getData().get("매운맛"));
                                        }
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }
                                }
                            });
                        }

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        }
        //기존 레시피별 오미 총 합 계산하기
        for(int i = 0; i < 81; i++)
        {
            Sum_sugar[i] = 0;
            Sum_bitter[i] = 0;
            Sum_sour[i] = 0;
            Sum_salty[i] = 0;
            Sum_hot[i] = 0;
            for(int j=0; j < Recipe_count[i]; j++)
             {
                 //null 값 들어갔을대의 에러 핸들링
                if(Sugar_Value[i][j] == null) Sugar_Value[i][j] = "0";
                if(Bitter_Value[i][j] == null) Bitter_Value[i][j] = "0";
                if(Sour_Value[i][j] == null) Sour_Value[i][j] = "0";
                if(Salty_Value[i][j] == null) Salty_Value[i][j] = "0";
                if(Hot_Value[i][j] == null) Hot_Value[i][j] = "0";

                Sum_sugar[i] += (parseInt(Sugar_Value[i][j]) * Recipe_volumelist[i][j].intValue());
                Sum_bitter[i] += (parseInt(Bitter_Value[i][j]) * Recipe_volumelist[i][j].intValue());
                Sum_sour[i] += (parseInt(Sour_Value[i][j]) * Recipe_volumelist[i][j].intValue());
                Sum_salty[i] += (parseInt(Salty_Value[i][j]) * Recipe_volumelist[i][j].intValue());
                Sum_hot[i] += (parseInt(Hot_Value[i][j]) * Recipe_volumelist[i][j].intValue());
             }
            Log.d(TAG, "로그확인: "+(6001+i)+"번째 레시피의 단맛 총합: "+Sum_sugar[i]);
            Log.d(TAG, "로그확인: "+(6001+i)+"번째 레시피의 쓴맛 총합: "+Sum_bitter[i]);
            Log.d(TAG, "로그확인: "+(6001+i)+"번째 레시피의 신맛 총합: "+Sum_sour[i]);
            Log.d(TAG, "로그확인: "+(6001+i)+"번째 레시피의 짠맛 총합: "+Sum_salty[i]);
            Log.d(TAG, "로그확인: "+(6001+i)+"번째 레시피의 매운맛 총합: "+Sum_hot[i]);
        }
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
        //여기의 .doument()안의 숫자를 조정함으로써 어떤 레시피를 가져올 것인가 특정 가능
//        DocumentReference docRef = db.collection("Recipe").document("6001");
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        //해당 데이터 전부 읽어오기, 및 로그를 통해 읽어왔는지 확인
//                        //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
//                        //일부만 읽어오기
//                        //기존레시피의 재료 구성들을 Map형태로 저장한다.
//                        Map<String, Number> Recipe_Ingredient = (Map<String, Number>) document.get("Ingredient_content");
//
//                        //재료, 양들이 정상적으로 읽어졌는지 확인
//                        //Log.d(TAG, "Recipe_Ingredient Key data: " + Recipe_Ingredient.keySet());
//                        //Log.d(TAG, "Recipe_Ingredient Value data: " + Recipe_Ingredient.values());\
//
//                        //각각의 배열에 Map의 키값(재료이름), 밸류값(재료양)을 순서대로 저장한다.
//                        String[] Ingredient_key = Recipe_Ingredient.keySet().toArray(new String[0]);
//                        Number[] Ingredient_Value = Recipe_Ingredient.values().toArray(new Number[0]);
//
//                        //for구문을 통해 재료 갯수만큼 돌린다.
//                        for(int i=0; i < Recipe_Ingredient.size(); i++)
//                        {
//                            //재료, 양들이 배열형태로 정상적으로 값이 들어왔는지 확인)
//                            //Log.d(TAG, "Recipe_Ingredient Key data: " + Ingredient_key[i]);
//                            //Log.d(TAG, "Recipe_Ingredient Value data: " + Ingredient_Value[i]);
//                            final int finalI = i;
//                            //위를통해 읽어온 레시피들의 이름을 토대로 재료 테이블에 있는 DB와 이름이 일치하는 재료들의 5미 값을 저장한다.
//                            db.collection("Ingredient").whereEqualTo("Ingredient_name",Ingredient_key[i]).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                    if (task.isSuccessful()) {
//                                        for (QueryDocumentSnapshot document : task.getResult()) {
//                                            Sugar_Value[finalI] = String.valueOf(document.getData().get("단맛"));
//                                            Log.d(TAG, document.getId() + "의 쓴맛 => " + document.getData().get("단맛"));
//                                            Bitter_Value[finalI] = String.valueOf(document.getData().get("쓴맛"));
//                                            Log.d(TAG, document.getId() + "의 신맛 => " + document.getData().get("쓴맛"));
//                                            Sour_Value[finalI] = String.valueOf(document.getData().get("신맛"));
//                                            Log.d(TAG, document.getId() + "의 짠맛 => " + document.getData().get("신맛"));
//                                            Salty_Value[finalI] = String.valueOf(document.getData().get("짠맛"));
//                                            Log.d(TAG, document.getId() + "의 매운맛 => " + document.getData().get("짠맛"));
//                                            Hot_Value[finalI] = String.valueOf(document.getData().get("매운맛"));
//                                            Log.d(TAG, document.getId() + "의 단맛 => " + document.getData().get("매운맛"));
//                                        }
//                                    } else {
//                                        Log.d(TAG, "Error getting documents: ", task.getException());
//                                    }
//                                }
//                            });
//                            //Log.d(TAG, "Recipe_Ingredient Sugar Value data: " + Sugar_Value[i]);
//
//                        }
//                        //Log.d(TAG, "Recipe_Ingredient Value data: " + Recipe_Ingredient.);
//
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
                //setDocument();
            }
        });

        RecyclerView recyclerViewForIngredientButton = root.findViewById(R.id.recyclerViewForIngredient);
        LinearLayoutManager layoutManagerForIngredientButton = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerViewForIngredientButton.setLayoutManager(layoutManagerForIngredientButton);
        final CocktailIngredientButtonAdapter adapterForIngredientButton = new CocktailIngredientButtonAdapter();

        //영진 여기 확인
        //String[] main_keyword = {"가니쉬", "데킬라", "럼", "리큐르", "맥주", "베르무트", "보드카", "브랜디", "와인", "위스키", "주스", "진"};
        //맥주로 검색되는거 없음 맥주 키워드 삭제
        //가니쉬로 검색되는거 없음 가니쉬 키워드 삭제
        //영진아 이거 검색 안되는거 확인좀 하고 주자
        List<String> main_keyword = new ArrayList<>(Arrays.asList("데킬라", "럼", "리큐르", "베르무트", "보드카", "브랜디", "와인", "위스키", "주스", "진"));
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

        //테스트용 삭제필
        Button testButton = root.findViewById(R.id.buttonForTestCosine_home);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), TestForCosine.class);
                startActivity(intent);
            }
        });
        //테스트용 삭제필
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
