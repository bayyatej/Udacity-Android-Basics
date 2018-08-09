package com.example.android.inventory;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class EditorActivity extends AppCompatActivity
{
	private Uri currentUri;

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
}
