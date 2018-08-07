package com.example.android.inventory;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import com.example.android.inventory.data.InventoryContract.InventoryEntry;

public class InventoryActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventory);
	}

	/*
		Loader Callback Methods
	 */
	@NonNull
	@Override
	public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args)
	{
		return new CursorLoader(this, InventoryEntry.CONTENT_URI, null, null, null, null);
	}

	@Override
	public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data)
	{
		//todo cursorAdapter.swap(data)
	}

	@Override
	public void onLoaderReset(@NonNull Loader<Cursor> loader)
	{
		//todo cursorAdapter.swap(data)
	}
}
