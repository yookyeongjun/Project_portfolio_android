package com.example.android_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ContentsDetail extends AppCompatActivity {

    TextView tvName, tvCnt, tvExp, tvType_1, tvType_2, tvDday;
    ImageView ivPic;
    Button btnEdit, btnRecipe, btnBuyList;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contents_detail);


        tvName = findViewById(R.id.tvName);
        tvCnt = findViewById(R.id.tvCount);
        tvExp = findViewById(R.id.tvExDay);
        tvType_1 = findViewById(R.id.tvType_1);
        tvType_2 = findViewById(R.id.tvType_2);
        tvDday = findViewById(R.id.tvDday);
        ivPic = findViewById(R.id.ivPic);
        btnEdit = findViewById(R.id.btnEdit);
        btnRecipe = findViewById(R.id.btnRecipe);
        btnBuyList = findViewById(R.id.btnBuyList);



        // 전달받은 인텐트.(메인어댑터가 호출함.)
        Intent intent = getIntent();

        // 전달받은 데이터. (메인어댑터에서 전달함.)
        Long long_id = intent.getLongExtra("id", 0);
        String c_name = intent.getStringExtra("name");
        String c_count = intent.getStringExtra("count");
        String c_exp = intent.getStringExtra("expiration");
        String c_category = intent.getStringExtra("category");
        String c_type = intent.getStringExtra("type");
        Long c_dDay = intent.getLongExtra("dDay",0);
        Long long_inUseList = intent.getLongExtra("inUseList", 0);
        Long long_inBuyList = intent.getLongExtra("inBuyList", 0);
        String c_memo = intent.getStringExtra("memo");


        // 데이터 세팅.
        if (c_dDay < 0) {
            tvDday.setText("D+" + Math.abs(c_dDay));
            tvDday.setTextColor(Color.RED);
        } else if (c_dDay >= 0 && c_dDay <= 3) {
            tvDday.setText("D-" + Math.abs(c_dDay));
            tvDday.setTextColor(Color.BLUE);
        } else {
            tvDday.setText("D-" + Math.abs(c_dDay));
            tvDday.setTextColor(Color.BLACK);
        }

        tvName.setText(c_name);
        tvCnt.setText(c_count);
        tvExp.setText(c_exp);



        switch (c_category) {
            case "채소":
                ivPic.setImageResource(R.drawable.ci_vegetable);
                break;
            case "과일":
                ivPic.setImageResource(R.drawable.ci_fruits);
                break;
            case "육류":
                ivPic.setImageResource(R.drawable.ci_meat);
                break;
            case "수산물":
                ivPic.setImageResource(R.drawable.ci_fish);
                break;
            case "음료":
                ivPic.setImageResource(R.drawable.ci_drinks);
                break;
            case "유제품":
                ivPic.setImageResource(R.drawable.ci_dairy);
                break;
            case "인스턴트":
                ivPic.setImageResource(R.drawable.ci_instant);
                break;
            case "기타":
                ivPic.setImageResource(R.drawable.ci_etcetera);
                break;
        }


        switch(c_type){
            case "냉장" :
                tvType_2.setVisibility(View.INVISIBLE);
                break;
            case "냉동" :
                tvType_1.setVisibility(View.INVISIBLE);
                break;
        }

        // 수정버튼.
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("업데이트로 전달 데이터 : ", long_id + c_name);

                Intent intent = new Intent(getApplicationContext(), ContentsUpdate.class)
                        .putExtra("id", long_id)
                        .putExtra("name", c_name)
                        .putExtra("count", c_count)
                        .putExtra("expiration", c_exp)
                        .putExtra("category", c_category)
                        .putExtra("type", c_type)
                        .putExtra("dDay", c_dDay)
                        .putExtra("inUseList", long_inUseList)
                        .putExtra("inBuyList", long_inBuyList)
                        .putExtra("memo", c_memo);

                startActivity(intent);
            }
        });

        // 레시피보기 버튼.
        btnRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(
//                        Intent.ACTION_VIEW,
//                        Uri.parse("https://www.youtube.com/results?search_query="+ c_name+" 레시피"));
//
//                startActivity( intent );
                Intent intent = new Intent(getApplicationContext(), RecipeView.class).putExtra("keyword", c_name);
                startActivity(intent);
            }
        });

        //firebase 정의
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // 구매 리스트 담기 버튼.
        btnBuyList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contents content = new Contents(long_id, c_name, Long.valueOf(c_count), c_category, c_type, c_exp, Long.valueOf(c_dDay), Long.valueOf(long_inUseList), 1l);
                mDatabase.child("Contents").child(String.valueOf(long_id)).setValue(content)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                //Toast.makeText(getApplicationContext(), "구매리스트 수정완료, inBuyList 값 : " + content.getInBuyList(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //Toast.makeText(getApplicationContext(),"구매리스트 수정실패, inBuyList 값 : " + content.getInBuyList(),Toast.LENGTH_SHORT).show();
                            }
                        });

                Intent intent = new Intent(getApplicationContext(), CartView.class);
                startActivity(intent);


            }
        });



        // 메뉴바 관련
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    // 메뉴바
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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