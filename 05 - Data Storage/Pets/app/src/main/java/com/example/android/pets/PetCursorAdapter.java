package com.example.android.pets;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class PetCursorAdapter extends CursorAdapter
{
	/**
	 * {@link PetCursorAdapter} is an adapter for a list or grid view
	 * that uses a {@link Cursor} of pet data as its data source. This adapter knows
	 * how to create list items for each row of pet data in the {@link Cursor}.
	 */
	PetCursorAdapter(Context context, Cursor c, int flags)
	{
		super(context, c, flags);
	}

	/**
	 * Makes a new blank list item view. No data is set (or bound) to the views yet.
	 *
	 * @param context app context
	 * @param cursor  The cursor from which to get the data. The cursor is already
	 *                moved to the correct position.
	 * @param parent  The parent to which the new view is attached to
	 * @return the newly created list item view.
	 */
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent)
	{
		return LayoutInflater.from(context).inflate(R.layout.pet_list_item, parent, false);
	}

	/**
	 * This method binds the pet data (in the current row pointed to by cursor) to the given
	 * list item layout. For example, the name for the current pet can be set on the name TextView
	 * in the list item layout.
	 *
	 * @param view    Existing view, returned earlier by newView() method
	 * @param context app context
	 * @param cursor  The cursor from which to get the data. The cursor is already moved to the
	 *                correct row.
	 */
	@Override
	public void bindView(View view, Context context, Cursor cursor)
	{
		// Populates Pet Name TextView with pet name
		((TextView) view.findViewById(R.id.pet_name)).setText(cursor.getString(1));
		// Populates Pet Summary TextView with pet breed
		String petBreed = cursor.getString(2);
		if (TextUtils.isEmpty(petBreed))
		{
			petBreed = "Unknown";
		}
		((TextView) view.findViewById(R.id.pet_summary)).setText(petBreed);
	}
}
