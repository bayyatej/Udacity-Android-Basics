package com.example.android.pets.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.pets.data.PetContract.PetsEntry;

public class PetDBHelper extends SQLiteOpenHelper
{
	private static final String DB_NAME = "shelter.db";
	private static final int VERSION_NUMBER = 1;

	PetDBHelper(Context context)
	{
		super(context, DB_NAME, null, VERSION_NUMBER);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		String SQLStatement = "CREATE TABLE " + PetsEntry.TABLE_NAME + "(" +
				PetsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				PetsEntry.COLUMN_PET_NAME + " TEXT NOT NULL, " +
				PetsEntry.COLUMN_PET_BREED + " TEXT, " +
				PetsEntry.COLUMN_PET_GENDER + " INTEGER NOT NULL, " +
				PetsEntry.COLUMN_PET_WEIGHT + " INTEGER NOT NULL DEFAULT 0);";
		db.execSQL(SQLStatement);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		if (oldVersion != newVersion)
		{
			String SQLStatement = "DROP TABLE " + PetsEntry.TABLE_NAME + ";";
			db.execSQL(SQLStatement);
		}
	}
}
