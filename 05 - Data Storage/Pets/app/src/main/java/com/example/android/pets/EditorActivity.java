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

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
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
	 * Input views
	 */
	private EditText mNameEditText;
	private EditText mBreedEditText;
	private EditText mWeightEditText;
	private Spinner mGenderSpinner;
	/**
	 * Holds the URI of a single pet when editing
	 */
	private Uri mEditPetUri;
	/**
	 * initial pet input data
	 */
	private String mInitialPetName;
	private String mInitialPetBreed;
	private String mInitialPetWeightString;
	private int mInitialGender;
	/**
	 * Holds pet input data
	 */
	private String mPetName;
	private String mPetBreed;
	private String mPetWeightString;
	private int mGender = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editor);

		mEditPetUri = getIntent().getData();
		// Find all relevant views that we will need to read user input from
		mNameEditText = findViewById(R.id.edit_pet_name);
		mBreedEditText = findViewById(R.id.edit_pet_breed);
		mWeightEditText = findViewById(R.id.edit_pet_weight);
		mGenderSpinner = findViewById(R.id.spinner_gender);
		setupSpinner();

		if (mEditPetUri == null)
		{
			this.setTitle(R.string.editor_activity_title_new_pet);
			invalidateOptionsMenu();
		} else
		{
			this.setTitle(R.string.editor_activity_title_edit_pet);
			getLoaderManager().initLoader(0, null, this);
		}
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
				showDeleteConfirmationDialog();
				return true;
			// Respond to a click on the "Up" arrow button in the app bar
			case android.R.id.home:
				// If the pet hasn't changed, continue with navigating up to parent activity
				// which is the {@link CatalogActivity}.
				if (noUnsavedChangesPresent())
				{
					NavUtils.navigateUpFromSameTask(EditorActivity.this);
					return true;
				}

				// Otherwise if there are unsaved changes, setup a dialog to warn the user.
				// Create a click listener to handle the user confirming that
				// changes should be discarded.
				DialogInterface.OnClickListener discardButtonClickListener =
						new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialogInterface, int i)
							{
								// User clicked "Discard" button, navigate to parent activity.
								NavUtils.navigateUpFromSameTask(EditorActivity.this);
							}
						};

				// Show a dialog that notifies the user they have unsaved changes
				showUnsavedChangesDialog(discardButtonClickListener);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu)
	{
		super.onPrepareOptionsMenu(menu);
		// If this is a new pet, hide the "Delete" menu item.
		if (mEditPetUri == null)
		{
			MenuItem menuItem = menu.findItem(R.id.action_delete);
			menuItem.setVisible(false);
		}
		return true;
	}

	@Override
	public void onBackPressed()
	{
		// If the pet hasn't changed, continue with handling back button press
		if (noUnsavedChangesPresent())
		{
			super.onBackPressed();
			return;
		}

		// Otherwise if there are unsaved changes, setup a dialog to warn the user.
		// Create a click listener to handle the user confirming that changes should be discarded.
		DialogInterface.OnClickListener discardButtonClickListener =
				new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialogInterface, int i)
					{
						// User clicked "Discard" button, close the current activity.
						finish();
					}
				};

		// Show dialog that there are unsaved changes
		showUnsavedChangesDialog(discardButtonClickListener);
	}

	/**
	 * Helper method to pull user input from EditorActivity and save input to database
	 */
	private void savePet()
	{
		Uri mInsertResult = null;
		mPetName = mNameEditText.getText().toString().trim();
		mPetBreed = mBreedEditText.getText().toString().trim();
		mPetWeightString = mWeightEditText.getText().toString().trim();
		int petWeight = (TextUtils.isEmpty(mPetWeightString)) ? 0 : Integer.valueOf(mPetWeightString);

		ContentValues petValues = new ContentValues();

		if (editorIsEmpty())
		{
			return;
		} else
		{
			petValues.put(PetsEntry.COLUMN_PET_NAME, mPetName);
			petValues.put(PetsEntry.COLUMN_PET_BREED, mPetBreed);
			petValues.put(PetsEntry.COLUMN_PET_GENDER, mGender);
			petValues.put(PetsEntry.COLUMN_PET_WEIGHT, petWeight);
		}

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
			toastMessage = getString(R.string.pet_save_error);
		} else
		{
			toastMessage = mPetName + " saved successfully";
		}
		Toast toast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);
		toast.show();
	}

	private void showDeleteConfirmationDialog()
	{
		// Create an AlertDialog.Builder and set the message, and click listeners
		// for the postivie and negative buttons on the dialog.
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.delete_alert_msg);
		builder.setPositiveButton(R.string.delete_alert_confirm, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int id)
			{
				// User clicked the "Delete" button, so delete the pet.
				deletePet();
				finish();
			}
		});
		builder.setNegativeButton(R.string.delete_dialog_no, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int id)
			{
				// User clicked the "Cancel" button, so dismiss the dialog
				// and continue editing the pet.
				if (dialog != null)
				{
					dialog.dismiss();
				}
			}
		});

		// Create and show the AlertDialog
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
	}

	/**
	 * Helper method to delete pet if in editor mode
	 */
	private void deletePet()
	{
		String toastMessage;

		if (mEditPetUri != null)
		{
			int deleteResult = getContentResolver().delete(mEditPetUri, null, null);

			if (deleteResult > 0)
			{
				toastMessage = mInitialPetName + " deleted successfully";
			} else
			{
				toastMessage = getString(R.string.delete_error_msg);
			}

			Toast toast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);
			toast.show();
		}
	}

	/**
	 * Helper method to determine whether editor is empty
	 *
	 * @return: true if editor is empty, false otherwise
	 */
	private boolean editorIsEmpty()
	{
		return TextUtils.isEmpty(mPetName) && TextUtils.isEmpty(mPetBreed) && mGender == PetsEntry.GENDER_UNKNOWN;
	}

	/**
	 * Helper method to determine whether unsaved changes are present by comparing current
	 * editor values to previous (initial) values
	 *
	 * @return: true if changes present, false otherwise
	 */
	private boolean noUnsavedChangesPresent()
	{
		mPetName = mNameEditText.getText().toString().trim();
		mPetBreed = mBreedEditText.getText().toString().trim();
		mPetWeightString = mWeightEditText.getText().toString().trim();
		return mPetName.equals(mInitialPetName) && mPetBreed.equals(mInitialPetBreed) && mPetWeightString.equals(mInitialPetWeightString) && mGender == mInitialGender;
	}

	/**
	 * Display alert dialog for unsaved changes
	 *
	 * @param discardButtonClickListener: listener for back button click
	 */
	private void showUnsavedChangesDialog(DialogInterface.OnClickListener discardButtonClickListener)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.unsaved_changes_dialog_msg);
		builder.setPositiveButton(R.string.discard, discardButtonClickListener);
		builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				if (dialog != null)
				{
					dialog.dismiss();
				}
			}
		});

		AlertDialog alertDialog = builder.create();
		alertDialog.show();
	}

	// Loader callbacks
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
		if (data.getCount() > 0)
		{
			data.moveToFirst();
			mNameEditText.setText(data.getString(0));
			mBreedEditText.setText(data.getString(1));
			mGenderSpinner.setSelection(data.getInt(2));
			mWeightEditText.setText(data.getString(3));
		}

		mInitialPetName = mNameEditText.getText().toString().trim();
		mInitialPetBreed = mBreedEditText.getText().toString().trim();
		mInitialPetWeightString = mPetWeightString = mWeightEditText.getText().toString().trim();
		mInitialGender = mGenderSpinner.getSelectedItemPosition();
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
