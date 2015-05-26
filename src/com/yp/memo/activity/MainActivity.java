package com.yp.memo.activity;

import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.yp.memo.R;
import com.yp.memo.dao.MemoDB;
import com.yp.memo.model.Information;
import com.yp.memo.util.InformationAdapter;


public class MainActivity extends Activity{
	private MemoDB m=null;
	private List<Information> list=null;
	private ListView lv;
	private InformationAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = this.getActionBar();
        actionBar.setTitle("所有信息");
        initListView();
        
    }

    @Override
	protected void onResume() {
		super.onResume();
		initListView();
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.main, menu);
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		m.closeDB();
	}
	
	public void initListView(){
		m=MemoDB.getInstance(this);
        list=m.loadProvinces();
        lv = (ListView)findViewById(R.id.lv_main);
        
        listAdapter=new InformationAdapter(this,list);
        lv.setAdapter(listAdapter);
        
        lv.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> arg0, View arg1,int arg2,
                    long arg3) {
        		Information info=new Information();
        		info=list.get(arg2);
        		Intent intent=new Intent();
        		intent.setClass(MainActivity.this,ViewActivity.class);
        		Bundle bundle=new Bundle();
        		bundle.putSerializable("info", info);
        		intent.putExtras(bundle);
        		MainActivity.this.startActivity(intent);
        		
        	}
		});
	}
	
    
}
