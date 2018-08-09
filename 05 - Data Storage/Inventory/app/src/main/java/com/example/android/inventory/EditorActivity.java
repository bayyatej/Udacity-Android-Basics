package com.example.android.inventory;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;

public class EditorActivity extends AppCompatActivity
{
	private Uri currentUri;

	private EditText mNameEditText,
			mDescriptionEditText,
			mPriceEditText,
			mQuantityEditText;
	private ImageButton mTakePictureButton,
			mAddPhotoButton;

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

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.save_product:
				//todo save product
				break;
			case R.id.delete_product:
				//todo delete product
				break;
		}
		return true;
	}
}
