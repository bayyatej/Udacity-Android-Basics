package com.example.android.pets;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.pets.data.PetContract.PetsEntry;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>
{
	private PetCursorAdapter mCursorAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_catalog);

		// Setup FAB to open EditorActivity
		FloatingActionButton fab = findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
				startActivity(intent);
			}
		});

		// Setup empty view
		ListView petList = findViewById(R.id.pet_list);
		petList.setEmptyView(findViewById(R.id.empty_view));

		// Setup adapter and attach to list view
		mCursorAdapter = new PetCursorAdapter(this, null, 0);
		petList.setAdapter(mCursorAdapter);

		// Add onClickListener
		petList.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
				intent.setData(ContentUris.withAppendedId(PetsEntry.CONTENT_URI, id));
				startActivity(intent);
			}
		});

		getLoaderManager().initLoader(0, null, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu options from the res/menu/menu_catalog.xml file.
		// This adds menu items to the app bar.
		getMenuInflater().inflate(R.menu.menu_catalog, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// User clicked on a menu option in the app bar overflow menu
		switch (item.getItemId())
		{
			// Respond to a click on the "Insert dummy data" menu option
			case R.id.action_insert_dummy_data:
				insertDummyData();
				return true;
			// Respond to a click on the "Delete all entries" menu option
			case R.id.action_delete_all_entries:
				getContentResolver().delete(PetsEntry.CONTENT_URI, null, null);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onStart()
	{
		super.onStart();
	}

	@Override
	protected void onStop()
	{
		super.onStop();
	}

	/**
	 * Helper method to insert dummy data in app
	 */
	private void insertDummyData()
	{
		ContentValues totoValues = new ContentValues();
		totoValues.put(PetsEntry.COLUMN_PET_NAME, "Toto");
		totoValues.put(PetsEntry.COLUMN_PET_BREED, "Terrier");
		totoValues.put(PetsEntry.COLUMN_PET_GENDER, 1);
		totoValues.put(PetsEntry.COLUMN_PET_WEIGHT, 7);
		getContentResolver().insert(PetsEntry.CONTENT_URI, totoValues);
	}

	@Override
	public CursorLoader onCreateLoader(int id, Bundle args)
	{
		String[] projection = {
				PetsEntry._ID,
				PetsEntry.COLUMN_PET_NAME,
				PetsEntry.COLUMN_PET_BREED
		};
		return new CursorLoader(this, PetsEntry.CONTENT_URI, projection, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data)
	{
		mCursorAdapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader loader)
	{
		mCursorAdapter.swapCursor(null);
	}
}
