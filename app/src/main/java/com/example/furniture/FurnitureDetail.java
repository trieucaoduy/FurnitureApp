package com.example.furniture;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.furniture.Model.Furniture;
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
                Furniture furnitureItem =  dataSnapshot.getValue(Furniture.class);

                Picasso.with(getBaseContext()).load(furnitureItem.getImage()).into(furniture_image);

                collapsingToolbarLayout.setTitle(furnitureItem.getName());

                furniture_name.setText(furnitureItem.getName());
                furniture_price.setText(furnitureItem.getPrice());
                furniture_description.setText(furnitureItem.getDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
