package com.example.android.inventory;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.android.inventory.data.InventoryContract.InventoryEntry;

import java.io.ByteArrayOutputStream;

public class InventoryActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>
{
	private InventoryAdapter mInventoryAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventory);
		mInventoryAdapter = new InventoryAdapter(this, null, 0);
		FloatingActionButton fab = findViewById(R.id.fab);

		fab.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getBaseContext(), EditorActivity.class);
				startActivity(intent);
			}
		});

		ListView inventoryList = findViewById(R.id.inventory_list);
		inventoryList.setAdapter(mInventoryAdapter);
		inventoryList.setEmptyView(findViewById(R.id.empty));

		getSupportLoaderManager().initLoader(0, null, this);
	}

	/*
		Inventory Methods
	 */
	private void insertDummyData()
	{
		ContentValues dummyValues = new ContentValues();
		dummyValues.put(InventoryEntry.COLUMN_PRODUCT_NAME, "Meh");
		dummyValues.put(InventoryEntry.COLUMN_PRODUCT_DESCRIPTION, "sexy mofo");
		dummyValues.put(InventoryEntry.COLUMN_PRODUCT_PRICE, Integer.MAX_VALUE);
		dummyValues.put(InventoryEntry.COLUMN_PRODUCT_QUANTITY, 1);

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		Bitmap dummyPic = BitmapFactory.decodeResource(this.getResources(), R.drawable.option1);
		dummyPic.compress(Bitmap.CompressFormat.JPEG, 50, stream);
		dummyValues.put(InventoryEntry.COLUMN_PRODUCT_PICTURE, stream.toByteArray());
		getContentResolver().insert(InventoryEntry.CONTENT_URI, dummyValues);
	}

	private void deleteAllData()
	{
		getContentResolver().delete(InventoryEntry.CONTENT_URI, null, null);
	}

	/*
		Option Menu Methods
	 */

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_inventory_activity, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.insert_dummy_data:
				insertDummyData();
				return true;
			case R.id.delete_all_inventory:
				deleteAllData();
				return true;
		}

		return super.onOptionsItemSelected(item);
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
		mInventoryAdapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(@NonNull Loader<Cursor> loader)
	{
		mInventoryAdapter.swapCursor(null);
	}
}
