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
import android.widget.TextView;

import com.example.android.pets.data.PetContract.PetsEntry;
import com.example.android.pets.data.PetDBHelper;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity
{
	private PetDBHelper mDbHelper;

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
		mDbHelper = new PetDBHelper(this);
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

		Cursor cursor = getContentResolver().query(PetsEntry.CONTENT_URI, projection, null, null, null, null);

		try
		{
			// Display the number of rows in the Cursor (which reflects the number of rows in the
			// pets table in the database).
			TextView displayView = findViewById(R.id.text_view_pet);
			String displayMsg = getString(R.string.num_pets) + cursor.getCount();
			displayMsg += ("\n\n" + PetsEntry._ID + "-" + PetsEntry.COLUMN_PET_NAME + "-" + PetsEntry.COLUMN_PET_BREED + "-" + PetsEntry.COLUMN_PET_GENDER + "-" + PetsEntry.COLUMN_PET_WEIGHT);
			for (int i = 0; i < cursor.getCount(); i++)
			{
				displayMsg += "\n\n";
				cursor.moveToPosition(i);
				displayMsg += cursor.getInt(0) + "-";
				displayMsg += cursor.getString(1) + "-";
				displayMsg += cursor.getString(2) + "-";
				displayMsg += cursor.getInt(3) + "-";
				displayMsg += cursor.getInt(4);
			}
			displayMsg.trim();
			displayView.setText(displayMsg);
		} finally
		{
			// Always close the cursor when you're done reading from it. This releases all its
			// resources and makes it invalid.
			cursor.close();
		}
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
				// Do nothing for now
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
