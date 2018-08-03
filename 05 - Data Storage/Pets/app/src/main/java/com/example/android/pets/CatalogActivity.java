package com.example.android.pets;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.android.pets.data.PetContract.PetsEntry;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity
{
	private Cursor mCursor;

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
		displayDatabaseInfo();
	}

	/**
	 * Temporary helper method to display information in the onscreen TextView about the state of
	 * the pets database.
	 */
	private void displayDatabaseInfo()
	{
		// Perform this raw SQL query "SELECT * FROM pets"
		// to get a Cursor that contains all rows from the pets table.
		String[] projection = {
				PetsEntry._ID,
				PetsEntry.COLUMN_PET_NAME,
				PetsEntry.COLUMN_PET_BREED,
				PetsEntry.COLUMN_PET_GENDER,
				PetsEntry.COLUMN_PET_WEIGHT
		};
		mCursor = getContentResolver().query(PetsEntry.CONTENT_URI, projection, null, null, null, null);
		PetCursorAdapter petCursorAdapter = new PetCursorAdapter(this, mCursor, 0);

		ListView petList = findViewById(R.id.pet_list);
		petList.setEmptyView(findViewById(R.id.empty_view));
		petList.setAdapter(petCursorAdapter);
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
				displayDatabaseInfo();
				return true;
			// Respond to a click on the "Delete all entries" menu option
			case R.id.action_delete_all_entries:
				getContentResolver().delete(PetsEntry.CONTENT_URI, null, null);
				displayDatabaseInfo();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		displayDatabaseInfo();
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		mCursor.close();
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
}
