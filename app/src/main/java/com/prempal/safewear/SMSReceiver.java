package com.prempal.safewear;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSReceiver extends BroadcastReceiver {

    public static final String TAG = "SMSReceiver";

    public SMSReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (Object aPdusObj : pdusObj) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) aPdusObj);
                    String senderAddress = currentMessage.getDisplayOriginatingAddress();
                    String message = currentMessage.getDisplayMessageBody();

                    Log.d(TAG, "Received SMS: " + message + "\n Sender: " + senderAddress);

                    if(senderAddress.equals("+917218573120")){
                        try{
                            String[] strings = message.split("\n");
                            String name = strings[0].substring(5);
                            String latitude = strings[1].substring(4);
                            String longitude = strings[2].substring(5);
                            Log.d(TAG, name + latitude + longitude);
                            Intent activityIntent = new Intent(context, MapsActivity.class);
                            activityIntent.putExtra("name", name);
                            activityIntent.putExtra("lat", latitude);
                            activityIntent.putExtra("long", longitude);
                            activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(activityIntent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }
}
