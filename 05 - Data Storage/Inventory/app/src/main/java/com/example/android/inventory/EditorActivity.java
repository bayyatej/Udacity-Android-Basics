package com.example.android.inventory;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class EditorActivity extends AppCompatActivity
{
	private Uri currentUri = null;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editor);

		try
		{
			currentUri = getIntent().getData();
		} catch (NullPointerException e)
		{
			currentUri = null;
		}
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		Log.i("In Editor Activity", "onStart: ");
	}
}
