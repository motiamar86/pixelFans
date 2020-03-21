package com.zms.fansfixels;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Alarm", Toast.LENGTH_LONG).show();
        Log.d("MNMN", "Alarmmmm1!!");
        EventBus.getDefault().post(new StartEventEventBus());
    }
}