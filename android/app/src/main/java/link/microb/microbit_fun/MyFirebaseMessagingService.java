package link.microb.microbit_fun;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.bluetooth.mwoolley.microbitbledemo.Constants;
import com.bluetooth.mwoolley.microbitbledemo.MicroBitEvent;
import com.bluetooth.mwoolley.microbitbledemo.Utility;
import com.bluetooth.mwoolley.microbitbledemo.bluetooth.BleAdapterService;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.HashMap;

/**
 * Created by chentb on 2016/12/10.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    String  TAG = "MyFirebaseMessaging";
    private BleAdapterService bluetooth_le_adapter;
    private Handler mMessageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            Bundle bundle;
            String service_uuid = "";
            String characteristic_uuid = "";
            String descriptor_uuid = "";
            byte[] b = null;
            TextView value_text = null;

            switch (msg.what) {
                case BleAdapterService.GATT_CHARACTERISTIC_WRITTEN:
                    Log.d(Constants.TAG, "Handler received characteristic written result");
                    bundle = msg.getData();
                    service_uuid = bundle.getString(BleAdapterService.PARCEL_SERVICE_UUID);
                    characteristic_uuid = bundle.getString(BleAdapterService.PARCEL_CHARACTERISTIC_UUID);
                    Log.d(Constants.TAG, "characteristic " + characteristic_uuid + " of service " + service_uuid + " written OK");
                    break;
                case BleAdapterService.MESSAGE:
                    bundle = msg.getData();
                    String text = bundle.getString(BleAdapterService.PARCEL_TEXT);
                    //showMsg(text);
            }
        }
    };

    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            Log.d(Constants.TAG, "onServiceConnected");
            bluetooth_le_adapter = ((BleAdapterService.LocalBinder) service).getService();
            bluetooth_le_adapter.setActivityHandler(mMessageHandler);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            bluetooth_le_adapter = null;
        }
    };

    public void onCreate(){
        super.onCreate();
        Intent gattServiceIntent = new Intent(this, BleAdapterService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
    }

    public void onDestroy(){
        super.onDestroy();
        unbindService(mServiceConnection);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...
//        Log.d(TAG,remoteMessage.getData());
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            String weatherIcon = remoteMessage.getData().get("default");
            short type = 1104;
            short value = 1;
            if("01d".equals(weatherIcon) || "01n".equals(weatherIcon)) {
                value = 1;
            }else if("02d".equals(weatherIcon) || "02n".equals(weatherIcon)) {
                value = 2;
            }else if("03d".equals(weatherIcon) || "03n".equals(weatherIcon)) {
                value = 3;
            }else if("04d".equals(weatherIcon) || "04n".equals(weatherIcon)) {
                value = 4;
            }else if("09d".equals(weatherIcon) || "09n".equals(weatherIcon)) {
                value = 5;
            }else if("10d".equals(weatherIcon) || "10n".equals(weatherIcon)) {
                value = 6;
            }else if("11d".equals(weatherIcon) || "11n".equals(weatherIcon)) {
                value = 7;
            }else if("13d".equals(weatherIcon) || "13n".equals(weatherIcon)) {
                value = 8;
            }else if("50d".equals(weatherIcon) || "50n".equals(weatherIcon)) {
                value = 9;
            }
            MicroBitEvent mb_event = new MicroBitEvent(type,value);
            byte[] event_bytes = mb_event.getEventBytesForBle();
            if(bluetooth_le_adapter != null) {
                bluetooth_le_adapter.writeCharacteristic(Utility.normaliseUUID(BleAdapterService.EVENTSERVICE_SERVICE_UUID), Utility.normaliseUUID(BleAdapterService.CLIENTEVENT_CHARACTERISTIC_UUID), event_bytes);
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
}
