package com.yp.memo.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.yp.memo.R;
import com.yp.memo.dao.MemoDB;
import com.yp.memo.model.Information;

public class EditActivity extends Activity{
	private Intent intent;
	private ActionBar actionBar;
	private EditText editText;
	private Button addPicButton;
	private Button addSoundButton;
	private Button setRemindButton;
	private Information info;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		
		actionBar = this.getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
		
		intent=this.getIntent();
		info=(Information) intent.getSerializableExtra("info");
		
		editText=(EditText)findViewById(R.id.information);
    	editText.setText(info.getMemoInfo());
		
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.add_memu, menu);
		MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.add_memus, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId())
        {
        case android.R.id.home:
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
		case R.id.save:
        	String input=editText.getText().toString();
        	info.setMemoInfo(input);
        	MemoDB m=MemoDB.getInstance(this);
        	int ii=m.saveInformation(info);
        	Log.d("position", ""+ii);
        	finish();
        	
        default:
            
        }
		return super.onOptionsItemSelected(item);
	}
}
