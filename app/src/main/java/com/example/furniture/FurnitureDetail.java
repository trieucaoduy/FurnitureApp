package com.example.furniture;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.furniture.Database.Database;
import com.example.furniture.Model.Furniture;
import com.example.furniture.Model.Order;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FurnitureDetail extends AppCompatActivity {

    TextView furniture_name, furniture_price, furniture_description;
    ImageView furniture_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton elegantNumberButton;

    String furnitureId="";

    FirebaseDatabase database;
    DatabaseReference furnitures;

    Furniture currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_furniture_detail);


        //Firebase
        database = FirebaseDatabase.getInstance();
        furnitures = database.getReference("Furniture");

        //init view
        elegantNumberButton = (ElegantNumberButton) findViewById(R.id.number_button);
        btnCart = (FloatingActionButton)findViewById(R.id.btn_Cart);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).addToCart(new Order(
                    furnitureId,
                        currentItem.getName(),
                        elegantNumberButton.getNumber(),
                        currentItem.getPrice(),
                        currentItem.getDiscount()
                ));


                Toast.makeText(FurnitureDetail.this, "Added To Cart", Toast.LENGTH_SHORT).show();
            }
        });

        furniture_name = (TextView)findViewById(R.id.furniture_name);
        furniture_price = (TextView)findViewById(R.id.furniture_price);
        furniture_description = (TextView)findViewById(R.id.furniture_description);
        furniture_image = (ImageView)findViewById(R.id.furniture_image);

        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapseAppbar);

        if(getIntent() != null)
            furnitureId = getIntent().getStringExtra("FurnitureId");
        if (!furnitureId.isEmpty()) {
            getDetailFurniture(furnitureId);
        }

    }

    private void getDetailFurniture(final String furnitureId) {
        furnitures.child(furnitureId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentItem =  dataSnapshot.getValue(Furniture.class);

                Picasso.with(getBaseContext()).load(currentItem.getImage()).into(furniture_image);

                collapsingToolbarLayout.setTitle(currentItem.getName());

                furniture_name.setText(currentItem.getName());
                furniture_price.setText(currentItem.getPrice());
                furniture_description.setText(currentItem.getDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
