package com.example.android.pets.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class PetContract
{
	/**
	 * Content Provider URI
	 */
	public static final String CONTENT_AUTHORITY = "com.example.android.pets";
	public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
	public static final String PATH_PETS = "pets";

	private PetContract()
	{

	}

	public static final class PetsEntry implements BaseColumns
	{
		/**
		 * Content URI
		 */
		public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PETS);

		/**
		 * Table Name
		 */
		public static final String TABLE_NAME = "pets";

		/**
		 * Column names
		 */
		//Pet's unique ID
		public static final String _ID = BaseColumns._ID;
		//Pet's name
		public static final String COLUMN_PET_NAME = "Name";
		//Pet's breed
		public static final String COLUMN_PET_BREED = "Breed";
		//Pet's gender
		public static final String COLUMN_PET_GENDER = "Gender";
		//Pet's weight
		public static final String COLUMN_PET_WEIGHT = "Weight";

		/**
		 * Constants representing pet genders
		 */
		public static final int GENDER_UNKNOWN = 0;
		public static final int GENDER_MALE = 1;
		public static final int GENDER_FEMALE = 2;

	}
}
