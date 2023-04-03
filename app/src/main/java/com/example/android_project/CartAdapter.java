package com.example.android_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{







    // 인터페이스 만들어서 사용
    public interface OnLongItemClick{
        void  onLongItemClick(int pos);
    }

    private OnLongItemClick onLongItemClick;

    //setter
    public void setOnLongItemClick(OnLongItemClick onLongItemClick) {
        this.onLongItemClick = onLongItemClick;
    }








    private ArrayList<Contents> CartArrayList;

    private Context context;

    public CartAdapter(ArrayList<Contents> CartAdapter, Context context) {
        this.CartArrayList = CartAdapter;
        this.context = context;
    }

    public CartAdapter(ArrayList<Contents> CartAdapter) {
        this.CartArrayList = CartAdapter;
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        private TextView cartCategory, cartName,cartType,cartCount;
        private ImageView Cart_imgView;

        private ImageButton btnCartDel;

        private CheckBox Cart_chbox;


        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            cartCategory = (TextView) itemView.findViewById(R.id.tvCart_Category);
            cartName = (TextView) itemView.findViewById(R.id.tvCart_Name);
            cartType = (TextView) itemView.findViewById(R.id.tvCart_Type);
            Cart_imgView = (ImageView) itemView.findViewById(R.id.Cart_imgView);
            btnCartDel = (ImageButton) itemView.findViewById(R.id.Cart_btnCheck);

        }
    }


    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items, parent, false);
        CartViewHolder viewHolder = new CartViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Contents contents = CartArrayList.get(position);
        holder.cartCategory.setText(contents.getCategory());
        holder.cartType.setText(contents.getType());
        holder.cartName.setText(contents.getName());
        holder.Cart_imgView.setImageResource(R.drawable.ic_launcher_background);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });



        holder.btnCartDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Contents");

                ref.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Contents contents = snapshot.getValue(Contents.class);


                            Long ids =   CartArrayList.get(position).getId();

                            //   Long inUseList = Long.valueOf(0);
                            Long inBuyList = Long.valueOf(0);

                            contents.setInBuyList(inBuyList);


                            Map<String, Object> map = new HashMap<>();
                            map.put("inBuyList", 0);

                            ref.child(String.valueOf(ids)).updateChildren(map);


                            //    ref.child(String.valueOf(ids)).setValue(map);

//

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }

        });



        switch (contents.getCategory()) {
            case "채소":
                holder.Cart_imgView.setImageResource(R.drawable.ci_vegetable);
                break;
            case "과일":
                holder.Cart_imgView.setImageResource(R.drawable.ci_fruits);
                break;
            case "육류":
                holder.Cart_imgView.setImageResource(R.drawable.ci_meat);
                break;
            case "수산물":
                holder.Cart_imgView.setImageResource(R.drawable.ci_fish);
                break;
            case "음료":
                holder.Cart_imgView.setImageResource(R.drawable.ci_drinks);
                break;
            case "유제품":
                holder.Cart_imgView.setImageResource(R.drawable.ci_dairy);
                break;
            case "인스턴트":
                holder.Cart_imgView.setImageResource(R.drawable.ci_instant);
                break;
            case "기타":
                holder.Cart_imgView.setImageResource(R.drawable.ci_etcetera);
                break;
        }






    }

    @Override
    public int getItemCount() {
        return CartArrayList == null? 0 : CartArrayList.size();
    }
}
