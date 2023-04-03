package com.example.android_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CustomViewHolder> {

    private ArrayList<Contents> arrayList;
    private Context context;
    public HashMap<Long, Boolean> itemStateArray = new HashMap<>();

    public MainAdapter(ArrayList<Contents> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public MainAdapter() {
    }

    @NonNull
    @Override
    public MainAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_list, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.CustomViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //리사이클러뷰 리스트에 표시할 항목
        Contents contents = arrayList.get(position);

        holder.tv_name.setText(contents.getName());

        if (arrayList.get(position).getCount() == 0) {
            holder.tv_count.setText(contents.getCount() + "개");
            holder.tv_count.setTextColor(Color.RED);
        } else {
            holder.tv_count.setText(contents.getCount() + "개");
            holder.tv_count.setTextColor(Color.BLACK);
        }

        switch (arrayList.get(position).getCategory()) {
            case "채소":
                holder.list_icon.setImageResource(R.drawable.ci_vegetable);
                break;
            case "과일":
                holder.list_icon.setImageResource(R.drawable.ci_fruits);
                break;
            case "육류":
                holder.list_icon.setImageResource(R.drawable.ci_meat);
                break;
            case "수산물":
                holder.list_icon.setImageResource(R.drawable.ci_fish);
                break;
            case "음료":
                holder.list_icon.setImageResource(R.drawable.ci_drinks);
                break;
            case "유제품":
                holder.list_icon.setImageResource(R.drawable.ci_dairy);
                break;
            case "인스턴트":
                holder.list_icon.setImageResource(R.drawable.ci_instant);
                break;
            case "기타":
                holder.list_icon.setImageResource(R.drawable.ci_etcetera);
                break;
        }

        //리스트 배경색
        if (contents.getInUseList() == 1) {
            contents.setBackgroundColor(Color.rgb(204, 255, 255));
        } else {
            contents.setBackgroundColor(Color.WHITE);
        }
        holder.itemView.setBackgroundColor(contents.getBackgroundColor());

        if (arrayList.get(position).getDday() < 0) {
            holder.tv_dday.setText("D+" + Math.abs(arrayList.get(position).getDday()));
            holder.tv_dday.setTextColor(Color.RED);
        } else if (arrayList.get(position).getDday() == 0) {
            holder.tv_dday.setText("D-Day");
            holder.tv_dday.setTextColor(Color.RED);
        } else if (arrayList.get(position).getDday() <= 3) {
            holder.tv_dday.setText("D-" + arrayList.get(position).getDday());
            holder.tv_dday.setTextColor(Color.BLUE);
        } else {
            holder.tv_dday.setText("D-" + arrayList.get(position).getDday());
            holder.tv_dday.setTextColor(Color.BLACK);
        }

//      선택삭제 +전체선택
        final int pos = position;
        holder.chk_item.setChecked(arrayList.get(position).isSelected());
        holder.chk_item.setTag(arrayList.get(position));
        holder.chk_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemStateArray.containsKey(contents.getId())) {
                    itemStateArray.remove(contents.getId());
                } else {
                    itemStateArray.put(contents.getId(),true);
                    CheckBox cb = (CheckBox) v;
                    Contents contents = (Contents) cb.getTag();
                    contents.setSelected(cb.isChecked());
                    arrayList.get(pos).setSelected(cb.isChecked());
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.main_dialog, null);
                final ImageView imageView = dialogView.findViewById(R.id.logo);
                final Button btnUseList = dialogView.findViewById(R.id.btnUseList);
                final Button btnBuyList = dialogView.findViewById(R.id.btnBuyList);
                final TextView dialogTv = dialogView.findViewById(R.id.dialog_tv_name);

                switch (arrayList.get(position).getCategory()) {
                    case "채소":
                        imageView.setImageResource(R.drawable.ci_vegetable);
                        break;
                    case "과일":
                        imageView.setImageResource(R.drawable.ci_fruits);
                        break;
                    case "육류":
                        imageView.setImageResource(R.drawable.ci_meat);
                        break;
                    case "수산물":
                        imageView.setImageResource(R.drawable.ci_fish);
                        break;
                    case "음료":
                        imageView.setImageResource(R.drawable.ci_drinks);
                        break;
                    case "유제품":
                        imageView.setImageResource(R.drawable.ci_dairy);
                        break;
                    case "인스턴트":
                        imageView.setImageResource(R.drawable.ci_instant);
                        break;
                    case "기타":
                        imageView.setImageResource(R.drawable.ci_etcetera);
                        break;
                }


                dialogTv.setText(arrayList.get(position).getName());
                AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                dlg.setView(dialogView);
                btnBuyList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id = String.valueOf(arrayList.get(position).getId());
                        FirebaseDatabase.getInstance().getReference().child("Contents").child(id).child("inBuyList").setValue(1);
                        Toast.makeText(context.getApplicationContext(), arrayList.get(position).getName() + " 장바구니 등록완료", Toast.LENGTH_SHORT).show();
                    }
                });
                btnUseList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id = String.valueOf(arrayList.get(position).getId());
                        FirebaseDatabase.getInstance().getReference().child("Contents").child(id).child("inUseList").setValue(1);
                        Toast.makeText(context.getApplicationContext(), arrayList.get(position).getName() + " 소비목록 등록완료", Toast.LENGTH_SHORT).show();
                    }
                });
                dlg.setNeutralButton("상세보기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, ContentsDetail.class)
                                //.putExtra("content", content); 콘텐츠형 콘텐트를 넘기고 싶은데 에러남.
                                // 멤버변수 각각을 인텐트로 전달.
                                .putExtra("id", arrayList.get(position).getId())
                                .putExtra("name", arrayList.get(position).getName())
                                .putExtra("count", String.valueOf(arrayList.get(position).getCount()))
                                .putExtra("expiration", arrayList.get(position).getExpiration())
                                .putExtra("category", arrayList.get(position).getCategory())
                                .putExtra("type", arrayList.get(position).getType())
                                .putExtra("dDay", arrayList.get(position).getDday())
                                .putExtra("inUseList", arrayList.get(position).getInUseList())
                                .putExtra("inBuyList", arrayList.get(position).getInBuyList());

                        v.getContext().startActivity(intent);
                    }
                });
                dlg.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String id = String.valueOf(arrayList.get(position).getId());
                        FirebaseDatabase.getInstance().getReference().child("Contents").child(id).removeValue();
                        // MainAcitivity로 강제이동 시켜서 새로고침 함. 더 좋은 방법이 있으면 ??
                        Intent intent = new Intent(context, MainActivity.class);
                        v.getContext().startActivity(intent);
                    }
                });
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Intent intent = new Intent(context, MainActivity.class);
//                        v.getContext().startActivity(intent);
                    }
                });
                dlg.show();

            }
        });
    }

    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy/MM/dd");

    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_count, tv_dday;
        CheckBox chk_item, chk_all;
        ImageView list_icon;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.chk_item = itemView.findViewById(R.id.list_cb);
            this.chk_all = itemView.findViewById(R.id.chk_all);
            this.tv_name = itemView.findViewById(R.id.tv_name);
            this.list_icon = itemView.findViewById(R.id.list_icon);
            this.tv_count = itemView.findViewById(R.id.tv_count);
            this.tv_dday = itemView.findViewById(R.id.tv_dday);
        }
    }
}