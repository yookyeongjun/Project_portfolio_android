package com.example.android_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class UseListView extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UseListAdapter ContentUseAdapter;

    private Button btnUse;

    Set<Long> selectedIDs = new HashSet<>();

    LinearLayoutManager linearLayoutManager;

    ArrayList<Contents> CUseArrayList;

    public HashMap<Long, Boolean> CUseStateArray = new HashMap<>();

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    ImageView pic_no_result;
    TextView tv_no_result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.use_list);


        Context context = getApplicationContext();

        // Toolbar안에 들어가는 컨텐츠 입력
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 검색결과 없음 바인딩.
        pic_no_result = findViewById(R.id.pic_no_result);
        pic_no_result.setVisibility(View.INVISIBLE);
        tv_no_result = findViewById(R.id.tv_no_result);
        tv_no_result.setVisibility(View.INVISIBLE);


        btnUse = (Button) findViewById(R.id.btnUse);

        // 리사이클러 뷰

        recyclerView = findViewById(R.id.Content_recyclerView);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        CUseArrayList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Contents");

        Query orderInUseList = databaseReference.orderByChild("inUseList").equalTo(1);



//        orderInBuyList.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                CUseArrayList.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    Contents contents = dataSnapshot.getValue(Contents.class);
//                    CUseArrayList.add(contents);
//
//                    ContentUseAdapter.notifyDataSetChanged();
//                }
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        orderInUseList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ContentUseAdapter.notifyDataSetChanged();

                CUseArrayList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Contents contents = dataSnapshot.getValue(Contents.class);
                    CUseArrayList.add(contents);


                }
                if (CUseArrayList.size() == 0) {
                    pic_no_result.setVisibility(View.VISIBLE);
                    tv_no_result.setVisibility(View.VISIBLE);
                } else {
                    pic_no_result.setVisibility(View.INVISIBLE);
                    tv_no_result.setVisibility(View.INVISIBLE);
                }
                ContentUseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        ContentUseAdapter = new UseListAdapter(CUseArrayList, context);
        recyclerView.setAdapter(ContentUseAdapter);

        btnUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContentUseAdapter.selectedIDs.isEmpty()){
                    Toast.makeText(context, "체크해주세요", Toast.LENGTH_SHORT).show();
                }else {
                    ContentUseAdapter.cUseSelected();
                }

            }
        });




    }

    // 메뉴바
    @Override
    public boolean onCreateOptionsMenu(Menu menu)    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // 메뉴바 이벤트
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.menu_home:
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_btn1:
                // Toast.makeText(this, "카트", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(getApplicationContext(), CartView.class);
                startActivity(intent2);
                return true;
            case R.id.action_btn2:
                // Toast.makeText(this, "카트", Toast.LENGTH_SHORT).show();
                Intent intent3 = new Intent(getApplicationContext(), RecipeView.class);
                startActivity(intent3);
                return true;

            case R.id.action_btn3:
                // Toast.makeText(this, "장바구니", Toast.LENGTH_SHORT).show();
                Intent intent4 = new Intent(getApplicationContext(), UseListView.class);
                startActivity(intent4);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
