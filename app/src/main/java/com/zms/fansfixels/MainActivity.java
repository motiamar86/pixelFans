package com.zms.fansfixels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    FirebaseRemoteConfig mFirebaseRemoteConfig;
    Button updateMyLocation;
    NumberPicker mNumberPickerRow;
    NumberPicker mNumberPickerChair;
    LinearLayout waitView;
    TextView mWaitViewText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateMyLocation = (Button) findViewById(R.id.updateMyLocation);
        mNumberPickerRow = (NumberPicker) findViewById(R.id.numberPickersRowNumber);
        mNumberPickerChair = (NumberPicker) findViewById(R.id.numberPickersChairNumber);
        waitView = (LinearLayout) findViewById(R.id.waitForStarting);
        mWaitViewText = (TextView) findViewById(R.id.wait_view_text);

        initRemoteConfig();




        updateMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpdateClicked();
                waitView.setVisibility(View.VISIBLE);
            }
        });
        // FlashlightProvider aFlashlightProvider = new FlashlightProvider(this);
        // aFlashlightProvider.turnFlashlightOn();




    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(StartEventEventBus event) {
        mWaitViewText.setText(R.string.event_started_message);

    };

    private void onUpdateClicked() {
        AlarmManager mAlarmManger = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        String eventTimeInString = FireBaseNewIconSharedPreference.getInstance().getStartEvent();
        if (eventTimeInString != null) {
            //Timestamp in milliseconds
            Calendar now = Calendar.getInstance();
            if((Long.valueOf(eventTimeInString)) > (now.getTimeInMillis() / 1000)) {
                Toast.makeText(this, "alarmSet", Toast.LENGTH_SHORT).show();
                mAlarmManger.set(AlarmManager.RTC_WAKEUP, (Long.valueOf(eventTimeInString)), pendingIntent);
            }
        }
    }

    private void initPickers() {
        mNumberPickerRow.setMinValue(0);
        mNumberPickerRow.setMaxValue(FireBaseNewIconSharedPreference.getInstance().getEventImageY());

        mNumberPickerRow.setOnValueChangedListener(onValueChangeListenerRow);

        mNumberPickerChair.setMinValue(0);
        mNumberPickerChair.setMaxValue(FireBaseNewIconSharedPreference.getInstance().getEventImageX());

        mNumberPickerChair.setOnValueChangedListener(onValueChangeListenerChair);
    }

    NumberPicker.OnValueChangeListener onValueChangeListenerRow =
            new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker numberPicker, int i, int i1) {

                }
            };

    NumberPicker.OnValueChangeListener onValueChangeListenerChair =
            new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker numberPicker, int i, int i1) {

                }
            };


    private void initRemoteConfig() {
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        // cacheExpirationSeconds is set to cacheExpiration here, indicating the next fetch request
// will use fetch data from the Remote Config service, rather than cached parameter values,
// if cached parameter values are more than cacheExpiration seconds old.
// See Best Practices in the README for more information.
        mFirebaseRemoteConfig.fetch(0)
                .addOnCompleteListener((Activity) this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {


                            mFirebaseRemoteConfig.activateFetched();

                            String listIds = mFirebaseRemoteConfig.getString(FireBaseConstance.EVENT_FLASH_DETAILS);
                            Log.d("MNMN", listIds);
                            FireBaseNewIconSharedPreference.getInstance().saveNewLIst(listIds);


                            initPickers();
                        }
                    }
                });
    }
}
