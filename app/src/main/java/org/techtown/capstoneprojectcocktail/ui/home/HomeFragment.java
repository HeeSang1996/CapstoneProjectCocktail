package org.techtown.capstoneprojectcocktail.ui.home;

import android.content.Intent;
import android.os.Bundle;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.techtown.capstoneprojectcocktail.Cocktail;
import org.techtown.capstoneprojectcocktail.CocktailAdapter;
import org.techtown.capstoneprojectcocktail.CocktailIngredient;
import org.techtown.capstoneprojectcocktail.CocktailIngredientAdapter;
import org.techtown.capstoneprojectcocktail.CocktailRecipeActivity;
import org.techtown.capstoneprojectcocktail.MainActivity;
import org.techtown.capstoneprojectcocktail.OnCocktailIngredientItemClickListener;
import org.techtown.capstoneprojectcocktail.OnCocktailItemClickListener;
import org.techtown.capstoneprojectcocktail.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        Button searchButtonHome = root.findViewById(R.id.button_search_home);
        searchButtonHome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Snackbar.make(view, "서치 기능이 들어갈 예정입니다", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        FloatingActionButton simulationFloatingButtonHome = root.findViewById(R.id.button_simulation_action);
        simulationFloatingButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "시뮬레이션 기능이 들어갈 예정입니다.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        RecyclerView recyclerViewForIngredientHome = root.findViewById(R.id.recyclerViewForIngredient);
        LinearLayoutManager layoutManagerForIngredientHome = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerViewForIngredientHome.setLayoutManager(layoutManagerForIngredientHome);
        final CocktailIngredientAdapter adapterForIngredientHome = new CocktailIngredientAdapter();

        adapterForIngredientHome.addItem(new CocktailIngredient("Rum0"));
        adapterForIngredientHome.addItem(new CocktailIngredient("Whisky0"));
        adapterForIngredientHome.addItem(new CocktailIngredient("Rum1"));
        adapterForIngredientHome.addItem(new CocktailIngredient("Whisky1"));
        adapterForIngredientHome.addItem(new CocktailIngredient("Rum2"));
        adapterForIngredientHome.addItem(new CocktailIngredient("Whisky2"));
        adapterForIngredientHome.addItem(new CocktailIngredient("Rum3"));
        adapterForIngredientHome.addItem(new CocktailIngredient("Whisky3"));
        adapterForIngredientHome.addItem(new CocktailIngredient("Rum4"));
        adapterForIngredientHome.addItem(new CocktailIngredient("Whisky4"));
        adapterForIngredientHome.addItem(new CocktailIngredient("Rum5"));
        adapterForIngredientHome.addItem(new CocktailIngredient("Whisky5"));
        adapterForIngredientHome.addItem(new CocktailIngredient("Rum6"));
        adapterForIngredientHome.addItem(new CocktailIngredient("Whisky6"));
        adapterForIngredientHome.addItem(new CocktailIngredient("Rum7"));
        adapterForIngredientHome.addItem(new CocktailIngredient("Whisky7"));

        recyclerViewForIngredientHome.setAdapter(adapterForIngredientHome);

        adapterForIngredientHome.setOnItemClickListener(new OnCocktailIngredientItemClickListener() {
            @Override
            public void onItemClick(CocktailIngredientAdapter.ViewHolder holder, View view, int position) {
                CocktailIngredient item = adapterForIngredientHome.getItem(position);
                Toast.makeText(getActivity().getApplicationContext(),"선택된 재료: " + item.getName(),Toast.LENGTH_LONG).show();
            }
        });

        RecyclerView recyclerViewForCocktailHome = root.findViewById(R.id.recyclerViewForCocktail_home);
        LinearLayoutManager layoutManagerForCocktailHome = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerViewForCocktailHome.setLayoutManager(layoutManagerForCocktailHome);
        final CocktailAdapter adapterForCocktailHome = new CocktailAdapter();

        adapterForCocktailHome.addItem(new Cocktail("맛있는 칵테일 1","맛있는 칵테일 1의 설명 정말 맛있다맛있는 칵테일 1의 설명 정말 맛있다맛있는 칵테일 1의 설명 정말 맛있다맛있는 칵테일 1의 설명 정말 맛있다맛있는 칵테일 1의 설명 정말 맛있다맛있는 칵테일 1의 설명 정말 맛있다맛있는 칵테일 1의 설명 정말 맛있다맛있는 칵테일 1의 설명 정말 맛있다맛있는 칵테일 1의 설명 정말 맛있다맛있는 칵테일 1의 설명 정말 맛있다맛있는 칵테일 1의 설명 정말 맛있다맛있는 칵테일 1의 설명 정말 맛있다맛있는 칵테일 1의 설명 정말 맛있다맛있는 칵테일 1의 설명 정말 맛있다맛있는 칵테일 1의 설명 정말 맛있다","10%"));
        adapterForCocktailHome.addItem(new Cocktail("맛있는 칵테일 2","맛있는 칵테일 2의 설명 정말 맛있다","10%"));
        adapterForCocktailHome.addItem(new Cocktail("맛있는 칵테일 3","맛있는 칵테일 3의 설명 정말 맛있다","10%"));
        adapterForCocktailHome.addItem(new Cocktail("맛있는 칵테일 4","맛있는 칵테일 4의 설명 정말 맛있다","10%"));
        adapterForCocktailHome.addItem(new Cocktail("맛있는 칵테일 5","맛있는 칵테일 5의 설명 정말 맛있다","10%"));
        adapterForCocktailHome.addItem(new Cocktail("맛있는 칵테일 6","맛있는 칵테일 6의 설명 정말 맛있다","10%"));
        adapterForCocktailHome.addItem(new Cocktail("맛있는 칵테일 7","맛있는 칵테일 7의 설명 정말 맛있다","10%"));
        adapterForCocktailHome.addItem(new Cocktail("맛있는 칵테일 8","맛있는 칵테일 8의 설명 정말 맛있다","10%"));
        adapterForCocktailHome.addItem(new Cocktail("맛있는 칵테일 9","맛있는 칵테일 9의 설명 정말 맛있다","10%"));
        adapterForCocktailHome.addItem(new Cocktail("맛있는 칵테일 10","맛있는 칵테일 10의 설명 정말 맛있다","10%"));
        adapterForCocktailHome.addItem(new Cocktail("맛있는 칵테일 11","맛있는 칵테일 11의 설명 정말 맛있다","10%"));
        adapterForCocktailHome.addItem(new Cocktail("맛있는 칵테일 12","맛있는 칵테일 12의 설명 정말 맛있다","10%"));
        adapterForCocktailHome.addItem(new Cocktail("맛있는 칵테일 13","맛있는 칵테일 13의 설명 정말 맛있다","10%"));
        adapterForCocktailHome.addItem(new Cocktail("맛있는 칵테일 14","맛있는 칵테일 14의 설명 정말 맛있다","10%"));
        adapterForCocktailHome.addItem(new Cocktail("맛있는 칵테일 15","맛있는 칵테일 15의 설명 정말 맛있다","10%"));
        adapterForCocktailHome.addItem(new Cocktail("맛있는 칵테일 16","맛있는 칵테일 16의 설명 정말 맛있다","10%"));
        adapterForCocktailHome.addItem(new Cocktail("맛있는 칵테일 17","맛있는 칵테일 17의 설명 정말 맛있다","10%"));
        adapterForCocktailHome.addItem(new Cocktail("맛있는 칵테일 18","맛있는 칵테일 18의 설명 정말 맛있다","10%"));
        adapterForCocktailHome.addItem(new Cocktail("맛있는 칵테일 19","맛있는 칵테일 19의 설명 정말 맛있다","10%"));
        adapterForCocktailHome.addItem(new Cocktail("맛있는 칵테일 20","맛있는 칵테일 20의 설명 정말 맛있다","10%"));

        recyclerViewForCocktailHome.setAdapter(adapterForCocktailHome);

        adapterForCocktailHome.setOnItemClickListener(new OnCocktailItemClickListener() {
            @Override
            public void onItemClick(CocktailAdapter.ViewHolder holder, View view, int position) {
                Cocktail item = adapterForCocktailHome.getItem(position);
                //Toast.makeText(getActivity().getApplicationContext(),"선택된 칵테일: " + item.getName(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(view.getContext(), CocktailRecipeActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}
