package com.yp.memo.broadcastreceiver;

import com.yp.memo.activity.AlarmActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
        intent.setClass(context, AlarmActivity.class);  
        context.startActivity(intent);  
	}

}
