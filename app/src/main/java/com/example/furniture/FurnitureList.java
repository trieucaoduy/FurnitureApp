package com.example.furniture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.furniture.Model.Furniture;
import com.example.furniture.Interface.ItemClickListener;
import com.example.furniture.ViewHolder.FurnitureViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FurnitureList extends AppCompatActivity {

    RecyclerView recyclerItem;
    RecyclerView.LayoutManager layoutManager;

    String categoryId = "";

    FirebaseRecyclerAdapter<Furniture, FurnitureViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference furnitureList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.furniturelist_content);

        database = FirebaseDatabase.getInstance();
        furnitureList = database.getReference("Furniture");

        //Load Furniture Item
        recyclerItem = (RecyclerView)findViewById(R.id.recycler_furniture);
        recyclerItem.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerItem.setLayoutManager(layoutManager);

        if(getIntent() != null)
            categoryId = getIntent().getStringExtra("CategoryId");
        if (!categoryId.isEmpty() && categoryId != null) {
            loadFurnitureList(categoryId);
        }
    }

    private void loadFurnitureList(String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Furniture, FurnitureViewHolder>(Furniture.class, R.layout.furniture_item, FurnitureViewHolder.class, furnitureList.orderByChild("MenuId").equalTo(categoryId)) {
            @Override
            protected void populateViewHolder(FurnitureViewHolder viewHolder, Furniture model, int i) {
                viewHolder.FurnitureName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.FurnitureImage);
                final Furniture local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intentDetail = new Intent(FurnitureList.this, FurnitureDetail.class);
                        intentDetail.putExtra("FurnitureId", adapter.getRef(position).getKey());
                        startActivity(intentDetail);
                    }
                });
            }
        };

        recyclerItem.setAdapter(adapter);
    }
}
