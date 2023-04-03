package com.example.android_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Home extends AppCompatActivity {

    ImageView logo;
    View v;

    // 알림테스트 3
    private String TAG = this.getClass().getSimpleName();

    private AlarmManager alarmManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        this.initialSet();

        Glide.with(this)
                .asGif()
                .load(R.raw.minibar)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(logo);

        logo = (ImageView) findViewById(R.id.logo);
        // 알림테스트3
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // dday 스케쥴러 동작
        scheduleJob(v);

        Button btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                cancelJob(v);

                // 알림등록 : 유통기한 하루 전날, 자정직전.
                // 테스트방법 : 현재시간 +1분으로 설정 후 실행하고 재료 인풋하세요. 1분 30초쯤 뒤에 알림 뜹니다.
                // 인풋한 재료ID와 재료이름 받아오는게 될때가 있고 안될때(그 이전에 넣은 재료의 정보를 가져옴)가 있음...
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Contents");
                Query query = databaseReference.orderByChild("dday");
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int count = 0;
                        boolean ddayChk = false;
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Log.d("test", "count : " + count);
                            if (Long.parseLong(String.valueOf(dataSnapshot.child("dday").getValue()))<=1 ) {
                                ddayChk = true;
                                count += 1;
                            }
                            Log.d("test", "count : " + count);
                        }
                        if (ddayChk == true) {
                            try {
                                setNotice("00:00:00", count);
                                Log.d("Notice", "count : " + count);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    //스케쥴 시작
    public void scheduleJob(View v){
        ComponentName componentName = new ComponentName(this, DdayScheduler.class);

        //작업설정
        JobInfo info = new JobInfo.Builder(123, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)//네트워크 상태 설정
                .setPersisted(true) //부팅후 작업 실행 여부설정 . RECEIVE_BOOT_COMPLETED 권한 필요
                .build();

        //setPeriodic 부팅 후 작업 실행여부설정 RECEIVE_BOOT_COMPLETED 권한설정해야함
        //setMinimumLatency(TimeUnit.MINUTES.toMillis(1))1분 //얼마후에 실행되어야 하는지(한번만)
        //NETWORK_TYPE_UNMETERED WIFI
        //NETWORK_TYPE_CELLULAR 셀룰러
        //NETWORK_TYPE_ANY 언제든
        //NETWORK_TYPE_NONE 인터넷 연결 안되었을때

        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

        //작업결과값
        int resultCode = jobScheduler.schedule(info);
        if(resultCode == jobScheduler.RESULT_SUCCESS){
                Log.d("schedule", "작업 성공");
        }else{
            //   Log.d(TAG, "작업 실패");
        }
    }

    //스케쥴 취소
    public void cancelJob(View v){
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);

    }

    // 알림테스트 3

    private void setNotice(String alarmTimeValue, int count) throws ParseException {

        Intent receiverIntent = new Intent(this, NotificationReceiver.class);
        receiverIntent.putExtra("content", "123" );
        receiverIntent.putExtra("count", count);


        /**
         * PendingIntent란?
         * - Notification으로 작업을 수행할 때 인텐트가 실행되도록 합니다.
         * Notification은 안드로이드 시스템의 NotificationManager가 Intent를 실행합니다.
         * 즉 다른 프로세스에서 수행하기 때문에 Notification으로 Intent수행시 PendingIntent의 사용이 필수 입니다.
         */

        /**
         * 브로드캐스트로 실행될 pendingIntent선언 한다.
         * Intent가 새로 생성될때마다(알람을 등록할 때마다) intent값을 업데이트 시키기 위해, FLAG_UPDATE_CURRENT 플래그를 준다
         * 이전 알람을 취소시키지 않으려면 requestCode를 다르게 줘야 한다.
         * */
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 123, receiverIntent, PendingIntent.FLAG_IMMUTABLE);



        //등록한 알람날짜 포맷을 밀리초로 변경한기 위한 코드
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


        SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy/MM/dd");


        Calendar calendarA = Calendar.getInstance();


        String str1 = dtFormat.format(calendarA.getTime());

        Date datetime = null;

        try {
            datetime = dateFormat.parse(str1 + " " + alarmTimeValue);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //date타입으로 변경된 알람시간을 캘린더 타임에 등록
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime);

        //알람시간 설정
        //param 1)알람의 타입
        //param 2)알람이 울려야 하는 시간(밀리초)을 나타낸다.
        //param 3)알람이 울릴 때 수행할 작업을 나타냄
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);

    }


    public void initialSet() {
        logo = (ImageView) findViewById(R.id.logo);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}