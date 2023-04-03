package com.example.android_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {

    private ArrayList<Recipe> recipeArrayList;

    public RecipeAdapter(ArrayList<Recipe> recipeArrayList) {
        this.recipeArrayList = recipeArrayList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView recipeName, recipeDescription;
        private ImageView recipeImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeName = (TextView) itemView.findViewById(R.id.recipeName);
            recipeDescription = (TextView) itemView.findViewById(R.id.recipeDescription);
            recipeImage = itemView.findViewById(R.id.recipeImage);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_items, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Recipe recipe = recipeArrayList.get(position);
        holder.recipeName.setText(recipe.getRCP_NM());
        holder.recipeDescription.setText(recipe.getRCP_WAY2());

        // image url로 호출 Glide 라이브러리 사용
        String imageUrl = recipe.getATT_FILE_NO_MAIN();
        Glide.with(holder.itemView).load(imageUrl).into(holder.recipeImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RecipeDetail.class)
                        .putExtra("name", recipeArrayList.get(position).getRCP_NM())
                        .putExtra("manual01", recipeArrayList.get(position).getMANUAL01())
                        .putExtra("manual02", recipeArrayList.get(position).getMANUAL02())
                        .putExtra("manual03", recipeArrayList.get(position).getMANUAL03())
                        .putExtra("manual04", recipeArrayList.get(position).getMANUAL04())
                        .putExtra("manual05", recipeArrayList.get(position).getMANUAL05())
                        .putExtra("manual06", recipeArrayList.get(position).getMANUAL06())
                        .putExtra("manual07", recipeArrayList.get(position).getMANUAL07())
                        .putExtra("manual08", recipeArrayList.get(position).getMANUAL08())
                        .putExtra("manual09", recipeArrayList.get(position).getMANUAL09())
                        .putExtra("manual10", recipeArrayList.get(position).getMANUAL10())
                        .putExtra("manual11", recipeArrayList.get(position).getMANUAL11())
                        .putExtra("manual12", recipeArrayList.get(position).getMANUAL12())
                        .putExtra("manual13", recipeArrayList.get(position).getMANUAL13())
                        .putExtra("manual14", recipeArrayList.get(position).getMANUAL14())
                        .putExtra("manual15", recipeArrayList.get(position).getMANUAL15())
                        .putExtra("manual16", recipeArrayList.get(position).getMANUAL16())
                        .putExtra("manual17", recipeArrayList.get(position).getMANUAL17())
                        .putExtra("manual18", recipeArrayList.get(position).getMANUAL18())
                        .putExtra("manual19", recipeArrayList.get(position).getMANUAL19())
                        .putExtra("manual20", recipeArrayList.get(position).getMANUAL20())
                        .putExtra("materials", recipeArrayList.get(position).getRCP_PARTS_DTLS())
                        .putExtra("imageUrl", recipeArrayList.get(position).getATT_FILE_NO_MAIN());

                v.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return recipeArrayList == null? 0 : recipeArrayList.size();
    }
}
