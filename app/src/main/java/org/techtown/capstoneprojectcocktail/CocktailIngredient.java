package org.techtown.capstoneprojectcocktail;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentChange.Type;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Query.Direction;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Source;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CocktailIngredient {
    private String Ingredient_name;
    private String Ingredient_type;
    private float abv;
    private float sugar_rate;
    private float salty;
    private float bitter;
    private float sour;
    private String flavour;
    private float specific_gravity;
    private Map<String, Number> Ingredient_color;

    public CocktailIngredient(
            String Ingredient_name,
            String Ingredient_type,
            float abv,
            float sugar_rate,
            float salty,
            float bitter,
            float sour,
            String flavour,
            float specific_gravity,
            Map<String, Number> Ingredient_color) {

        this.Ingredient_name =Ingredient_name;
        this.Ingredient_type =Ingredient_type;
        this.abv =abv;
        this.sugar_rate =sugar_rate;
        this.salty =salty;
        this.bitter =bitter;
        this.sour =sour;
        this.flavour =flavour;
        this.specific_gravity =specific_gravity;
        this.Ingredient_color =Ingredient_color;
    }

    public String getIngredient_name() {
        return Ingredient_name;
    }

    public String getIngredient_type() {
        return Ingredient_type;
    }

    public float getAbv() {
        return abv;
    }

    public float getSugar_rate() {
        return sugar_rate;
    }

    public float getSalty() {
        return salty;
    }

    public float getBitter() {
        return bitter;
    }

    public float getSour() {
        return sour;
    }

    public String getFlavour() {
        return flavour;
    }

    public float getSpecific_gravity() {
        return specific_gravity;
    }

}


