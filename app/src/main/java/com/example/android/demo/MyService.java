package com.example.android.demo;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class MyService extends Service {

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivities(this,0, new Intent[]{intent},0);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentText("")
                .setContentTitle("")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1,notification);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    class DownloadBinder extends Binder{
        public void startDownload(){

        }
        public int getProgeree(){
            return 0;
        }
    }
}
