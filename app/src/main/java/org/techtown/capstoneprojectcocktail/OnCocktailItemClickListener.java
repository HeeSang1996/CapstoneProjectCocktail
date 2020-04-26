package org.techtown.capstoneprojectcocktail;

import android.view.View;

public interface OnCocktailItemClickListener {
    public void onItemClick(CocktailAdapter.ViewHolder holder, View view, int position);
}
