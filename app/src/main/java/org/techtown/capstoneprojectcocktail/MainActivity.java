package org.techtown.capstoneprojectcocktail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int REQUEST_PROFILE = 264;
    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseAuth mAuth;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    NavController navController;
    public FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_myPage).setDrawerLayout(drawerLayout).build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }

        //실험용
        //수정필
        //네비게이션바 이름변경, Sign in, Sign out check
        /*
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.userNameText_nav);
        navUsername.setText("Your Text Here");
         */

        //Menu menuView = navigationView.getMenu();
        //MenuItem logoutItem = menuView.findItem(R.id.nav_signInCheck);
        //logoutItem.setVisible(false);
        //navigationView.getMenu().findItem(R.id.nav_signInString).setVisible(false);
        //setup();
    }

    @Override
    protected void onResume(){
        super.onResume();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            navigationView.getMenu().findItem(R.id.nav_signInString).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_signOutString).setVisible(true);
            Toast.makeText(this,"on resume 콜 로그인",Toast.LENGTH_LONG).show();
        }
        else{
            navigationView.getMenu().findItem(R.id.nav_signInString).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_signOutString).setVisible(false);
            Toast.makeText(this,"on resume 콜 로그아웃",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.nav_home:
            case R.id.nav_myPage:
                NavigationUI.onNavDestinationSelected(menuItem,navController);
                break;
            case R.id.nav_signInString:
                Intent intent = new Intent(this, LoginPageActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_signOutString:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }
}
