/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.pets;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.pets.data.PetContract.PetsEntry;

/**
 * Allows user to create a new pet or edit an existing one.
 */
public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>
{
	/**
	 * EditText field to enter the pet's name
	 */
	private EditText mNameEditText;
	/**
	 * EditText field to enter the pet's breed
	 */
	private EditText mBreedEditText;
	/**
	 * EditText field to enter the pet's weight
	 */
	private EditText mWeightEditText;
	/**
	 * EditText field to enter the pet's gender
	 */
	private Spinner mGenderSpinner;
	/**
	 * Gender of the pet. The possible values are:
	 * 0 for unknown gender, 1 for male, 2 for female.
	 */
	private int mGender = 0;
	/**
	 * Holds the URI of a single pet when editing
	 */
	private Uri mEditPetUri;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editor);

		// Find all relevant views that we will need to read user input from
		mNameEditText = findViewById(R.id.edit_pet_name);
		mBreedEditText = findViewById(R.id.edit_pet_breed);
		mWeightEditText = findViewById(R.id.edit_pet_weight);
		mGenderSpinner = findViewById(R.id.spinner_gender);

		mEditPetUri = getIntent().getData();

		if (mEditPetUri == null)
		{
			this.setTitle(R.string.editor_activity_title_new_pet);
		} else
		{
			this.setTitle(R.string.editor_activity_title_edit_pet);
			getLoaderManager().initLoader(0, null, this);
		}

		setupSpinner();
	}

	/**
	 * Setup the dropdown spinner that allows the user to select the gender of the pet.
	 */
	private void setupSpinner()
	{
		// Create adapter for spinner. The list options are from the String array it will use
		// the spinner will use the default layout
		ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
				R.array.array_gender_options, android.R.layout.simple_spinner_item);

		// Specify dropdown layout style - simple list view with 1 item per line
		genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

		// Apply the adapter to the spinner
		mGenderSpinner.setAdapter(genderSpinnerAdapter);

		// Set the integer mSelected to the constant values
		mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				String selection = (String) parent.getItemAtPosition(position);
				if (!TextUtils.isEmpty(selection))
				{
					if (selection.equals(getString(R.string.gender_male)))
					{
						mGender = PetsEntry.GENDER_MALE; // Male
					} else if (selection.equals(getString(R.string.gender_female)))
					{
						mGender = PetsEntry.GENDER_FEMALE; // Female
					} else
					{
						mGender = PetsEntry.GENDER_UNKNOWN; // Unknown
					}
				}
			}

			// Because AdapterView is an abstract class, onNothingSelected must be defined
			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
				mGender = 0; // Unknown
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu options from the res/menu/menu_editor.xml file.
		// This adds menu items to the app bar.
		getMenuInflater().inflate(R.menu.menu_editor, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// User clicked on a menu option in the app bar overflow menu
		switch (item.getItemId())
		{
			// Respond to a click on the "Save" menu option
			case R.id.action_save:
				savePet();
				finish();
				return true;
			// Respond to a click on the "Delete" menu option
			case R.id.action_delete:
				// Do nothing for now
				return true;
			// Respond to a click on the "Up" arrow button in the app bar
			case android.R.id.home:
				// Navigate back to parent activity (CatalogActivity)
				NavUtils.navigateUpFromSameTask(this);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Helper method to pull user input from EditorActivity and save input to database
	 */
	private void savePet()
	{
		Uri mInsertResult = null;
		String petName = mNameEditText.getText().toString().trim();
		String petBreed = mBreedEditText.getText().toString().trim();
		int petGender = mGender;
		int petWeight = Integer.valueOf(mWeightEditText.getText().toString().trim());
		ContentValues petValues = new ContentValues();

		petValues.put(PetsEntry.COLUMN_PET_NAME, petName);
		petValues.put(PetsEntry.COLUMN_PET_BREED, petBreed);
		petValues.put(PetsEntry.COLUMN_PET_GENDER, petGender);
		petValues.put(PetsEntry.COLUMN_PET_WEIGHT, petWeight);

		if (mEditPetUri == null)
		{
			mInsertResult = getContentResolver().insert(PetsEntry.CONTENT_URI, petValues);
		} else
		{
			getContentResolver().update(mEditPetUri, petValues, null, null);
		}

		String toastMessage;
		if (mInsertResult == null && mEditPetUri == null)
		{
			toastMessage = "Error with saving pet";
		} else
		{
			toastMessage = "Pet saved with id: 1";
		}
		Toast toast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);
		toast.show();
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args)
	{
		String[] projection = {
				PetsEntry.COLUMN_PET_NAME,
				PetsEntry.COLUMN_PET_BREED,
				PetsEntry.COLUMN_PET_GENDER,
				PetsEntry.COLUMN_PET_WEIGHT
		};

		return new CursorLoader(this, mEditPetUri, projection, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data)
	{
		data.moveToFirst();
		mNameEditText.setText(data.getString(0));
		mBreedEditText.setText(data.getString(1));
		mGenderSpinner.setSelection(data.getInt(2));
		mWeightEditText.setText(data.getString(3));
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader)
	{
		mNameEditText.setText("");
		mBreedEditText.setText("");
		mGenderSpinner.setSelection(0);
		mWeightEditText.setText("");
	}
}
