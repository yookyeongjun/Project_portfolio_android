package com.example.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;

public class RecipeDetail extends AppCompatActivity {

    TextView tvName, tvMaterial, tvManual;
    ImageView ivRecipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail);

        tvName = (TextView) findViewById(R.id.recipeName);
        tvMaterial = (TextView) findViewById(R.id.recipeMaterials);
        tvManual = (TextView) findViewById(R.id.recipeManual);
        ivRecipe = (ImageView) findViewById(R.id.recipeImage);

        Intent intent = getIntent();
        String rName = intent.getStringExtra("name");
        String rMaterial = intent.getStringExtra("materials");

        StringBuilder rManual = new StringBuilder();
        rManual.append(intent.getStringExtra("manual01")+"\n"+"\n");
        rManual.append(intent.getStringExtra("manual02")+"\n"+"\n");
        rManual.append(intent.getStringExtra("manual03")+"\n"+"\n");
        rManual.append(intent.getStringExtra("manual04")+"\n"+"\n");
        rManual.append(intent.getStringExtra("manual05")+"\n"+"\n");
        rManual.append(intent.getStringExtra("manual06")+"\n"+"\n");
        rManual.append(intent.getStringExtra("manual07")+"\n"+"\n");
        rManual.append(intent.getStringExtra("manual08")+"\n"+"\n");
        rManual.append(intent.getStringExtra("manual09")+"\n"+"\n");
        rManual.append(intent.getStringExtra("manual10")+"\n"+"\n");
        rManual.append(intent.getStringExtra("manual11")+"\n"+"\n");
        rManual.append(intent.getStringExtra("manual12")+"\n"+"\n");
        rManual.append(intent.getStringExtra("manual13")+"\n"+"\n");
        rManual.append(intent.getStringExtra("manual14")+"\n"+"\n");
        rManual.append(intent.getStringExtra("manual15")+"\n"+"\n");
        rManual.append(intent.getStringExtra("manual16")+"\n"+"\n");
        rManual.append(intent.getStringExtra("manual17")+"\n"+"\n");
        rManual.append(intent.getStringExtra("manual18")+"\n"+"\n");
        rManual.append(intent.getStringExtra("manual19")+"\n"+"\n");
        rManual.append(intent.getStringExtra("manual20")+"\n"+"\n");

        String rImageUrl = intent.getStringExtra("imageUrl");

        tvName.setText(rName);
        tvMaterial.setText(rMaterial);
        tvManual.setText(rManual);
        Glide.with(this).load(rImageUrl).into(ivRecipe);



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
