package org.techtown.capstoneprojectcocktail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class CocktailAdapterForSearch extends RecyclerView.Adapter<CocktailAdapterForSearch.ViewHolder> implements OnCocktailItemClickListenerForSearch{
    ArrayList<Cocktail> items = new ArrayList<Cocktail>();
    OnCocktailItemClickListenerForSearch listener;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textForCocktailName;
        TextView textForCocktailDescription;
        TextView textForCocktailABV;
        ImageView imageForCocktail;

        public ViewHolder (View itemView, final OnCocktailItemClickListenerForSearch listener){
            super(itemView);

            textForCocktailName = itemView.findViewById(R.id.textView_cocktailName_search);
            textForCocktailDescription = itemView.findViewById(R.id.textView_cocktailDescription_search);
            textForCocktailABV = itemView.findViewById(R.id.textView_cocktailABV_search);
            imageForCocktail = itemView.findViewById(R.id.imageView_cocktail_search);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(CocktailAdapterForSearch.ViewHolder.this, view, position);
                    }
                }
            });
        }

        public void setItem(Cocktail item) {
            String imageUrl = item.getImageUrl();
            textForCocktailName.setText(item.getName());
            textForCocktailDescription.setText(item.getDescription());
            textForCocktailABV.setText(item.getAbvNum());
            imageForCocktail.setImageResource(R.mipmap.ic_launcher_round);
        }
    }

    @NonNull
    @Override
    public CocktailAdapterForSearch.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.cocktail_cardview_vertical, viewGroup, false);

        return new CocktailAdapterForSearch.ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CocktailAdapterForSearch.ViewHolder viewHolder, int position) {
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

    public void setOnItemClickListener(OnCocktailItemClickListenerForSearch listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(CocktailAdapterForSearch.ViewHolder holder, View view, int position){
        if(listener!=null){
            listener.onItemClick(holder,view,position);
        }
    }


    //수정필 필터
    /*
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        items.clear();
        if (charText.length() == 0) {
            potionList.addAll(arrayList);
        } else {
            for (Potion potion : arrayList) {
                String name = context.getResources().getString(potion.name);
                if (name.toLowerCase().contains(charText)) {
                    potionList.add(potion);
                }
            }
        }
        notifyDataSetChanged();
    }
     */
    //수정필 필터
}
