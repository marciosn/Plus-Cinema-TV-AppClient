package com.app.android.qxd0102.pluscinematv.loadimages;

import java.util.ArrayList;

import com.app.android.qxd0102.model.FilmePojo;
import com.app.android.qxd0102.pluscinematv.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterGridViewFilmes extends BaseAdapter{
	
	private LayoutInflater inflater;
	private ArrayList<FilmePojo> filmes;
	
	public AdapterGridViewFilmes(Context context, ArrayList<FilmePojo> filmes) {
		this.filmes = filmes;
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return filmes.size();
	}

	@Override
	public Object getItem(int position) {
		return filmes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		FilmePojo filme = filmes.get(position);
		view = inflater.inflate(R.layout.item_grid, null);
		((ImageView) view.findViewById(R.id.gridViewFilmes)).setImageResource(position);
		
		return view;
	}
	
}
