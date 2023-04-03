package com.example.android_project;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ContentsInput extends AppCompatActivity {

    EditText edtName,edtCnt, edtId;
    Button btnAdd, btnDate;
    TextView date, tvdday;
    RadioButton rdo1day, rdo3day, rdo7day;
    RadioGroup rg1;

    //데이터를 배열에 넣어서 준비
    String[] items = {"채소", "육류", "과일", "수산물", "음료", "유제품", "인스턴트", "기타"};

    String[] storage = {"냉장", "냉동"};

    private DatabaseReference mDatabase, contentsDatabase;

    // 날짜계산용
    int dateEndY, dateEndM, dateEndD;
    int ddayValue = 0;

    // 현재 날짜를 알기 위해 사용
    Calendar calendar;
    int currentYear, currentMonth, currentDay;

    // Millisecond 형태의 하루(24 시간)
    private final int ONE_DAY = 24 * 60 * 60 * 1000;

    // 유통기한 라디오버튼 함수
    private static String AddDate(int i) throws ParseException {

        SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy/MM/dd");

        String strNow = dtFormat.format(new Date(System.currentTimeMillis()));
        Date dt = dtFormat.parse(strNow);

        Calendar calendarA = Calendar.getInstance();
        calendarA.setTime(dt);
        calendarA.add(Calendar.DAY_OF_MONTH, i);

        return dtFormat.format(calendarA.getTime());
    }




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
            tvdday = findViewById(R.id.tvdday);
            tvdday.setText(getDday(year, month, dayOfMonth));

            updateLabel();
        }
    };




    // 현재시간

    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy/MM/dd");
    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contents_input);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edtId = findViewById(R.id.edtId);
        edtName = findViewById(R.id.edtName);
        edtCnt = findViewById(R.id.edtCnt);
        btnAdd = findViewById(R.id.btnAdd);
        date = findViewById(R.id.tvExDay);

        tvdday = findViewById(R.id.tvdday);
        tvdday.setVisibility(View.INVISIBLE);

        btnDate = findViewById(R.id.btnDayInput);

        rg1 = findViewById(R.id.rg1);

        rdo1day = findViewById(R.id.rdo1day);
        rdo3day = findViewById(R.id.rdo3day);
        rdo7day = findViewById(R.id.rdo7day);

        // 라디오그룹 익명리스너 세팅
        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                switch (checkedId) {
                    case R.id.rdo1day:
                        //오늘날짜+1 하는 함수호출
                        try {
                            date.setText(AddDate(1));
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        //디데이 텍스트뷰에 1 세팅
                        tvdday.setText("1");
                        break;
                    case R.id.rdo3day:
                        try {
                            date.setText(AddDate(3));
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        tvdday.setText("3");
                        break;
                    case R.id.rdo7day:
                        try {
                            date.setText(AddDate(7));
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        tvdday.setText("7");
                        break;
                }

            }
        });

        //시작일, 종료일 데이터 저장
        calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = (calendar.get(Calendar.MONTH));
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        //한국어 설정 (ex: date picker)
        Locale.setDefault(Locale.KOREAN);


        // 유통기한 데이트피커버튼
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ContentsInput.this, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });




        //스피너
        Spinner spinner = findViewById(R.id.spinCate);
        // textView = findViewById(R.id.textView);

        ArrayAdapter adapter1 = new ArrayAdapter<String>(
                //API에 만들어져 있는 R.layout.simple_spinner...를 씀
                this,android.R.layout.simple_spinner_item, items
        );
        //미리 정의된 레이아웃 사용
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_item);
        // 스피너 객체에다가 어댑터를 넣어줌
        spinner.setAdapter(adapter1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // 선택되면
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //   textView.setText(items[position]);
            }

            // 아무것도 선택되지 않은 상태일 때
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // textView.setText("선택: ");
            }
        });


        //스피너
        Spinner spinStoreWay = findViewById(R.id.spinStoreWay);
        // textView = findViewById(R.id.textView);

        ArrayAdapter adapter2 = new ArrayAdapter<String>(
                //API에 만들어져 있는 R.layout.simple_spinner...를 씀
                this,android.R.layout.simple_spinner_item, storage
        );
        //미리 정의된 레이아웃 사용
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
        // 스피너 객체에다가 어댑터를 넣어줌
        spinStoreWay.setAdapter(adapter2);
        spinStoreWay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // 선택되면
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //   textView.setText(items[position]);
            }

            // 아무것도 선택되지 않은 상태일 때
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // textView.setText("선택: ");
            }
        });









        // 자동 id 생성
        contentsDatabase = FirebaseDatabase.getInstance().getReference("Contents");
        contentsDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    Long maxId = 0L;
                    Long Id = 0L;
                    Long[] longList;
                    longList = new Long[(int) snapshot.getChildrenCount()];

                    for (int i=0; i<snapshot.getChildrenCount();i++) {
                        longList[i] = (Long) postSnapshot.child("id").getValue();
                    }
                    for (int i=0; i<longList.length; i++) {
                        if (maxId < longList[i]) {
                            maxId = longList[i];
                            Id = maxId + 1;
                        }
                    }
                    edtId.setText(Id.toString());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        edtId.setText("1");     // Contents 에 항목이 없으면 첫 id는 1로 자동설정

        //firebase 정의
        mDatabase = FirebaseDatabase.getInstance().getReference();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtName.getText().toString().length() == 0 || edtCnt.getText().toString().length() == 0) {
                    Toast.makeText(ContentsInput.this, "재료명과 수량을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (date.getText().toString().length() == 0) {
                    Toast.makeText(ContentsInput.this, "유통기한을 선택해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    Long getId = Long.parseLong(edtId.getText().toString());
                    String getUserName = edtName.getText().toString();
                    String getUserCnt = edtCnt.getText().toString();
                    String getCategory = spinner.getSelectedItem().toString();
                    String getStorage = spinStoreWay.getSelectedItem().toString();
                    Long getDday = Long.parseLong(tvdday.getText().toString());
                    String getExpiration = date.getText().toString();

                    writeNewContents(getId, getUserName, Long.valueOf(getUserCnt),getCategory, getStorage, getExpiration, getDday,0L, 0L);

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }




    private void writeNewContents(Long id, String name, Long count, String category, String type, String expiration, Long dday, Long inUseList, Long inBuyList) {
        Contents content = new Contents(id, name, count, category, type, expiration, dday, inUseList, inBuyList);

            mDatabase.child("Contents").child(String.valueOf(id)).setValue(content)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                }
            });

    }



    // 유통기한 형식
    private void updateLabel() {
        String myFormat = "yyyy/MM/dd";    // 출력형식   2021/07/26
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        TextView date = (TextView) findViewById(R.id.tvExDay);
        date.setText(sdf.format(myCalendar.getTime()));
    }



    // d-day
    private String getDday(int mYear, int mMonthOfYear, int mDayOfMonth) {
        // D-day 설정
        final Calendar ddayCalendar = Calendar.getInstance();
        ddayCalendar.set(mYear, mMonthOfYear, mDayOfMonth);

        // D-day 를 구하기 위해 millisecond 으로 환산하여 d-day 에서 today 의 차를 구한다.
        final long dday = ddayCalendar.getTimeInMillis() / ONE_DAY;
        final long today = Calendar.getInstance().getTimeInMillis() / ONE_DAY;
        long result = dday - today;

        // 출력 시 d-day 에 맞게 표시
//        String strFormat;
//        if (result > 0) {
//            strFormat = "D-%d";
//        } else if (result == 0) {
//            strFormat = "Today";
//        } else {
//            result *= -1;
//            strFormat = "D+%d";
//        }



        //  final String strCount = (String.format(strFormat, result));

        final String strCount = String.valueOf(result);

        return strCount;
    }
// 2023/02/08
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

    public int ddayResult_int(int dateEndY, int dateEndM, int dateEndD) {
        int result = 0;
        result = onCalculatorDate(dateEndY, dateEndM, dateEndD);
        return result;
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

