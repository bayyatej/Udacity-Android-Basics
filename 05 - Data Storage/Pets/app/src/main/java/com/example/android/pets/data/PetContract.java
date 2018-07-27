package com.example.android.pets.data;

import android.provider.BaseColumns;

public class PetContract
{
	public static final class PetsEntry implements BaseColumns
	{
		/**
		 * Table Name
		 */
		public static final String TABLE_NAME = "pets";

		/**
		 * Column names
		 */
		//Pet's unique ID
		public static final String COLUMN_PET_ID = BaseColumns._ID;
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
