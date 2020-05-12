package org.techtown.capstoneprojectcocktail;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();

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
            FirebaseStorage storage = FirebaseStorage.getInstance();

            StorageReference gsReference = storage.getReferenceFromUrl(item.getImageUrl());
            gsReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        // Glide 이용하여 이미지뷰에 로딩
                        Glide.with(itemView)
                                .load(task.getResult())
                                .into(imageForCocktail);
                    } else {
                        // URL을 가져오지 못하면 토스트 메세지
                    }
                }
            });
            textForCocktailName.setText(item.getName());
            textForCocktailDescription.setText(item.getDescription());
            textForCocktailABV.setText(item.getAbvNum());
            //Glide.with(itemView).load(imageUrl).into(imageForCocktail);
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
