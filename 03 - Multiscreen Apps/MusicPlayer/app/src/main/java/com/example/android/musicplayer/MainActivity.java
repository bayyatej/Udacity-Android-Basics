package com.example.android.musicplayer;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
	private MediaPlayer mediaPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mediaPlayer = MediaPlayer.create(this, R.raw.color_gray);
	}

	public void play(View view)
	{
		final Activity main = this;
		mediaPlayer.start();
		mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
		{
			@Override
			public void onCompletion(MediaPlayer mp)
			{
				Toast toast = Toast.makeText(main, R.string.done, Toast.LENGTH_SHORT);
				toast.show();
			}
		});
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
