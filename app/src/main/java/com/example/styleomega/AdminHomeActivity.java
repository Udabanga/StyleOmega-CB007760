package com.example.styleomega;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class AdminHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        drawerLayout = findViewById(R.id.drawerAdmin);
        toolbar = findViewById(R.id.toolbarAdmin);
        navigationView = findViewById(R.id.navigationViewAdmin);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawerOpen,R.string.drawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.viewCart:
                Toast.makeText(AdminHomeActivity.this,"View Cart", Toast.LENGTH_LONG).show();
                break;
            case R.id.viewProfile:
                Toast.makeText(AdminHomeActivity.this,"View Profile", Toast.LENGTH_LONG).show();
                break;
            case R.id.logOut:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(AdminHomeActivity.this,"Logout", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AdminHomeActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return false;
    }
}
