package com.example.android_project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class RecipeView extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Recipe> recipeArrayList;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    ImageButton btnYoutube;
    ImageButton btnSearch;

    EditText edt_keyword;
    ImageView pic_no_result;
    TextView tv_no_result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list);


        // Toolbar안에 들어가는 컨텐츠 입력
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 검색결과 없음 바인딩.
        pic_no_result = findViewById(R.id.pic_no_result);
        tv_no_result = findViewById(R.id.tv_no_result);

        // detail 화면에서 키워드 물고 레시피로 넘어옴
        Intent intent = getIntent();
        edt_keyword = (EditText) findViewById(R.id.edt_keyword);
        edt_keyword.setText(intent.getStringExtra("keyword"));
        String keyword = edt_keyword.getText().toString();
        // youtube 버튼
        btnYoutube = (ImageButton) findViewById(R.id.youtubeButton);
        btnYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.youtube.com/results?search_query="+edt_keyword.getText()+" 레시피"));

                startActivity( intent );
            }
        });

        recyclerView = findViewById(R.id.recipeRecyclerView);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        recipeArrayList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Recipe");

//        Query recipeWithKeyword = databaseReference.orderByChild("RCP_PARTS_DTLS").startAt(keyword).endAt(keyword+"\uf8ff");
//        recipeWithKeyword.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                recipeArrayList.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    Recipe recipe = dataSnapshot.getValue(Recipe.class);
//                    recipeArrayList.add(recipe);
//
//                }
//                if (recipeArrayList.size() == 0) {
//                    pic_no_result.setVisibility(View.VISIBLE);
//                    tv_no_result.setVisibility(View.VISIBLE);
//                } else {
//                    pic_no_result.setVisibility(View.INVISIBLE);
//                    tv_no_result.setVisibility(View.INVISIBLE);
//                }
//                recipeAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        btnSearch = (ImageButton) findViewById(R.id.recipeSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchKeyword = edt_keyword.getText().toString();
                Query searchDB = databaseReference.orderByChild("RCP_PARTS_DTLS").startAt(searchKeyword).endAt(searchKeyword+"\uf8ff");
                searchDB.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        recipeArrayList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Recipe recipe = dataSnapshot.getValue(Recipe.class);
                            recipeArrayList.add(recipe);


                        }
                        if (recipeArrayList.size() == 0) {
                            Log.d("search", "0");
                            pic_no_result.setVisibility(View.VISIBLE);
                            tv_no_result.setVisibility(View.VISIBLE);
                        } else {
                            Log.d("search", "1");
                            pic_no_result.setVisibility(View.INVISIBLE);
                            tv_no_result.setVisibility(View.INVISIBLE);
                        }
                        recipeAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        recipeAdapter = new RecipeAdapter(recipeArrayList);
        recyclerView.setAdapter(recipeAdapter);
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
