package com.app.android.qxd0102.pluscinematv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.app.android.qxd0102.pluscinematv.loadimages.ImageLoader;

public class VerImagem extends Activity {
	private String url;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ver_imagem);
		
		Intent i = getIntent();
		url = i.getExtras().getString("url");
		int loader = R.drawable.loader;
	    ImageView image = (ImageView) findViewById(R.id.verImagem);
	    ImageLoader imgLoader = new ImageLoader(getApplicationContext());
	    imgLoader.DisplayImage(url, loader, image);
	}
}
