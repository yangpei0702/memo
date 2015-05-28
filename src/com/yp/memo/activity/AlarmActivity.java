package com.yp.memo.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Audio;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yp.memo.R;
import com.yp.memo.model.Information;

public class AlarmActivity extends Activity{
	private Intent intent;
	public static final int NOTIFICATION_ID=1;   
    
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
         super.onCreate(savedInstanceState);  
         setContentView(R.layout.activity_alarm);  
          
         intent = this.getIntent();
         Information info=(Information) intent.getSerializableExtra("info");
         
        final NotificationManager nm=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);  
        Notification n=new Notification();  
         
        n.sound=Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "20"); 
        
        // Post a notification to be shown in the status bar  
        nm.notify(NOTIFICATION_ID, n);  
          
        /* display some information */  
        TextView tv=(TextView)findViewById(R.id.tvNotification);  
        tv.setText(info.getMemoInfo());
          
        /* the button by which you can cancel the alarm */  
        Button btnCancel=(Button)findViewById(R.id.btnCancel);  
        btnCancel.setOnClickListener(new View.OnClickListener() {  
              
            @Override  
            public void onClick(View arg0) {  
                nm.cancel(NOTIFICATION_ID);  
                finish();  
            }  
        });  
    }  
	
}
