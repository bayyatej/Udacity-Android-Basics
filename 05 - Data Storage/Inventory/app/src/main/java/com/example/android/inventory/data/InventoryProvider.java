package com.example.android.inventory.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.example.android.inventory.data.InventoryContract.InventoryEntry;

public class InventoryProvider extends ContentProvider
{
	private static final int INVENTORY = 100;
	private static final int INVENTORY_ID = 101;
	private InventoryDBHelper mInventoryDBHelper;
	/**
	 * UriMatcher object to match a content URI to a corresponding code.
	 * The input passed into the constructor represents the code to return for the root URI.
	 * It's common to use NO_MATCH as the input for this case.
	 */
	private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	/**
	 * Exception error strings
	 */
	private final String mMalformedContentValues = "Malformed ContentValues";
	private final String mNoProductName = "No Product Name Provided";
	private final String mNoDescription = "No Product Description Provided";
	private final String mNoPrice = "No Product Price Provided";
	private final String mNoQuantity = "No Product Quantity Provided";
	private final String mNoPicture = "No Product Picture Provided";

	static
	{
		sUriMatcher.addURI(InventoryContract.CONTENT_AUTHORITY, InventoryContract.PATH_INVENTORY, INVENTORY);
		sUriMatcher.addURI(InventoryContract.CONTENT_AUTHORITY, InventoryContract.PATH_INVENTORY + "/#", INVENTORY_ID);
	}

	@Override
	public boolean onCreate()
	{
		mInventoryDBHelper = new InventoryDBHelper(getContext());
		return true;
	}

	@Nullable
	@Override
	public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder)
	{
		SQLiteDatabase database = mInventoryDBHelper.getReadableDatabase();
		Cursor cursor;

		int match = sUriMatcher.match(uri);
		switch (match)
		{
			case INVENTORY:
				cursor = database.query(InventoryEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
				break;
			case INVENTORY_ID:
				selection = InventoryContract.InventoryEntry._ID + "=?";
				selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
				cursor = database.query(InventoryEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
				break;
			default:
				throw new IllegalArgumentException("Cannot query unknown URI " + uri);
		}

		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Nullable
	@Override
	public String getType(@NonNull Uri uri)
	{
		final int match = sUriMatcher.match(uri);
		switch (match)
		{
			case INVENTORY:
				return InventoryEntry.CONTENT_LIST_TYPE;
			case INVENTORY_ID:
				return InventoryEntry.CONTENT_ITEM_TYPE;
			default:
				throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
		}
	}

	@Nullable
	@Override
	public Uri insert(@NonNull Uri uri, @Nullable ContentValues values)
	{
		try
		{
			validateInput(values);
		} catch (IllegalArgumentException e)
		{
			throw e;
		}
		notifyChangeHelper(uri);
		final int match = sUriMatcher.match(uri);
		switch (match)
		{
			case INVENTORY:
				return insertInventory(uri, values);
			default:
				throw new IllegalArgumentException("Insertion is not supported for " + uri);
		}
	}

	@Override
	public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs)
	{
		// Get writeable database
		SQLiteDatabase database = mInventoryDBHelper.getWritableDatabase();
		int numRowsDeleted;

		final int match = sUriMatcher.match(uri);
		switch (match)
		{
			case INVENTORY:
				// Delete all rows that match the selection and selection args
				numRowsDeleted = database.delete(InventoryEntry.TABLE_NAME, selection, selectionArgs);
				if (numRowsDeleted > 0)
				{
					// Set notification
					notifyChangeHelper(uri);
				}
				return numRowsDeleted;
			case INVENTORY_ID:
				// Delete a single row given by the ID in the URI
				selection = InventoryEntry._ID + "=?";
				selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
				numRowsDeleted = database.delete(InventoryEntry.TABLE_NAME, selection, selectionArgs);
				if (numRowsDeleted > 0)
				{
					// Set notification
					notifyChangeHelper(uri);
				}
				return numRowsDeleted;
			default:
				throw new IllegalArgumentException("Deletion is not supported for " + uri);
		}
	}

	@Override
	public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs)
	{
		try
		{
			validateInput(values);
		} catch (IllegalArgumentException e)
		{
			throw e;
		}

		final int match = sUriMatcher.match(uri);
		int numRowsUpdated;

		switch (match)
		{
			case INVENTORY:
				numRowsUpdated = updateInventory(values, selection, selectionArgs);
				if (numRowsUpdated > 0)
				{
					// Set notification
					notifyChangeHelper(uri);
				}
				return numRowsUpdated;
			case INVENTORY_ID:
				// For the PET_ID code, extract out the ID from the URI,
				// so we know which row to update. Selection will be "_id=?" and selection
				// arguments will be a String array containing the actual ID.
				selection = InventoryEntry._ID + "=?";
				selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
				numRowsUpdated = updateInventory(values, selection, selectionArgs);
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

	/*
	 *  Helper Methods
	 */

	/**
	 * Validates input before insert or update
	 *
	 * @param values: ContentValues object to validate
	 */
	private void validateInput(ContentValues values)
	{
		if (!values.containsKey(InventoryEntry.COLUMN_PRODUCT_NAME) || !values.containsKey(InventoryEntry.COLUMN_PRODUCT_DESCRIPTION) || !values.containsKey(InventoryEntry.COLUMN_PRODUCT_PRICE) || !values.containsKey(InventoryEntry.COLUMN_PRODUCT_QUANTITY) || !values.containsKey(InventoryEntry.COLUMN_PRODUCT_PICTURE))
		{
			throw new IllegalArgumentException(mMalformedContentValues);
		}

		if (TextUtils.isEmpty(values.getAsString(InventoryEntry.COLUMN_PRODUCT_NAME)))
		{
			throw new IllegalArgumentException(mNoProductName);
		} else if (TextUtils.isEmpty(values.getAsString(InventoryEntry.COLUMN_PRODUCT_DESCRIPTION)))
		{
			throw new IllegalArgumentException(mNoDescription);
		} else if (values.getAsInteger(InventoryEntry.COLUMN_PRODUCT_PRICE) == null)
		{
			throw new IllegalArgumentException(mNoPrice);
		} else if (values.getAsInteger(InventoryEntry.COLUMN_PRODUCT_QUANTITY) == null)
		{
			throw new IllegalArgumentException(mNoQuantity);
		} else if (values.get(InventoryEntry.COLUMN_PRODUCT_PICTURE) == null)
		{
			throw new IllegalArgumentException(mNoPicture);
		}
	}

	/**
	 * Insert values into database
	 *
	 * @param uri:
	 * @param values:
	 * @return: Uri of newly inserted row
	 */
	private Uri insertInventory(Uri uri, ContentValues values)
	{
		String logTag = "Provider insert";
		SQLiteDatabase database = mInventoryDBHelper.getWritableDatabase();
		long id = database.insert(InventoryEntry.TABLE_NAME, null, values);

		if (id == -1)
		{
			Log.e(logTag, "Insert failed");
		}

		// Once we know the ID of the new row in the table,
		// return the new URI with the ID appended to the end of it
		return ContentUris.withAppendedId(uri, id);
	}

	/**
	 * Update database
	 *
	 * @param values:
	 * @param selection:
	 * @param selectionArgs:
	 * @return:
	 */
	private int updateInventory(ContentValues values, String selection, String[] selectionArgs)
	{
		String logTag = "PetProvider Update";
		SQLiteDatabase writableShelterDB = mInventoryDBHelper.getWritableDatabase();

		try
		{
			validateInput(values);
		} catch (IllegalArgumentException e)
		{
			throw e;
		}

		if (values.size() == 0)
		{
			return 0;
		}

		return writableShelterDB.update(InventoryEntry.TABLE_NAME, values, selection, selectionArgs);
	}

	/**
	 * @param uri: a URI to set notification for
	 */
	private void notifyChangeHelper(Uri uri)
	{
		getContext().getContentResolver().notifyChange(uri, null);
	}
}
