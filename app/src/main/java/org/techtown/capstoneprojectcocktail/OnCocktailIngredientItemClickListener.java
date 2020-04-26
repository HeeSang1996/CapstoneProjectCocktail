package org.techtown.capstoneprojectcocktail;

import android.view.View;

import org.techtown.capstoneprojectcocktail.CocktailIngredientAdapter;

public interface OnCocktailIngredientItemClickListener {
    public void onItemClick(CocktailIngredientAdapter.ViewHolder holder, View view, int position);
}
