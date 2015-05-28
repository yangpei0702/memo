package com.yp.memo.activity;

import java.io.IOException;

import com.yp.memo.R;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RecordActivity extends Activity implements OnClickListener {
	private static final String LOG_TAG = "AudioRecordTest";
	public static final int TAKE_AUDIO = 3;
	// �����ļ�����·��
	private String fileName = null;
	// ����ؼ�
	private Button startRecord;
	private Button stopRecord;
	private Button saveFile;

	private Intent thisIntent;
	// ������������
	private MediaRecorder mRecorder = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record);

		// ��ʼ¼��
		startRecord = (Button) findViewById(R.id.startRecord);
		startRecord.setOnClickListener(this);
		// ����¼��
		stopRecord = (Button) findViewById(R.id.stopRecord);
		stopRecord.setOnClickListener(this);

		saveFile = (Button) findViewById(R.id.saveFile);
		saveFile.setOnClickListener(this);

		thisIntent = this.getIntent();

		// ����sdcard��·��
		fileName = thisIntent.getStringExtra("Path");

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		finish();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.startRecord:
			mRecorder = new MediaRecorder();
			mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			mRecorder.setOutputFile(fileName);
			mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			try {
				mRecorder.prepare();
			} catch (IOException e) {
				Log.e(LOG_TAG, "prepare() failed", e);
			}
			mRecorder.start();
			break;

		case R.id.stopRecord:
			mRecorder.stop();
			mRecorder.release();
			mRecorder = null;

			break;
		case R.id.saveFile:
			Intent intent = new Intent();
			intent.putExtra("result", "OK");
			// ���ý���������д���
			this.setResult(TAKE_AUDIO, intent);
			finish();
			break;
		}

	}
}
