package com.example.android_project;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver {

    private String TAG = this.getClass().getSimpleName();

    NotificationManager manager;
    NotificationCompat.Builder builder;

    //오레오 이상은 반드시 채널을 설정해줘야 Notification이 작동함
    private static String CHANNEL_ID = "CHANNEL";
    private static String CHANNEL_NAME = "CHANNEL";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG, "onReceive: " + intent.getIntExtra("count",0));
        Log.e(TAG, "onReceive 알람 들어옴");

        String contentValue = intent.getStringExtra("content");
        int count = intent.getIntExtra("count",3);
        Log.d("test", "onReceive: " + count);
        Log.d(TAG, "onReceive: " + CHANNEL_ID);

        Log.e(TAG, "넘어오는 값 확인 : " + CHANNEL_ID + CHANNEL_NAME);

        Log.e(TAG, "onReceiver contentValue값 확인 : " + contentValue);

        builder = null;

        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //안드로이드 오레오 버전 대응
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            manager.createNotificationChannel(
//                    new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
//            );

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Mascot");

            manager.createNotificationChannel(notificationChannel);


            builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        } else {
            builder = new NotificationCompat.Builder(context);
        }

//        Intent intent2 = new Intent(context, MainActivity.class);
//
//        // FLAG_UPDATE_CURRENT ->
//        // 설명된 PendingIntent가 이미 존재하는 경우 유지하되, 추가 데이터를 이 새 Intent에 있는 것으로 대체함을 나타내는 플래그입니다.
//        // getActivity, getBroadcast 및 getService와 함께 사용
//        PendingIntent pendingIntent = PendingIntent.getActivity(context,123,intent2,
//                PendingIntent.FLAG_IMMUTABLE);

        //알림창 제목
        builder.setContentTitle("나의 냉장고");
        // count 받아올 수 있는지??
        builder.setContentText("유통기한이 얼마 남지 않은 품목이 있습니다." + "\n" + "어서 확인 해주세요.");
        //알림창 아이콘
        builder.setSmallIcon(R.drawable.fridge);
        //알림창 터치시 자동 삭제
        builder.setAutoCancel(true);

//        builder.setContentIntent(pendingIntent);
        //푸시알림 빌드
        Notification notification = builder.build();

        //NotificationManager를 이용하여 푸시 알림 보내기
        manager.notify(1,notification);
    }
}
