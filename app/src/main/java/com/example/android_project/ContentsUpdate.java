package com.example.android_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ContentsUpdate extends AppCompatActivity {

    EditText edtName, edtCnt, edtId, edtInUseList, edtInBuyList;
    Spinner spinCate, spinStoreWay;
    TextView tvExDay, tvDday;
    Button btnUpdate, btnDate;

    String[] category_array = {"채소","과일","육류","수산물","음료","유제품","인스턴트","기타"};
    String[] type_array = {"냉장","냉동"};

    private DatabaseReference mDatabase, contentsDatabase;


    // 날짜계산용
    int dateEndY, dateEndM, dateEndD;
    int ddayValue = 0;

    // 현재 날짜를 알기 위해 사용
    Calendar calendar;
    int currentYear, currentMonth, currentDay;

    // Millisecond 형태의 하루(24 시간)
    private final int ONE_DAY = 24 * 60 * 60 * 1000;




    // 유통기한
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // dday 텍스트뷰에
            ddayValue = ddayResult_int(dateEndY, dateEndM, dateEndD);
            tvDday = findViewById(R.id.tvDday);
            tvDday.setText(getDday(year, month, dayOfMonth));

            updateLabel();
        }
    };

    // d-day
    private String getDday(int mYear, int mMonthOfYear, int mDayOfMonth) {
        // D-day 설정
        final Calendar ddayCalendar = Calendar.getInstance();
        ddayCalendar.set(mYear, mMonthOfYear, mDayOfMonth);

        // D-day 를 구하기 위해 millisecond 으로 환산하여 d-day 에서 today 의 차를 구한다.
        final long dday = ddayCalendar.getTimeInMillis() / ONE_DAY;
        final long today = Calendar.getInstance().getTimeInMillis() / ONE_DAY;
        long result = dday - today;

        //  final String strCount = (String.format(strFormat, result));

        final String strCount = String.valueOf(result);

        return strCount;
    }

    // 유통기한 형식
    private void updateLabel() {
        String myFormat = "yyyy/MM/dd";    // 출력형식   2021/07/26
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        TextView date = (TextView) findViewById(R.id.tvExDay);
        date.setText(sdf.format(myCalendar.getTime()));
    }


    public int ddayResult_int(int dateEndY, int dateEndM, int dateEndD) {
        int result = 0;
        result = onCalculatorDate(dateEndY, dateEndM, dateEndD);
        return result;
    }


    public int onCalculatorDate (int dateEndY, int dateEndM, int dateEndD) {
        try {
            Calendar today = Calendar.getInstance(); //현재 오늘 날짜
            Calendar dday = Calendar.getInstance();

            //시작일, 종료일 데이터 저장
            Calendar calendar = Calendar.getInstance();
            int cyear = calendar.get(Calendar.YEAR);
            int cmonth = (calendar.get(Calendar.MONTH) + 1);
            int cday = calendar.get(Calendar.DAY_OF_MONTH);

            today.set(cyear, cmonth, cday);
            dday.set(dateEndY, dateEndM, dateEndD);// D-day의 날짜를 입력합니다.

            long day = dday.getTimeInMillis() / 86400000;
            // 각각 날의 시간 값을 얻어온 다음
            //( 1일의 값(86400000 = 24시간 * 60분 * 60초 * 1000(1초값) ) )

            long tday = today.getTimeInMillis() / 86400000;
            long count = tday - day; // 오늘 날짜에서 dday 날짜를 빼주게 됩니다.
            return (int) count; // 날짜는 하루 + 시켜줘야합니다.
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contents_update);


        edtName = findViewById(R.id.edtName);
        edtCnt = findViewById(R.id.edtCnt);
        edtId = findViewById(R.id.edtId);
        edtInUseList = findViewById(R.id.edtInUseList);
        edtInBuyList = findViewById(R.id.edtInBuyList);
        spinCate = findViewById(R.id.spinCate);
        spinStoreWay = findViewById(R.id.spinStoreWay);
        tvExDay = findViewById(R.id.tvExDay);
        tvDday = findViewById(R.id.tvDday);
//        tvDday.setVisibility(View.INVISIBLE);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDate = findViewById(R.id.btnDayInput);

        //시작일, 종료일 데이터 저장
        calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = (calendar.get(Calendar.MONTH));
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);



        // 데이트피커 디폴트 값 = 받아온 값 // 동작안됨.
//        String strDate = tvExDay.getText().toString();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date defaultDay = dateFormat.parse(strDate, new ParsePosition(0));
//        Long defaultLong = defaultDay.getTime();

        Intent intent = getIntent();
        Long c_dday = intent.getLongExtra("dDay",0);
        String c_exp = intent.getStringExtra("expiration");
        tvDday.setText(c_dday.toString());
        tvExDay.setText(c_exp);

        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
        String selectedDate = tvExDay.getText().toString();
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();


        try {
            date = dateTimeFormat.parse(selectedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(date);


        //한국어 설정 (ex: date picker)
        Locale.setDefault(Locale.KOREAN);


        // 유통기한 데이트피커버튼
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ContentsUpdate.this, myDatePicker, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // Detail 로부터 전달받은 인텐트.
        // Intent intent = getIntent();

        // Detail 로부터 전달받은 데이터.
        Long long_id = intent.getLongExtra("id", 0);
        String c_name = intent.getStringExtra("name");
        String c_count = intent.getStringExtra("count");
        //String c_exp = intent.getStringExtra("expiration");
        String c_category = intent.getStringExtra("category");
        String c_type = intent.getStringExtra("type");
        //String c_dDay = intent.getStringExtra("dDay");
        Long long_inUseList = intent.getLongExtra("inUseList", 0);
        Long long_inBuyList = intent.getLongExtra("inBuyList", 0);

        // 인풋한대로 데이터 셋팅.
        edtName.setText(c_name);
        edtCnt.setText(c_count);
        tvExDay.setText(c_exp);

        //tvdday.setText(c_dDay);
        edtInUseList.setText(long_inUseList.toString());
        edtInBuyList.setText(long_inBuyList.toString());
        edtId.setText(long_id.toString());

        ArrayAdapter cateAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, category_array);
        spinCate.setAdapter(cateAdapter);
        spinCate.setSelection(getIndex(spinCate, c_category));
        final String[] re_category = {(String) spinCate.getSelectedItem()};
        spinCate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (c_category == re_category[0]) {

                } else {
                    re_category[0] = spinCate.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, type_array);
        spinStoreWay.setAdapter(typeAdapter);
        spinStoreWay.setSelection(getIndex(spinStoreWay, c_type));
        final String[] re_type = {(String) spinStoreWay.getSelectedItem()};
        spinStoreWay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (c_type == re_type[0]) {

                } else {
                    re_type[0] = spinStoreWay.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        //firebase 정의
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // 수정버튼 클릭.
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getUserName = edtName.getText().toString();
                String getUserCnt = edtCnt.getText().toString();
                String getCategory = spinCate.getSelectedItem().toString();
                String getStorage = spinStoreWay.getSelectedItem().toString();
                String getExpiration = tvExDay.getText().toString();
                String getId = edtId.getText().toString();
                String getDday = tvDday.getText().toString();
                String getInUseList = edtInUseList.getText().toString();
                String getInBuyList = edtInBuyList.getText().toString();


                Update(Long.valueOf(getId), getUserName, getCategory, getStorage, getExpiration, Long.valueOf(getUserCnt), Long.valueOf(getDday), Long.valueOf(getInUseList), Long.valueOf(getInBuyList));

            }
        });

        // 메뉴바 관련
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    } // onCreate()

    // 업데이트(덮어쓰기) 함수.
    private void Update(Long Id, String name, String category, String type, String expiration, Long count, Long dday, Long inUseList, Long inBuyList){
        Contents content = new Contents(Id, name, count, category, type, expiration,  dday, inUseList, inBuyList);

        mDatabase.child("Contents").child(String.valueOf(Id)).setValue(content)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //Toast.makeText(getApplicationContext(), "수정완료", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Toast.makeText(getApplicationContext(),"수정실패",Toast.LENGTH_SHORT).show();
                    }
                });
    }


    // 스피너 디폴트 인덱스.
    private int getIndex(Spinner spinner, String item){
        for(int i=0; i<spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).toString().equals(item)) {
                return i;
            }
        }
        return 0;
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