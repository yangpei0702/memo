package com.yp.memo.activity;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.yp.memo.R;
import com.yp.memo.dao.MemoDB;
import com.yp.memo.model.Information;
import com.yp.memo.model.Resource;
import com.yp.memo.util.CurrentTime;

public class AddActivity extends Activity implements OnClickListener{
	public static final int TAKE_PHOTO = 1;
	public static final int CROP_PHOTO = 2;
	private ActionBar actionBar;
	private EditText editText;
	private Button addPicButton;
	private Button addSoundButton;
	private Button setRemindButton;
	private ImageView picture;
	private Uri imageUri;
	private String mPhotoPath;
	private String picFileName;
	private String mAudioPath;
	private String audioFileName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		actionBar = this.getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
        
        addPicButton=(Button) findViewById(R.id.addPic);
        addSoundButton=(Button) findViewById(R.id.addSound);
        setRemindButton=(Button) findViewById(R.id.setRemind);
        picture=(ImageView) findViewById(R.id.picImage);
        
        addPicButton.setOnClickListener(this);
        addSoundButton.setOnClickListener(this);
        setRemindButton.setOnClickListener(this);
        
        
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
            finish();
            return true;
		case R.id.save:
        	editText=(EditText)findViewById(R.id.information);
        	String input=editText.getText().toString();
        	
        	
        	Information info=new Information();
        	List<Resource> list= new ArrayList<Resource>();
        	
        	Resource resource=new Resource();
        	
        	
        	
        	info.setMemoInfo(input);
        	
        	if(mPhotoPath!=null&&mPhotoPath!=""){
        		resource.setFileName(picFileName);
            	resource.setFilePath(mPhotoPath);
            	list.add(resource);
        	}
        	
        	
        	
        	MemoDB m=MemoDB.getInstance(this);
        	int ii=m.saveInformation(info,list);
        	Log.d("position", ""+ii);
        	finish();
        	
        default:
            
        }
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.addPic:
			picFileName=CurrentTime.getPhotoFileName();
			mPhotoPath = "mnt/sdcard/DCIM/Camera/" + picFileName;
			File outputImage = new File(mPhotoPath);
			try {
				if (outputImage.exists()) {
					outputImage.delete();
				}
				outputImage.createNewFile();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			imageUri = Uri.fromFile(outputImage);
			Log.d("imageUri", imageUri.toString());
			Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			startActivityForResult(intent, TAKE_PHOTO);// 启动相机程序
		break;
		
		}
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case TAKE_PHOTO:
			if (resultCode == RESULT_OK) {
				Intent intent = new Intent("com.android.camera.action.CROP");
				intent.setDataAndType(imageUri, "image/*");
				intent.putExtra("scale", true);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(intent, CROP_PHOTO); // 启动裁剪程序
			}
			break;
		case CROP_PHOTO:
			if (resultCode == RESULT_OK) {
				try {
					Bitmap bitmap = BitmapFactory
							.decodeStream(getContentResolver().openInputStream(
									imageUri));
					picture.setImageBitmap(bitmap); // 将裁剪后的照片显示出来
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			break;
		default:
			break;
		}
	}
	
}
