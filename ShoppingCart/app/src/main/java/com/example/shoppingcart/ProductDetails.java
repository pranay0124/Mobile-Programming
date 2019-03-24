package com.example.shoppingcart;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.shoppingcart.Models.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ProductDetails extends AppCompatActivity {

    ImageView productImage;
    TextView productName, productPrice, productDescription;
    ElegantNumberButton numberBtn;
    Button addToCart, viewCart;

    String productID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productID = getIntent().getStringExtra("pid");

        productImage = (ImageView) findViewById(R.id.productDetails_image);
        productName = (TextView) findViewById(R.id.productDetails_name);
        productPrice = (TextView) findViewById(R.id.productDetails_price);
        productDescription = (TextView) findViewById(R.id.productDetails_description);
        numberBtn = (ElegantNumberButton) findViewById(R.id.productDetails_number_btn);
        addToCart = (Button) findViewById(R.id.productDetails_addToCart);
        viewCart = (Button) findViewById(R.id.productDetails_viewCart);
        
        getProductDetails(productID);

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingToCartList();
            }
        });

        viewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetails.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addingToCartList() {
        DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pid", productID);
        cartMap.put("pname", productName.getText().toString());
        cartMap.put("price", productPrice.getText().toString());
        cartMap.put("quantity", numberBtn.getNumber());

        cartListRef.child("Products").child(productID)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ProductDetails.this, "added to cart", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void getProductDetails(String productID) {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("ProductCollection");

        productsRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Products products = dataSnapshot.getValue(Products.class);
                    productName.setText(products.getName());
                    productPrice.setText(products.getPrice().toString());
                    productDescription.setText("Product Description: " + products.getDescription());
                    Picasso.with(ProductDetails.this).load("http://msitmp.herokuapp.com"+products.getProductPicUrl()).into(productImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
