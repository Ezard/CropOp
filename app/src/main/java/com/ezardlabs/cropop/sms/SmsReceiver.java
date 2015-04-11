package com.ezardlabs.cropop.sms;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.util.Log;

import com.ezardlabs.cropop.DBManager;

import java.lang.Integer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmsReceiver extends BroadcastReceiver {

	public void onReceive(Context context, Intent intent) {
		Bundle pdusBundle = intent.getExtras();
		Object[] pdus = (Object[]) pdusBundle.get("pdus");
		SmsMessage messages = SmsMessage.createFromPdu((byte[]) pdus[0]);
		Log.i("", messages.getMessageBody() + ", " + messages.getOriginatingAddress());
		Pattern pattern = Pattern.compile("(?:(DIS) ((?:A[1-3])|UNKNOWN))|(?:(INC) ([0-9]+(?:\\.[0-9]+)?))|(?:(HRV)\\s(([0-9]+(?:\\.[0-9]+)?C[1-3]\\s?)+))");
		Matcher m = pattern.matcher(messages.getMessageBody());
		while (m.find()) {
			outer:
			for (int i = 1; i < m.groupCount(); i++) {
				if (m.group(i) != null) {
					switch (m.group(i)) {
						case "DIS":
							Log.i("", "Disease type: " + m.group(i + 1));
							DBManager.addDisease(messages.getOriginatingAddress(), m.group(i + 1));
							if (m.group(i + 1).equals("UNKNOWN")) {
								createNotification(context, "Unknown disease reported");
							}
							break outer;
						case "INC":
							Log.i("", "Income: $" + Float.parseFloat(m.group(i + 1)) * 1000f);
							DBManager.addIncome(messages.getOriginatingAddress(), Float.parseFloat(m.group(i + 1)) * 1000f);
							break outer;
						case "HRV":
							String[] split = m.group(i + 1).split(" ");
							for (String s : split) {
								String[] split2 = s.split("C");
								DBManager.addHarvest(messages.getOriginatingAddress(), Float.parseFloat(split2[0]) * 1000f, Integer.parseInt(split2[1]));
							}
							break outer;
                        case "PST":
                            Log.i("", "Pesticide used:" + Float.parseFloat(m.group(i+1)));
                            String[] split = m.group(i+1).split(" ");
                            for (String s:split) {
                                String[] split2 = s.split("P");
                                DBManager.addPesticide(messages.getOriginatingAddress(), Float.parseFloat(split2[0], Integer.parseInt(split2[1])));
                            }
					}
				}
				Log.i("", "m.group(" + i + "): " + m.group(i));
			}
		}
		abortBroadcast();
	}

	private void createNotification(Context context, String description) {
		((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE))
				.notify(1234, new NotificationCompat.Builder(context).setContentTitle("CropOp Alert").setContentText(description).build());
	}
}
