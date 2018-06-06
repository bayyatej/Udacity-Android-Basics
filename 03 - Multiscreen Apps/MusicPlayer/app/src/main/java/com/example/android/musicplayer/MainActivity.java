package com.example.android.musicplayer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
	private MediaPlayer mediaPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mediaPlayer = MediaPlayer.create(this, R.raw.kendrick);
	}

	public void play(View view)
	{
		mediaPlayer.start();
	}

	public void pause(View view)
	{
		mediaPlayer.pause();
	}

	public void restart(View view)
	{
		mediaPlayer.seekTo(0);
		mediaPlayer.start();
	}
}
