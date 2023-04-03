package com.example.android_project;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class MainTab3 extends Fragment {
    RecyclerView recyclerView_tab3;
    MainAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Contents> arrayList;
    ArrayList<String> spinner_list;
    ArrayAdapter<String> arrayAdapter;
    CheckBox chk_all;
    Button btnUse, btnDelete;
    ImageView pic_no_result;
    TextView tv_no_result;


    public MainTab3() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.main_tab3, container, false);
        Context context = rootView.getContext();

        // 검색결과 없음 바인딩.
        pic_no_result = rootView.findViewById(R.id.pic_no_result);
        pic_no_result.setVisibility(View.INVISIBLE);
        tv_no_result = rootView.findViewById(R.id.tv_no_result);
        tv_no_result.setVisibility(View.INVISIBLE);

        recyclerView_tab3 = rootView.findViewById(R.id.recyclerView_tab3);
        recyclerView_tab3.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView_tab3.scrollToPosition(0);
        recyclerView_tab3.setLayoutManager(layoutManager);

        //체크박스 전체삭제
        chk_all = rootView.findViewById(R.id.chk_all);
        chk_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chk_all.isChecked()) {
                    for (Contents contents : arrayList) {
                        contents.setSelected(true);
                        adapter.itemStateArray.put(contents.getId(),true);
                    }
                } else {
                    for (Contents contents : arrayList) {
                        contents.setSelected(false);
                        adapter.itemStateArray.remove(contents.getId());
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });

        adapter = new MainAdapter();
        btnUse = rootView.findViewById(R.id.btnUse);
        btnUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.itemStateArray.isEmpty()) {
                    Toast.makeText(context, "추가할 항목을 선택하세요", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                    dlg.setTitle(adapter.itemStateArray.size()+"개 추가할까요?");
                    dlg.setPositiveButton("취소", null)
                            .setNegativeButton("추가", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    for (Map.Entry<Long, Boolean> entry : adapter.itemStateArray.entrySet()) {
                                        if (entry.getValue()) {
                                            FirebaseDatabase.getInstance().getReference().child("Contents")
                                                    .child(String.valueOf(entry.getKey())).child("inUseList").setValue(1);
                                        }
                                    }
                                    Intent intent = new Intent(requireContext(), UseListView.class);
                                    startActivity(intent);
//                                adapter.notifyDataSetChanged();

                                }
                            }).create().show();
                }
            }

        });
        btnDelete = rootView.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.itemStateArray.isEmpty()) {
                    Toast.makeText(context, "삭제할 항목을 선택하세요", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                    dlg.setTitle(adapter.itemStateArray.size()+"개 삭제할까요?");
                    dlg.setPositiveButton("취소", null)
                            .setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    for (Map.Entry<Long, Boolean> entry : adapter.itemStateArray.entrySet()) {
                                        if (entry.getValue()) {
                                            FirebaseDatabase.getInstance().getReference().child("Contents")
                                                    .child(String.valueOf(entry.getKey())).removeValue();
                                        }
                                    }
                                    Intent intent = new Intent(requireContext(), MainActivity.class);
                                    startActivity(intent);
//                                adapter.notifyDataSetChanged();

                                }
                            }).create().show();
                }
            }

        });

        arrayList = new ArrayList<>();

        //카테고리 스피너
        spinner_list = new ArrayList<>();
        spinner_list.add("전체");
        spinner_list.add("채소");
        spinner_list.add("과일");
        spinner_list.add("육류");
        spinner_list.add("수산물");
        spinner_list.add("음료");
        spinner_list.add("유제품");
        spinner_list.add("인스턴트");
        spinner_list.add("기타");

        arrayAdapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_item, spinner_list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner_tab3;
        spinner_tab3 = rootView.findViewById(R.id.spinner_tab3);
        spinner_tab3.setAdapter(arrayAdapter);
        spinner_tab3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) spinner_tab3.getSelectedItem();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Contents");
                ref.orderByChild("dday").addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        arrayList.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Contents contents = snapshot.getValue(Contents.class);
                            String type = contents.getType();
                            if (selected.equals("전체") && type.equals("냉동")) {
                                arrayList.add(contents);
                            } else if (contents.getCategory().equals(selected) && type.equals("냉동")) {
                                arrayList.add(contents);
                            }


                        }
                        if (arrayList.size()==0) {
                            pic_no_result.setVisibility(View.VISIBLE);
                            tv_no_result.setVisibility(View.VISIBLE);
                        } else {
                            pic_no_result.setVisibility(View.INVISIBLE);
                            tv_no_result.setVisibility(View.INVISIBLE);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("TAB2", String.valueOf(error.toException()));
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapter = new MainAdapter(arrayList, context);
        recyclerView_tab3.setAdapter(adapter);
        return rootView;
    }
}
