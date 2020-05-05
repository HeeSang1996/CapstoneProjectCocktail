package org.techtown.capstoneprojectcocktail;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class CocktailIngredientList {
    private String Ingredient_num;
    private CocktailIngredient Ingredient_List[];

    FirebaseFirestore db;
    CollectionReference Ingredients = db.collection("Ingredient");
    DocumentReference docRef = db.collection("Ingredient").document(Ingredient_num);

    public CocktailIngredientList(
            String Ingredient_num,
            final CocktailIngredient Ingredient_List[]) {

        /*
        db.collection("Ingredient").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                if (task.isSuccessful())
                {
                    for (QueryDocumentSnapshot document : task.getResult())
                    {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                    }
                }
                else
                    Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });
        */
        /*
        for(int i = 0; i < 128; i++)
        {
            final int finalI = i;
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    //documentSnapshot.toObject(CocktailIngredient.class);
                    Ingredient_List[finalI] = documentSnapshot.toObject(CocktailIngredient.class);
                }
            });
        }
        */
    }

    public String getIngredient_num() {
        return Ingredient_num;
    }

    public CocktailIngredient getIngredient_List(int num) {
        return Ingredient_List[num];
    }

    public void getLog(){
        db.collection("Ingredient").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                if (task.isSuccessful())
                {
                    for (QueryDocumentSnapshot document : task.getResult())
                    {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                    }
                }
                else
                    Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });
    }
}