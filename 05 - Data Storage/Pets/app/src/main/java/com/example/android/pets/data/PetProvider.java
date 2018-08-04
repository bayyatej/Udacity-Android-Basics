package com.example.android.pets.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.pets.data.PetContract.PetsEntry;

/**
 * {@link ContentProvider} for Pets app.
 */
public class PetProvider extends ContentProvider
{

	/**
	 * URI matcher code for the content URI for the pets table
	 */
	private static final int PETS = 100;

	/**
	 * URI matcher code for the content URI for a single pet in the pets table
	 */
	private static final int PET_ID = 101;

	/**
	 * UriMatcher object to match a content URI to a corresponding code.
	 * The input passed into the constructor represents the code to return for the root URI.
	 * It's common to use NO_MATCH as the input for this case.
	 */
	private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

	// Static initializer. This is run the first time anything is called from this class.
	static
	{
		// The calls to addURI() go here, for all of the content URI patterns that the provider
		// should recognize. All paths added to the UriMatcher have a corresponding code to return
		// when a match is found.

		sUriMatcher.addURI(PetContract.CONTENT_AUTHORITY, PetsEntry.TABLE_NAME, PETS);
		sUriMatcher.addURI(PetContract.CONTENT_AUTHORITY, PetsEntry.TABLE_NAME + "/#", PET_ID);
	}

	/**
	 * PetDBHelper object
	 */
	private PetDBHelper mPetDBHelper;

	/**
	 * Initialize the provider and the database helper object.
	 */
	@Override
	public boolean onCreate()
	{
		// Make sure the variable is a global variable, so it can be referenced from other
		// ContentProvider methods.
		mPetDBHelper = new PetDBHelper(getContext());
		return true;
	}

	/**
	 * Perform the query for the given URI. Use the given projection, selection, selection arguments, and sort order.
	 */
	@Override
	public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs,
						String sortOrder)
	{
		// Get readable database
		SQLiteDatabase database = mPetDBHelper.getReadableDatabase();

		// This cursor will hold the result of the query
		Cursor cursor;

		// Figure out if the URI matcher can match the URI to a specific code
		int match = sUriMatcher.match(uri);
		switch (match)
		{
			case PETS:
				// For the PETS code, query the pets table directly with the given
				// projection, selection, selection arguments, and sort order. The cursor
				// could contain multiple rows of the pets table.
				cursor = database.query(PetsEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
				break;
			case PET_ID:
				// For the PET_ID code, extract out the ID from the URI.
				// For an example URI such as "content://com.example.android.pets/pets/3",
				// the selection will be "_id=?" and the selection argument will be a
				// String array containing the actual ID of 3 in this case.
				//
				// For every "?" in the selection, we need to have an element in the selection
				// arguments that will fill in the "?". Since we have 1 question mark in the
				// selection, we have 1 String in the selection arguments' String array.
				selection = PetsEntry._ID + "=?";
				selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

				// This will perform a query on the pets table where the _id equals 3 to return a
				// Cursor containing that row of the table.
				cursor = database.query(PetsEntry.TABLE_NAME, projection, selection, selectionArgs,
						null, null, sortOrder);
				break;
			default:
				throw new IllegalArgumentException("Cannot query unknown URI " + uri);
		}

		// Set notification for cursor
		cursor.setNotificationUri(getContext().getContentResolver(), uri);

		return cursor;
	}

	/**
	 * Insert new data into the provider with the given ContentValues.
	 */
	@Override
	public Uri insert(@NonNull Uri uri, ContentValues contentValues)
	{
		// Set notification
		notifyChangeHelper(uri);
		final int match = sUriMatcher.match(uri);

		switch (match)
		{
			case PETS:
				return insertPet(uri, contentValues);
			default:
				throw new IllegalArgumentException("Insertion is not supported for " + uri);
		}
	}

	/**
	 * Insert a pet into the database with the given content values. Return the new content URI
	 * for that specific row in the database.
	 */
	private Uri insertPet(Uri uri, ContentValues values)
	{
		String logTag = "PetProvider Insert";
		SQLiteDatabase shelterDBWritable = mPetDBHelper.getWritableDatabase();

		try
		{
			validateInput(values);
		} catch (IllegalArgumentException e)
		{
			Log.e(logTag, e.getLocalizedMessage());
		}

		long id = shelterDBWritable.insert(PetsEntry.TABLE_NAME, null, values);

		if (id == -1)
		{
			Log.e(logTag, "Insert failed");
		}

		// Once we know the ID of the new row in the table,
		// return the new URI with the ID appended to the end of it
		return ContentUris.withAppendedId(uri, id);
	}

	/**
	 * Updates the data at the given selection and selection arguments, with the new ContentValues.
	 */
	@Override
	public int update(@NonNull Uri uri, ContentValues contentValues, String selection,
					  String[] selectionArgs)
	{
		validateInput(contentValues);

		final int match = sUriMatcher.match(uri);
		int numRowsUpdated;

		switch (match)
		{
			case PETS:
				numRowsUpdated = updatePet(contentValues, selection, selectionArgs);
				if (numRowsUpdated > 0)
				{
					// Set notification
					notifyChangeHelper(uri);
				}
				return numRowsUpdated;
			case PET_ID:
				// For the PET_ID code, extract out the ID from the URI,
				// so we know which row to update. Selection will be "_id=?" and selection
				// arguments will be a String array containing the actual ID.
				selection = PetsEntry._ID + "=?";
				selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
				numRowsUpdated = updatePet(contentValues, selection, selectionArgs);
				if (numRowsUpdated > 0)
				{
					// Set notification
					notifyChangeHelper(uri);
				}
				return numRowsUpdated;
			default:
				throw new IllegalArgumentException("Update is not supported for " + uri);
		}
	}

	/**
	 * Update pets in the database with the given content values. Apply the changes to the rows
	 * specified in the selection and selection arguments (which could be 0 or 1 or more pets).
	 * Return the number of rows that were successfully updated.
	 */
	private int updatePet(ContentValues values, String selection, String[] selectionArgs)
	{
		String logTag = "PetProvider Update";
		SQLiteDatabase writableShelterDB = mPetDBHelper.getWritableDatabase();

		try
		{
			validateInput(values);
		} catch (IllegalArgumentException e)
		{
			Log.e(logTag, e.getLocalizedMessage());
		}

		if (values.size() == 0)
		{
			return 0;
		}

		return writableShelterDB.update(PetsEntry.TABLE_NAME, values, selection, selectionArgs);
	}


	/**
	 * Delete the data at the given selection and selection arguments.
	 */
	@Override
	public int delete(@NonNull Uri uri, String selection, String[] selectionArgs)
	{
		// Get writeable database
		SQLiteDatabase database = mPetDBHelper.getWritableDatabase();
		final int match = sUriMatcher.match(uri);
		int numRowsDeleted;

		switch (match)
		{
			case PETS:
				// Delete all rows that match the selection and selection args
				numRowsDeleted = database.delete(PetsEntry.TABLE_NAME, selection, selectionArgs);
				if (numRowsDeleted > 0)
				{
					// Set notification
					notifyChangeHelper(uri);
				}
				return numRowsDeleted;
			case PET_ID:
				// Delete a single row given by the ID in the URI
				selection = PetsEntry._ID + "=?";
				selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
				numRowsDeleted = database.delete(PetsEntry.TABLE_NAME, selection, selectionArgs);
				if (numRowsDeleted > 0)
				{
					// Set notification
					notifyChangeHelper(uri);
				}
			default:
				throw new IllegalArgumentException("Deletion is not supported for " + uri);
		}
	}

	/**
	 * Returns the MIME type of data for the content URI.
	 */
	@Override
	public String getType(@NonNull Uri uri)
	{
		final int match = sUriMatcher.match(uri);
		switch (match)
		{
			case PETS:
				return PetsEntry.CONTENT_LIST_TYPE;
			case PET_ID:
				return PetsEntry.CONTENT_ITEM_TYPE;
			default:
				throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
		}
	}

	/**
	 * @param values: a ContentValues object
	 */
	private void validateInput(ContentValues values) throws IllegalArgumentException
	{
		if (!values.containsKey(PetsEntry.COLUMN_PET_NAME) || !values.containsKey(PetsEntry.COLUMN_PET_GENDER) || !values.containsKey(PetsEntry.COLUMN_PET_WEIGHT))
		{
			throw new IllegalArgumentException("Malformed ContentValues");
		}

		if (values.getAsString(PetsEntry.COLUMN_PET_NAME) == null)
		{
			throw new IllegalArgumentException("Pet does not have a name");
		}
		Integer gender = values.getAsInteger(PetsEntry.COLUMN_PET_GENDER);
		if (gender == null || gender != PetsEntry.GENDER_FEMALE && gender != PetsEntry.GENDER_MALE && gender != PetsEntry.GENDER_UNKNOWN)
		{
			throw new IllegalArgumentException("Pet does not have a valid gender");
		}
		Integer weight = values.getAsInteger(PetsEntry.COLUMN_PET_WEIGHT);
		if (weight != null && weight < 0)
		{
			throw new IllegalArgumentException("Pet does not have a valid weight");
		}
	}

	/**
	 * @param uri: a URI to set notification for
	 */
	private void notifyChangeHelper(Uri uri)
	{
		getContext().getContentResolver().notifyChange(uri, null);
	}
}
