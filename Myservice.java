package com.example.user.battery;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.widget.Toast;

public class Myservice extends Service {

    int ratio;
    IntentFilter filter = new IntentFilter();
    String mStatus;

    public void onBatteryChanged(Intent intent) {

        int plug, status, scale, level;
        //ratio
        String sPlug = "";
        String sStatus = "";

        if (intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false) == false) {
            mStatus = "no battery";
            return;
        }

        plug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
        status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN);
        scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
        level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        ratio = level * 100 / scale;

        switch (plug) {
            case BatteryManager.BATTERY_PLUGGED_AC:
                sPlug = "AC";
                break;
            case BatteryManager.BATTERY_PLUGGED_USB:
                sPlug = "USB";
                break;
            default:
                sPlug = "BATTERY";
                break;
        }

        switch (status) {
            case BatteryManager.BATTERY_STATUS_CHARGING:
                sStatus = "Charging";
                break;
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                sStatus = "not charging";
                break;
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                sStatus = "discharging";
                break;
            case BatteryManager.BATTERY_STATUS_FULL:
                sStatus = "fully charged";
                break;
            default:
            case BatteryManager.BATTERY_STATUS_UNKNOWN:
                sStatus = "Unknwon status";
                break;
        }

        String str = "연결: " + sPlug + "\n상태: " + sStatus + "\n 레벨" + ratio;

        mStatus = str;

        ratio=100;
//        Toast.makeText(this, "배터리 용량 부족", Toast.LENGTH_SHORT).show();
        //SendSMS("01033448355",mStatus);
        new Thread(new Runnable() {
            public void run() {
                try{
                    //SendSMS("01033448355", mStatus);
//                    Toast.makeText(getApplicationContext(), "부족", Toast.LENGTH_SHORT).show();
//                    if(ratio <30){
//
//                        Toast.makeText(getApplicationContext(), "부족", Toast.LENGTH_SHORT).show();
//                    }

                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                    SendSMS("01033448355",mStatus);
                //Toast.makeText(getApplicationContext(), "부족", Toast.LENGTH_SHORT).show();
                }
        }).start();

    };



    BroadcastReceiver mBRBattery = new BroadcastReceiver() {

        int count = 0;

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
                 onBatteryChanged(intent);
            }
            if (action.equals(Intent.ACTION_BATTERY_LOW)) {
                Toast.makeText(context, "배터리 용량 부족", Toast.LENGTH_SHORT).show();
            }
            if (action.equals(Intent.ACTION_BATTERY_OKAY)) {
                Toast.makeText(context, "배터리 용량 정상", Toast.LENGTH_SHORT).show();
            }
        }
    };

    void SendSMS(String number, String msg) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(number, null, msg, null, null);
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        filter.addAction(Intent.ACTION_BATTERY_LOW);
        filter.addAction(Intent.ACTION_BATTERY_OKAY);
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);




        registerReceiver(mBRBattery, filter);

        if(ratio!=0){
            Toast.makeText(getApplicationContext(), "전송 완료!", Toast.LENGTH_LONG).show();
        }
     //   Toast.makeText(getApplicationContext(), "부족", Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            public void run() {

                try{
                    //SendSMS("01033448355", mStatus);
//                    Toast.makeText(getApplicationContext(), "부족", Toast.LENGTH_SHORT).show();
//                    if(ratio <30){
//
//                        Toast.makeText(getApplicationContext(), "부족", Toast.LENGTH_SHORT).show();
//                    }

                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

                if(ratio <30){

                    Toast.makeText(getApplicationContext(), "부족", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {

        unregisterReceiver(mBRBattery);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

}
