package com.example.styleomega;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ItemActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView title,price,brand,department,category,subcategory;
    private Spinner inputQuantitySpinner,inputSizeSpinner;
    private Button addButton;
    private String itemImage,itemTitle,itemPrice,itemBrand,itemDepartment,itemCategory,itemSubCategory,size,quantity,itemID;
    private boolean exist;

    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore= FirebaseFirestore.getInstance();
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();


    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_item_details);

        imageView=(ImageView) findViewById(R.id.itemImage);
        title=(TextView) findViewById(R.id.titleText);
        price=(TextView) findViewById(R.id.priceText);
        brand=(TextView) findViewById(R.id.brandText);
        department=(TextView) findViewById(R.id.departmentText);
        category=(TextView) findViewById(R.id.categoryText);
        subcategory=(TextView) findViewById(R.id.subCategoryText);
        inputQuantitySpinner=(Spinner) findViewById(R.id.quantitySpinner);
        inputSizeSpinner = (Spinner) findViewById(R.id.sizeSpinner);
        addButton = (Button) findViewById(R.id.addButton);


        ArrayAdapter<String> QuantityAdapter = new ArrayAdapter<String>(ItemActivity.this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.quantity));
        QuantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputQuantitySpinner.setAdapter(QuantityAdapter);


        itemImage=getIntent().getStringExtra("Image");
        itemTitle=getIntent().getStringExtra("Title");
        itemPrice=getIntent().getStringExtra("Price");
        itemDepartment=getIntent().getStringExtra("Department");
        itemCategory=getIntent().getStringExtra("Category");
        itemSubCategory=getIntent().getStringExtra("SubCategory");
        itemBrand=getIntent().getStringExtra("Brand");

        itemID=getIntent().getStringExtra("ID");
        if(itemID == null){
            exist=false;
        }
        else{
            exist=true;
        }

        if(itemCategory.equals("Footwear")){
            ArrayAdapter<String> SizeAdapter = new ArrayAdapter<String>(ItemActivity.this,
                    android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sizeShoe));
            SizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            inputSizeSpinner.setAdapter(SizeAdapter);
        }
        else if(itemSubCategory.equals("Jeans") || itemSubCategory.equals("Shorts")){
            ArrayAdapter<String> SizeAdapter = new ArrayAdapter<String>(ItemActivity.this,
                    android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sizeNumber));
            SizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            inputSizeSpinner.setAdapter(SizeAdapter);
        }
        else if(itemSubCategory.equals("Dress") || itemSubCategory.equals("T-Shirt") || itemSubCategory.equals("Shirt")){
            ArrayAdapter<String> SizeAdapter = new ArrayAdapter<String>(ItemActivity.this,
                    android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sizeLetter));
            SizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            inputSizeSpinner.setAdapter(SizeAdapter);
        }

        Picasso.get().load(itemImage).into(imageView);
        title.setText(itemTitle);
        price.setText(itemPrice);
        brand.setText(itemBrand);
        department.setText(itemDepartment);
        category.setText(itemCategory);
        subcategory.setText(itemSubCategory);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity=inputQuantitySpinner.getSelectedItem().toString();
                size=inputSizeSpinner.getSelectedItem().toString();

                DatabaseReference cartListReference = FirebaseDatabase.getInstance().getReference().child("Cart");
                Map<String, Object> cart = new HashMap<>();
                cart.put("department", itemDepartment);
                cart.put("category", itemCategory);
                cart.put("subCategory", itemSubCategory);
                cart.put("brand", itemBrand);
                cart.put("name", itemTitle);
                cart.put("image", itemImage);
                cart.put("price", itemPrice);
                cart.put("quantity", quantity);
                cart.put("size",size);
                if(exist){
                    cart.put("id", itemID);
                    cartListReference.child(currentUser.getUid()).child(itemID).updateChildren(cart);
                }else {
                    cart.put("id", "ITM" + uniqueIDgen());
                    cartListReference.child(currentUser.getUid()).child("ITM"+uniqueIDgen()).updateChildren(cart);
                }
                finish();
            }
        });
    }
    public String uniqueIDgen(){
        Calendar calendar= Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("MMDDyyyy");
        SimpleDateFormat currentTime=new SimpleDateFormat("HHmmss");

        return currentDate.format(calendar.getTime())+currentTime.format(calendar.getTime());
    }

}
