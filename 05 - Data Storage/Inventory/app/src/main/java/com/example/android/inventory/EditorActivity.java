package com.example.android.inventory;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

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
			this.setTitle("Edit Product");
		} catch (NullPointerException e)
		{
			this.setTitle("Add Product");
		}
	}

	/*
		Option Menu Methods
	 */

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_editor_activity, menu);
		return true;
	}
}
