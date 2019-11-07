package com.example.furniture;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.furniture.Interface.ItemClickListener;
import com.example.furniture.Model.Category;
import com.example.furniture.ViewHolder.CategoryViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class CategoryFragment extends Fragment {
    FirebaseDatabase database;
    DatabaseReference category;
    RecyclerView recycler_category;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Category, CategoryViewHolder> adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.category_content, container, false);

        database = FirebaseDatabase.getInstance();
        category = database.getReference("Category");

        recycler_category = rootView.findViewById(R.id.recycler_category);
        recycler_category.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recycler_category.setLayoutManager(layoutManager);

        loadCategory();
        return  rootView;
    }

    private void loadCategory() {

        adapter = new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(Category.class,R.layout.category_item,CategoryViewHolder.class,category) {
            @Override
            protected void populateViewHolder(CategoryViewHolder viewHolder, Category model, int i) {
                viewHolder.txtCategoryName.setText(model.getNAME());
                Picasso.with(getContext()).load(model.getIMAGE()).into(viewHolder.imageView);
                final Category clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //get CategoryId and send to new Activity
                        Intent furnitureList = new Intent(getActivity(), FurnitureList.class);
                        furnitureList.putExtra("CategoryId", adapter.getRef(position).getKey());
                        startActivity(furnitureList);
                    }
                });
            }
        };
        recycler_category.setAdapter(adapter);
    }
}
