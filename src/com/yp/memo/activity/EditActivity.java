package com.yp.memo.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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

public class EditActivity extends Activity implements OnClickListener {
	public static final int TAKE_PHOTO = 1;
	public static final int CROP_PHOTO = 2;
	private Intent intent;
	private ActionBar actionBar;
	private EditText editText;
	private Button addPicButton;
	private Button addSoundButton;
	private Button setRemindButton;
	private Information info;
	private Resource resource;
	private List<Resource> list;
	private String mPhotoPath = "";
	private String newPhotoPath = "";
	private ImageView imageView;
	private String picFileName;
	private Uri imageUri;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);

		actionBar = this.getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("");

		resource = new Resource();

		imageView = (ImageView) findViewById(R.id.picImage);
		addPicButton = (Button) findViewById(R.id.addPic);
		addPicButton.setOnClickListener(this);

		intent = this.getIntent();
		info = (Information) intent.getSerializableExtra("info");
		list = (List<Resource>) intent.getSerializableExtra("resourceList");

		if (list != null && list.size() != 0) {
			addPicButton.setText("修改图片");
			resource = list.get(0);
			mPhotoPath = resource.getFilePath();
			newPhotoPath = mPhotoPath;
			Bitmap bitmap = BitmapFactory.decodeFile(mPhotoPath, null);
			imageView.setImageBitmap(bitmap);
		}

		editText = (EditText) findViewById(R.id.information);
		editText.setText(info.getMemoInfo());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.add_memu, menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.add_memus, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			finish();
			return true;
		case R.id.save:
			String input = editText.getText().toString().trim();
			info.setMemoInfo(input);
			MemoDB m = MemoDB.getInstance(this);

			if (!newPhotoPath.equals(mPhotoPath)) {

				resource.setFileName(picFileName);

				resource.setFilePath(newPhotoPath);

				list.add(resource);
			}

			int p = m.updateInformation(info, list);

			Log.d("position", "" + p);
			finish();

		default:

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.addPic:
			picFileName = CurrentTime.getPhotoFileName();

			newPhotoPath = "mnt/sdcard/DCIM/Camera/" + picFileName;

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
					imageView.setImageBitmap(bitmap); // 将裁剪后的照片显示出来
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
