package com.example.android_project;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class DdayScheduler extends JobService {
    ArrayList<String> ddayArrayList;

    private static final String TAG = "ddayScheduler";
    private boolean jobCancelled = false;

    //서비스 시작
    @Override
    public boolean onStartJob(JobParameters params) {

        Log.d(TAG, "onStartJob");

        doBackgroundWork(params);
        return true;
    }

    //서비스 중지
    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "onStopJob");
        jobCancelled = true;
        return true;
    }

    private void doBackgroundWork(final JobParameters params){
        new Thread(new Runnable() {
            @Override
            public void run() {


                ddayArrayList = new ArrayList<>();

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Contents");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ddayArrayList.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            Contents contents = snapshot.getValue(Contents.class);

//                            DatabaseReference idd = ref.getParent();
//                             ref.getKey();
//                              ref.setValue(idd);
//                              snapshot.getKey();


                            String ids = snapshot.getKey();
                            Log.d(TAG, "onDataChange: " + ids);
                            String expiration = contents.getExpiration();
                            int year = Integer.parseInt(expiration.substring(0,4));
                            int month = Integer.parseInt(expiration.substring(5,7));
                            int day = Integer.parseInt(expiration.substring(8));

                            Calendar cal = Calendar.getInstance();
                            cal.set(year, month-1, day);

                            Long dDay = cal.getTimeInMillis()/1000/60/60/24;
                            long now = System.currentTimeMillis()/1000/60/60/24;

                            long result = dDay - now;

//                            contents.setDday(result);

                            ref.child(ids).child("dday").setValue(result);




//                            ddayArrayList.add(String.valueOf(contents));

                        }


                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        }).start();
    }

}