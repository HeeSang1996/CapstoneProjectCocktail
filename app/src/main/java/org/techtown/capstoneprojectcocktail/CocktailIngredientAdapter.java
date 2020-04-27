package org.techtown.capstoneprojectcocktail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CocktailIngredientAdapter extends RecyclerView.Adapter<CocktailIngredientAdapter.ViewHolder> implements OnCocktailIngredientItemClickListener {
    ArrayList<CocktailIngredient> items = new ArrayList<CocktailIngredient>();
    OnCocktailIngredientItemClickListener listener;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textForIngredient;

        public ViewHolder (View itemView, final OnCocktailIngredientItemClickListener listener){
            super(itemView);

            textForIngredient = itemView.findViewById(R.id.text_ingredient_home);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });
        }

        public void setItem(CocktailIngredient item) {
            textForIngredient.setText(item.getIngredient_name());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.cocktail_ingredient_button_home, viewGroup, false);

        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        CocktailIngredient item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(CocktailIngredient item){
        items.add(item);
    }

    public void setItems(ArrayList<CocktailIngredient> items){
        this.items = items;
    }

    public CocktailIngredient getItem(int position){
        return items.get(position);
    }

    public void setItems(int position, CocktailIngredient item){
        items.set(position, item);
    }

    public void setOnItemClickListener(OnCocktailIngredientItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder,View view,int position){
        if(listener!=null){
            listener.onItemClick(holder,view,position);
        }
    }
}
