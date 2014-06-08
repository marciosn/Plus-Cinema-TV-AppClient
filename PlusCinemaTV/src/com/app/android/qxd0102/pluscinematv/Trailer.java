package com.app.android.qxd0102.pluscinematv;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

public class Trailer extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{
	private final static String Api_Key = "AIzaSyCj3Mxedk4eG9YXpYNrF1IC_bKGXdIDIdI";
	private String ID_VIDEO;
	private YouTubePlayerView youtube;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trailer);
		
		Intent i = getIntent();
		ID_VIDEO = i.getExtras().getString("idVideo");
		
		youtube = (YouTubePlayerView) findViewById(R.id.youtube);
		youtube.initialize(Api_Key, this);
	}

	@Override
	public void onInitializationFailure(Provider provider, YouTubeInitializationResult error) {
		Toast.makeText(this, "onInitializationFailure()", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean loadAgain) {
		if(!loadAgain){
			player.cueVideo(ID_VIDEO);
		}
	}
}
