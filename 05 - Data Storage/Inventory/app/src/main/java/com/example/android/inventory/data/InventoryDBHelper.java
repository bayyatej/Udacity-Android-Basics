package com.example.android.inventory.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.inventory.data.InventoryContract.InventoryEntry;

public class InventoryDBHelper extends SQLiteOpenHelper
{
	private static final String DB_NAME = "inventory.db";
	private static final int VERSION_NUMBER = 1;

	public InventoryDBHelper(Context context)
	{
		super(context, DB_NAME, null, VERSION_NUMBER);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		String SQLiteStatement = "CREATE TABLE " + InventoryEntry.TABLE_NAME + "(" +
				InventoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				InventoryEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, " +
				InventoryEntry.COLUMN_PRODUCT_DESCRIPTION + " TEXT, " +
				InventoryEntry.COLUMN_PRODUCT_PRICE + " INTEGER NOT NULL DEFAULT 0, " +
				InventoryEntry.COLUMN_PRODUCT_QUANTITY + " INTEGER NOT NULL DEFAULT 0, " +
				InventoryEntry.COLUMN_PRODUCT_PICTURE + " BLOB NOT NULL);";
		db.execSQL(SQLiteStatement);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		if (oldVersion != newVersion)
		{
			String SQLiteStatement = "DROP TABLE " + InventoryEntry.TABLE_NAME + ";";
			db.execSQL(SQLiteStatement);
		}
	}
}
