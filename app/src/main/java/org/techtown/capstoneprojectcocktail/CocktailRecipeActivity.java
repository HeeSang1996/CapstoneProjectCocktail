package org.techtown.capstoneprojectcocktail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class CocktailRecipeActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.cocktail_recipe_activity);

        Intent intent = getIntent();

        TextView textForCocktailName = (TextView) findViewById(R.id.textView_cocktailName_recipe);
        TextView textForCocktailDescription = (TextView) findViewById(R.id.textView_cocktailDescription_recipe);
        TextView textForCocktailIngredient = (TextView) findViewById(R.id.textView_cocktailIngredient_recipe);
        final ImageView imageForCocktail = (ImageView) findViewById(R.id.imageView_cocktail_recipe);

        String cocktailName = intent.getExtras().getString("cocktailName");
        String cocktailDescription = intent.getExtras().getString("cocktailDescription");
        String cocktailIngredient = intent.getExtras().getString("cocktailIngredient");
        String cocktailRef = intent.getExtras().getString("cocktailRef");

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference gsReference = storage.getReferenceFromUrl(cocktailRef);

        gsReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    // Glide 이용하여 이미지뷰에 로딩
                    Glide.with(CocktailRecipeActivity.this)
                            .load(task.getResult())
                            .into(imageForCocktail);
                } else {
                    // URL을 가져오지 못하면 토스트 메세지
                }
            }
        });

        textForCocktailName.setText(cocktailName);
        textForCocktailDescription.setText(cocktailDescription);
        textForCocktailIngredient.setText(cocktailIngredient);
    }
}
