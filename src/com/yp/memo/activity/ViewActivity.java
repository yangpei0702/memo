package com.yp.memo.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.yp.memo.R;
import com.yp.memo.dao.MemoDB;
import com.yp.memo.model.Information;

public class ViewActivity extends Activity implements OnClickListener{
	private ActionBar actionBar;
	private TextView  textView;
	private Intent intent;
	private Button deleteButton;
	private Button editButton;
	private Information info;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view);
		
		actionBar = this.getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
        
        intent=this.getIntent();
        
        info=(Information) intent.getSerializableExtra("info");
       
        textView=(TextView) findViewById(R.id.viewText);
        textView.setText(info.getMemoInfo());
        
        deleteButton=(Button) findViewById(R.id.delete);
        deleteButton.setOnClickListener(this);
        
        editButton=(Button) findViewById(R.id.edit);
        editButton.setOnClickListener(this);
        
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId())
        {
        case android.R.id.home:
            Intent intent = new Intent(this, MainActivity.class);
/*            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
*/            startActivity(intent);
            return true;
        default:
            
        }
		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.delete:
			MemoDB m=MemoDB.getInstance(this);
			m.deleteInformation(info);
			finish();
			break;
		case R.id.edit:
			Intent intent=new Intent();
    		intent.setClass(ViewActivity.this,EditActivity.class);
    		Bundle bundle=new Bundle();
    		bundle.putSerializable("info", info);
    		intent.putExtras(bundle);
    		ViewActivity.this.startActivity(intent);
			
			
			break;
		}
	}
	
}
