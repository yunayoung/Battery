package com.example.user.battery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button buttonSend;
    EditText textPhoneNo;
    EditText textSMS;
    TextView mStatus;
    Switch swc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSend = (Button) findViewById(R.id.buttonSend);
        textPhoneNo = (EditText) findViewById(R.id.editTextPhoneNo);
        textSMS = (EditText) findViewById(R.id.editTextSMS);

        mStatus = (TextView) findViewById(R.id.status);
        swc = (Switch) findViewById(R.id.switch1);

        swc.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String str = String.valueOf(isChecked);
                // Toast.makeText(getApplicationContext(),"체크상태 = "+isChecked,Toast.LENGTH_SHORT);

                if (isChecked) {
                    Toast.makeText(getApplicationContext(), "on", Toast.LENGTH_SHORT).show();
                    Log.d("test", "on");
                    Intent intent = new Intent(
                            getApplicationContext(),//현재제어권자
                            Myservice.class); // 이동할 컴포넌트
                    startService(intent); // 서비스 시작


                } else {
                    Toast.makeText(getApplicationContext(), "off", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(
                            getApplicationContext(),//현재제어권자
                            Myservice.class); // 이동할 컴포넌트
                    stopService(intent); // 서비스 종료
                }
            }
        });
/*
        Intent intent = new Intent(
                getApplicationContext(),
                Myservice.class
        );
        startService(intent);
*/
        //버튼 클릭이벤트
        buttonSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //입력한 값을 가져와 변수에 담는다
                String phoneNo = textPhoneNo.getText().toString();
                // String sms = textSMS.getText().toString();
                String sms = mStatus.getText().toString();


                try {
                    //전송
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, sms, null, null);
                    Toast.makeText(getApplicationContext(), "전송 완료!", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "SMS faild, please try again later!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        });

    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        unregisterReceiver(mBRBattery);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
//        filter.addAction(Intent.ACTION_BATTERY_LOW);
//        filter.addAction(Intent.ACTION_BATTERY_OKAY);
//        filter.addAction(Intent.ACTION_POWER_CONNECTED);
//        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
//
//        registerReceiver(mBRBattery, filter);
//    }
//
//    BroadcastReceiver mBRBattery = new BroadcastReceiver() {
//
//        int count = 0;
//
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//
//            if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
//                onBatteryChanged(intent);
//            }
//            if (action.equals(Intent.ACTION_BATTERY_LOW)) {
//                Toast.makeText(context, "배터리 용량 부족", Toast.LENGTH_SHORT).show();
//            }
//            if (action.equals(Intent.ACTION_BATTERY_OKAY)) {
//                Toast.makeText(context, "배터리 용량 정상", Toast.LENGTH_SHORT).show();
//            }
//        }
//    };
//
//    public void onBatteryChanged(Intent intent) {
//
//        int plug, status, scale, level, ratio;
//        String sPlug = "";
//        String sStatus = "";
//
//        if (intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false) == false) {
//            mStatus.setText("no battery");
//            return;
//        }
//
//        plug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
//        status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN);
//        scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
//        level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
//        ratio = level * 100 / scale;
//
//        switch (plug) {
//            case BatteryManager.BATTERY_PLUGGED_AC:
//                sPlug = "AC";
//                break;
//            case BatteryManager.BATTERY_PLUGGED_USB:
//                sPlug = "USB";
//                break;
//            default:
//                sPlug = "BATTERY";
//                break;
//        }
//
//        switch (status) {
//            case BatteryManager.BATTERY_STATUS_CHARGING:
//                sStatus = "Charging";
//                break;
//            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
//                sStatus = "not charging";
//                break;
//            case BatteryManager.BATTERY_STATUS_DISCHARGING:
//                sStatus = "discharging";
//                break;
//            case BatteryManager.BATTERY_STATUS_FULL:
//                sStatus = "fully charged";
//                break;
//            default:
//            case BatteryManager.BATTERY_STATUS_UNKNOWN:
//                sStatus = "Unknwon status";
//                break;
//        }
//
//        String str = "연결: " + sPlug + "\n상태: " + sStatus + "\n 레벨" + ratio;
//
//        mStatus.setText(str);
//
//    }
//
//    ;



/*
    public static int getBatteryPercentage(Context context) {
        Intent batteryStatus = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryPct = level / (float)scale;
        return (int)(batteryPct * 100);
    }
*/
}
