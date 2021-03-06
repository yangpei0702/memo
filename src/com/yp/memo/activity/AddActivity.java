package com.yp.memo.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
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
import android.widget.Toast;

import com.yp.memo.R;
import com.yp.memo.broadcastreceiver.AlarmReceiver;
import com.yp.memo.dao.MemoDB;
import com.yp.memo.model.Information;
import com.yp.memo.model.Resource;
import com.yp.memo.model.TimeInfo;
import com.yp.memo.util.CurrentTime;

public class AddActivity extends Activity implements OnClickListener {
	public static final int TAKE_PHOTO = 1;
	public static final int CROP_PHOTO = 2;
	public static final int TAKE_AUDIO = 3;
	public static final int TAKE_Remind_Time = 4;
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
	private Button playAudio;
	private Button stopAudio;
	private MediaPlayer mPlayer = null;
	private TimeInfo timeInfo;
	private Information info;
	private Resource resource;
	private List<Resource> list;
	private MemoDB m;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		actionBar = this.getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("");

		addPicButton = (Button) findViewById(R.id.addPic);
		addSoundButton = (Button) findViewById(R.id.addSound);
		//setRemindButton = (Button) findViewById(R.id.setRemind);
		picture = (ImageView) findViewById(R.id.picImage);
		playAudio = (Button) findViewById(R.id.playAudio);
		playAudio.setVisibility(View.GONE);
		stopAudio = (Button) findViewById(R.id.stopAudio);
		stopAudio.setVisibility(View.GONE);
		editText = (EditText) findViewById(R.id.information);
		
		addPicButton.setOnClickListener(this);
		addSoundButton.setOnClickListener(this);
		//setRemindButton.setOnClickListener(this);
		playAudio.setOnClickListener(this);
		stopAudio.setOnClickListener(this);

		info = new Information();
		
		m = MemoDB.getInstance(this);
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
			
			AlertDialog.Builder dialog = new AlertDialog.Builder(
					AddActivity.this);
			dialog.setTitle("提示");
			dialog.setMessage("是否添加提醒？");
			dialog.setCancelable(false);
			dialog.setPositiveButton("是",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							String input = editText.getText().toString();
							info.setMemoInfo(input);
							list = new ArrayList<Resource>();
							resource = new Resource();
							if (mPhotoPath != null && mPhotoPath != "") {
								resource.setFileName(picFileName);
								resource.setFilePath(mPhotoPath);
								list.add(resource);
							}
							if (mAudioPath != null && mAudioPath != "") {
								resource.setFileName(audioFileName);
								resource.setFilePath(mAudioPath);
								list.add(resource);
							}

							Intent calendarIntent = new Intent(AddActivity.this, CalendarActivity.class);
							startActivityForResult(calendarIntent, TAKE_Remind_Time);
						}
					});
			dialog.setNegativeButton("否",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							String input = editText.getText().toString();
							info.setMemoInfo(input);
							list = new ArrayList<Resource>();
							resource = new Resource();
							if (mPhotoPath != null && mPhotoPath != "") {
								resource.setFileName(picFileName);
								resource.setFilePath(mPhotoPath);
								list.add(resource);
							}
							if (mAudioPath != null && mAudioPath != "") {
								resource.setFileName(audioFileName);
								resource.setFilePath(mAudioPath);
								list.add(resource);
							}
							int ii = m.saveInformation(info, list);
							Log.d("position", "" + ii);
							Toast.makeText(getApplicationContext(), "保存成功",
									Toast.LENGTH_SHORT).show();
							finish();
						}
					});
			dialog.show();

			
			

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
		case R.id.addSound:
			audioFileName = CurrentTime.getAudioFileName();
			mAudioPath = "mnt/sdcard/Music/" + audioFileName;
			Intent intentAudio = new Intent(this, RecordActivity.class);
			intentAudio.putExtra("Path", mAudioPath);
			startActivityForResult(intentAudio, TAKE_AUDIO);
			break;
		/*case R.id.setRemind:
			
			break;*/
		case R.id.playAudio:
			try {
				mPlayer = new MediaPlayer();
				mPlayer.setDataSource(mAudioPath);
				mPlayer.prepare();
				mPlayer.start();
				playAudio.setText("播放中");
				mPlayer.setOnCompletionListener(new OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mPlayer) {
						playAudio.setText("播放");
						mPlayer.release();
						mPlayer = null;
					}
				});
			} catch (IOException e) {
				Log.e("LOG_TAG", "播放失败", e);
			}

			break;
		case R.id.stopAudio:
			mPlayer.release();
			mPlayer = null;
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
		case TAKE_AUDIO:
			if (data.getStringExtra("result").equals("OK")) {
				// mPlayer = new MediaPlayer();
				playAudio.setVisibility(View.VISIBLE);
				stopAudio.setVisibility(View.VISIBLE);
			} else {
				Log.d("message", "声音未录制");
			}
			break;
		case TAKE_Remind_Time:
			timeInfo = new TimeInfo();
			timeInfo = (TimeInfo) data.getSerializableExtra("timeInfo");
			setReminder(true);
			
			int ii = m.saveInformation(info, list);
			Log.d("position", "" + ii);
			Toast.makeText(getApplicationContext(), "保存成功",
					Toast.LENGTH_SHORT).show();
			finish();
		default:
			break;
		}
	}

	private void setReminder(boolean b) {
		Calendar c = Calendar.getInstance();
		c.set(timeInfo.getmYear(), timeInfo.getmMonth(), timeInfo.getmDay(),
				timeInfo.getmHour(), timeInfo.getmMinute(),
				timeInfo.getmSecond());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String remindTime = df.format(c.getTime());
		info.setRemindDate(remindTime);
		// get the AlarmManager instance
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		// create a PendingIntent that will perform a broadcast
		Intent remindIntent = new Intent(this, AlarmReceiver.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("info", info);
		remindIntent.putExtras(bundle);
		PendingIntent pi = PendingIntent.getBroadcast(AddActivity.this, 0,
				remindIntent,PendingIntent.FLAG_UPDATE_CURRENT);

		if (b) {
			// schedule an alarm
			am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
		} else {
			// cancel current alarm
			am.cancel(pi);
		}

	}

}
