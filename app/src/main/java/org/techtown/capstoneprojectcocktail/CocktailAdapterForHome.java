package org.techtown.capstoneprojectcocktail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CocktailAdapterForHome extends RecyclerView.Adapter<CocktailAdapterForHome.ViewHolder> implements OnCocktailItemClickListenerForHome {
    ArrayList<Cocktail> items = new ArrayList<Cocktail>();
    OnCocktailItemClickListenerForHome listener;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textForCocktailName;
        TextView textForCocktailDescription;
        TextView textForCocktailABV;

        public ViewHolder (View itemView, final OnCocktailItemClickListenerForHome listener){
            super(itemView);

            textForCocktailName = itemView.findViewById(R.id.textView_cocktailName_home);
            textForCocktailDescription = itemView.findViewById(R.id.textView_cocktailDescription_home);
            textForCocktailABV = itemView.findViewById(R.id.textView_cocktailABV_home);

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

        public void setItem(Cocktail item) {
            textForCocktailName.setText(item.getName());
            textForCocktailDescription.setText(item.getDescription());
            textForCocktailABV.setText(item.getAbvNum());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.cocktail_cardview_horizontal, viewGroup, false);

        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Cocktail item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Cocktail item){
        items.add(item);
    }

    public void setItems(ArrayList<Cocktail> items){
        this.items = items;
    }

    public Cocktail getItem(int position){
        return items.get(position);
    }

    public void setItems(int position, Cocktail item){
        items.set(position, item);
    }

    public void setOnItemClickListener(OnCocktailItemClickListenerForHome listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder,View view,int position){
        if(listener!=null){
            listener.onItemClick(holder,view,position);
        }
    }
}
