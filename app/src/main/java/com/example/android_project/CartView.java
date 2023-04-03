package com.example.android_project;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

public class CartView extends AppCompatActivity {

    private RecyclerView recyclerView;
    private com.example.android_project.CartAdapter CartAdapter;

    LinearLayoutManager linearLayoutManager;

    ArrayList<Contents> CartArrayList;

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    ImageView pic_no_result;
    TextView tv_no_result;


    TextView textView;

    //데이터를 배열에 넣어서 준비
    String[] items = {"채소", "육류", "과일", "수산물", "음료", "유제품", "인스턴트", "기타"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_list);


        // Toolbar안에 들어가는 컨텐츠 입력
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 검색결과 없음 바인딩.
        pic_no_result = findViewById(R.id.pic_no_result);
        pic_no_result.setVisibility(View.INVISIBLE);
        tv_no_result = findViewById(R.id.tv_no_result);
        tv_no_result.setVisibility(View.INVISIBLE);

        Context context = getApplicationContext();

        // 리사이클러 뷰

        recyclerView = findViewById(R.id.Cart_recyclerView);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        CartArrayList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Contents");

        Query orderInBuyList = databaseReference.orderByChild("inBuyList").equalTo(1);

        orderInBuyList.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                CartArrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Contents contents = snapshot.getValue(Contents.class);
                    CartArrayList.add(contents);


                }
                if (CartArrayList.size() == 0) {
                    pic_no_result.setVisibility(View.VISIBLE);
                    tv_no_result.setVisibility(View.VISIBLE);
                } else {
                    pic_no_result.setVisibility(View.INVISIBLE);
                    tv_no_result.setVisibility(View.INVISIBLE);
                }
                CartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        CartAdapter = new CartAdapter(CartArrayList, context);
        recyclerView.setAdapter(CartAdapter);

        Button btnOrder = (Button) findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q="+""+"&hl=ko"));
                startActivity(intent);
            }
        });
        // ATTENTION: This was auto-generated to handle app links.

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
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
