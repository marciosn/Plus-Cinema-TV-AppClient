package com.app.android.qxd0102.pluscinematv.loadimages;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.android.qxd0102.model.FilmePojo;
import com.app.android.qxd0102.model.FilmesPojo;
import com.app.android.qxd0102.pluscinematv.File;
import com.app.android.qxd0102.pluscinematv.R;

public class AdapterListView extends BaseAdapter{
	
	private LayoutInflater inflater;
	private ArrayList<FilmesPojo> series;
	
	public AdapterListView(Context context, ArrayList<FilmesPojo> series){
		this.series = series;
		inflater = LayoutInflater.from(context);
		
	}
	@Override
	public int getCount() {
		return series.size();
	}

	@Override
	public Object getItem(int position) {
		return series.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		FilmesPojo serie = series.get(position);
		view = inflater.inflate(R.layout.item_list, null);
		((TextView) view.findViewById(R.id.text)).setText(serie.getNome());

		return view;
	}
		

}
