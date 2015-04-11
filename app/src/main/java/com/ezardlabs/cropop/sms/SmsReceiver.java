package com.ezardlabs.cropop.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmsReceiver extends BroadcastReceiver {

	public void onReceive(Context context, Intent intent) {
		Bundle pdusBundle = intent.getExtras();
		Object[] pdus = (Object[]) pdusBundle.get("pdus");
		SmsMessage messages = SmsMessage.createFromPdu((byte[]) pdus[0]);
		Log.i("", messages.getMessageBody() + ", " + messages.getOriginatingAddress());
		Pattern pattern = Pattern.compile("((DIS) ((?:A[1-3])|UNKNOWN))|((INC) ([0-9]+(?:\\.[0-9]+)?))|((HRV) ([0-9]+(?:\\.[0-9]+)?C[1-3])\\s?+)");
		Matcher m = pattern.matcher(messages.getMessageBody());
		while (m.find()) {
			Log.i("", "Start");
			outer:
			for (int i = 1; i < m.groupCount(); i++) {
				if (m.group(i) != null) {
					switch (m.group(i)) {
						case "DIS":
							Log.i("", "Disease type: " + m.group(i + 1));
							break outer;
						case "INC":
							Log.i("", "Income: $" + Float.parseFloat(m.group(i + 1)) * 1000f);
							break outer;
//						case "HRV":
//							Log.i("", "Harvest: " + (Float.parseFloat(m.group(i + 1)) * 1000f) + "kg");
//							break outer;
					}
				}
				Log.i("", "m.group(" + i + "): " + m.group(i));
			}
		}
//		if (m.find()) {
//			Log.i("", m.group(count++));
		abortBroadcast();
//		}
	}
}
