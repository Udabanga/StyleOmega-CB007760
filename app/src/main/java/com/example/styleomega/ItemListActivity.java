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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ItemListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;

    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private CollectionReference itemRef = fStore.collection("Item");

    private ItemAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);

        navigationView = findViewById(R.id.navigationViewHome);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawerOpen,R.string.drawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        setUpRecyclerView();

        adapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Item item = documentSnapshot.toObject(Item.class);
                String id = documentSnapshot.getId();
                //Toast.makeText(ItemListActivity.this,id, Toast.LENGTH_LONG).show();
                item.getName();
                Intent intent = new Intent(ItemListActivity.this,ItemActivity.class);
                intent.putExtra("Image", item.getImage());
                intent.putExtra("Title",item.getName());
                intent.putExtra("Price",item.getPrice());
                intent.putExtra("Brand",item.getBrand());
                intent.putExtra("Department",item.getDepartment());
                intent.putExtra("Category",item.getCategory());
                intent.putExtra("SubCategory",item.getSubCategory());
                startActivity(intent);
            }
        });
    }



    private void setUpRecyclerView(){

        String Search = getIntent().getStringExtra("Search");
            Query query = itemRef.whereEqualTo("department",getIntent().getStringExtra("DepartmentSelected"))
                    .whereEqualTo("category",getIntent().getStringExtra("CategorySelected"))
                    .orderBy("name",Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Item> options=new FirestoreRecyclerOptions.Builder<Item>()
                .setQuery(query,Item.class)
                .build();
        adapter = new ItemAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_cart);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }



    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }

    protected void onStop(){
        super.onStop();
        adapter.stopListening();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.viewCart:
                Toast.makeText(ItemListActivity.this,"View Cart", Toast.LENGTH_LONG).show();
                break;
            case R.id.viewProfile:
                Toast.makeText(ItemListActivity.this,"View Profile", Toast.LENGTH_LONG).show();
                break;
            case R.id.logOut:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(ItemListActivity.this,"Logout", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ItemListActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return false;
    }

}
