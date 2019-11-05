package com.example.furniture.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.furniture.Interface.ItemClickListener;
import com.example.furniture.Model.Furniture;
import com.example.furniture.R;

public class FurnitureViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView FurnitureName;
    public ImageView FurnitureImage;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public FurnitureViewHolder(@NonNull View itemView) {
        super(itemView);

        FurnitureName = (TextView)itemView.findViewById(R.id.furniture_name);
        FurnitureImage = (ImageView)itemView.findViewById(R.id.furniture_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
