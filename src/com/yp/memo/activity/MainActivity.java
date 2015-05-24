package com.yp.memo.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.yp.memo.R;
import com.yp.memo.model.Information;
import com.yp.memo.util.InformationAdapter;


public class MainActivity extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = this.getActionBar();
        actionBar.setTitle("所有信息");
        List<Information> list= new ArrayList<Information>();
        for(int i=0;i<20;i++){
        	list.add(new Information(i, "This is content"+i, i, "", i, ""));
        }
        ListView lv = (ListView)findViewById(R.id.lv_main);
        lv.setAdapter(new InformationAdapter(this,list));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    	switch(item.getItemId()){
    	case R.id.action_settings:
    		break;
    	case R.id.add:
    		Intent intent=new Intent(this,AddActivity.class);
    		startActivity(intent);
    		break;
    	default:
    		break;
    	}
        
        
        return super.onOptionsItemSelected(item);
    }
}
