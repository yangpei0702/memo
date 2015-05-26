package com.yp.memo.util;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yp.memo.R;
import com.yp.memo.model.Information;

public class InformationAdapter extends BaseAdapter{
	
	private List<Information> mList;
	private LayoutInflater mInflater;
	
	
	
	public InformationAdapter() {
	}
	public InformationAdapter(Context context,List<Information> list){
		mList=list;
		mInflater=LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView==null){
			convertView=mInflater.inflate(R.layout.item, null);
		}
		TextView content =(TextView)convertView.findViewById(R.id.tv_content);
		TextView createDate =(TextView)convertView.findViewById(R.id.tv_createDate);
		Information info=mList.get(position);
		content.setText(info.getMemoInfo());
		createDate.setText(info.getCreateDate());
		return convertView;
	}

}
