package com.example.android.inventory.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class InventoryContract
{
	/**
	 * Content Provider URI
	 */
	public static final String CONTENT_AUTHORITY = "com.example.android.inventory";
	static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
	static final String PATH_INVENTORY = "inventory";

	private InventoryContract()
	{

	}

	public static final class InventoryEntry implements BaseColumns
	{
		public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_INVENTORY);
		public static final String CONTENT_LIST_TYPE =
				ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORY;
		public static final String CONTENT_ITEM_TYPE =
				ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORY;
		public static final String TABLE_NAME = "inventory";

		/**
		 * Column names
		 */
		public static final String _ID = BaseColumns._ID;
		public static final String COLUMN_PRODUCT_NAME = "Name";
		public static final String COLUMN_PRODUCT_DESCRIPTION = "Description";
		public static final String COLUMN_PRODUCT_PRICE = "Price";
		public static final String COLUMN_PRODUCT_QUANTITY = "Quantity";
		public static final String COLUMN_PRODUCT_PICTURE = "Picture";
	}
}
