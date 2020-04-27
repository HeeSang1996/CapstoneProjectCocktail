package org.techtown.capstoneprojectcocktail.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.techtown.capstoneprojectcocktail.Cocktail;
import org.techtown.capstoneprojectcocktail.CocktailAdapter;
import org.techtown.capstoneprojectcocktail.CocktailIngredient;
import org.techtown.capstoneprojectcocktail.CocktailIngredientAdapter;
import org.techtown.capstoneprojectcocktail.CocktailRecipeActivity;
import org.techtown.capstoneprojectcocktail.CocktailSearchActivity;
import org.techtown.capstoneprojectcocktail.MainActivity;
import org.techtown.capstoneprojectcocktail.OnCocktailIngredientItemClickListener;
import org.techtown.capstoneprojectcocktail.OnCocktailItemClickListener;
import org.techtown.capstoneprojectcocktail.R;

import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HomeFragment extends Fragment {
    public FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void setDocument() {
        Map<String, Object> Ingredient_info = new HashMap<>();
        Ingredient_info.put("Ingredient_name", "테스트");
        Ingredient_info.put("Ingredient_type", "베이스");
        Ingredient_info.put("abv", "테스트");
        Ingredient_info.put("sugar_rate", 24);
        Ingredient_info.put("salty", 0);
        Ingredient_info.put("bitter", 0);
        Ingredient_info.put("sour", 0);
        Ingredient_info.put("flavour", "개같은 맛과 향");
        Ingredient_info.put("specific_gravity", 0.135);
        Map<String, Number> Ingredient_color = new HashMap<>();

        Ingredient_color.put("Red", 210);
        Ingredient_color.put("Green", 0);
        Ingredient_color.put("Blue", 0);

        Ingredient_info.put("Ingredient_color", Ingredient_color);

        db.collection("Ingredient").document("5006")
                .set(Ingredient_info)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
        // [END set_document]

        //Map<String, Object> data = new HashMap<>();
        // [START set_with_id]
        //db.collection("Ingredient").document("5006").set(data);
        // [END set_with_id]

        //가져오기
        DocumentReference docRef = db.collection("Ingredient").document("5006");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //해당 데이터 전부 읽어오기
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        //일부만 읽어오기
                        String name = (String) document.get("Ingredient_name");
                        Log.d(TAG, "DocumentSnapshot data: " + name);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        Button searchButtonHome = root.findViewById(R.id.button_search_home);
        searchButtonHome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Snackbar.make(view, "서치 기능이 들어갈 예정입니다", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent intent = new Intent(view.getContext(), CocktailSearchActivity.class);
                intent.putExtra("ingredientName", "");
                startActivity(intent);

            }
        });

        FloatingActionButton simulationFloatingButtonHome = root.findViewById(R.id.button_simulation_action);
        simulationFloatingButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "시뮬레이션 기능이 들어갈 예정입니다.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                setDocument();
            }
        });

        RecyclerView recyclerViewForIngredientHome = root.findViewById(R.id.recyclerViewForIngredient);
        LinearLayoutManager layoutManagerForIngredientHome = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerViewForIngredientHome.setLayoutManager(layoutManagerForIngredientHome);
        final CocktailIngredientAdapter adapterForIngredientHome = new CocktailIngredientAdapter();

        //수정필 테스트용
        Map<String, Number> ingredient_color = new HashMap<String, Number>();
        ingredient_color.put("red", 0);
        for(int i = 0; i < 10 ; i ++) {
            adapterForIngredientHome.addItem(new CocktailIngredient("Rum" + i,
                    "base", 0, 0, 0, 0, 0,
                    "칵테일이 짜다 애미야", 0, ingredient_color));
            adapterForIngredientHome.addItem(new CocktailIngredient("Whisky" + i,
                    "base", 0, 0, 0, 0, 0,
                    "칵테일이 짜다 애미야", 0, ingredient_color));
        }
        //수정필 테스트용

        recyclerViewForIngredientHome.setAdapter(adapterForIngredientHome);

        adapterForIngredientHome.setOnItemClickListener(new OnCocktailIngredientItemClickListener() {
            @Override
            public void onItemClick(CocktailIngredientAdapter.ViewHolder holder, View view, int position) {
                CocktailIngredient item = adapterForIngredientHome.getItem(position);

                Intent intent = new Intent(view.getContext(), CocktailSearchActivity.class);
                intent.putExtra("ingredientName", item.getIngredient_name());
                startActivity(intent);
                //Toast.makeText(getActivity().getApplicationContext(),"선택된 재료: " + item.getIngredient_name(),Toast.LENGTH_LONG).show();
            }
        });

        RecyclerView recyclerViewForCocktailHome = root.findViewById(R.id.recyclerViewForCocktail_home);
        LinearLayoutManager layoutManagerForCocktailHome = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerViewForCocktailHome.setLayoutManager(layoutManagerForCocktailHome);
        final CocktailAdapter adapterForCocktailHome = new CocktailAdapter();

        //수정필 테스트용
        for(int i=0; i<20; i++) {
            adapterForCocktailHome.addItem(new Cocktail("맛있는 칵테일 " + i, i, "맛있는 칵테일 " + i + "의 설명 정말 맛있다 맛있는 칵테일" + i +
                    "의 설명 정말 맛있다 맛있는 칵테일" + i +"의 설명 정말 맛있다 맛있는 칵테일" + i +"의 설명 정말 맛있다 맛있는 칵테일" + i +"의 설명 정말 맛있다 맛있는 칵테일" + i +
                    "의 설명 정말 맛있다 맛있는 칵테일" + i +"의 설명 정말 맛있다 맛있는 칵테일" + i +"의 설명 정말 맛있다 맛있는 칵테일" + i +"의 설명", i*10 + " %"));
        }
        //수정필 테스트용

        recyclerViewForCocktailHome.setAdapter(adapterForCocktailHome);

        adapterForCocktailHome.setOnItemClickListener(new OnCocktailItemClickListener() {
            @Override
            public void onItemClick(CocktailAdapter.ViewHolder holder, View view, int position) {
                Cocktail item = adapterForCocktailHome.getItem(position);
                //Toast.makeText(getActivity().getApplicationContext(),"선택된 칵테일: " + item.getName(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(view.getContext(), CocktailRecipeActivity.class);
                intent.putExtra("cocktailName", item.getName());
                startActivity(intent);
            }
        });

        return root;
    }
}
