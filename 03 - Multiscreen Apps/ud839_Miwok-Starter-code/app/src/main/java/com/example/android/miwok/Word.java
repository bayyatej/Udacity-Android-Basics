package com.example.android.miwok;

/**
 * {@link Word} Represents a vocabulary word the user wants to learn.
 * Contains a default translation and a Miwok translation for that word
 */
public class Word
{
	private String mMiwokTranslation;
	private String mDefaultTranslation;

	/**
	 * Constructs a word object using given parameters
	 *
	 * @param miwokTranslation:  String translation of word in Miwok
	 * @param defaultTranslation String translation of word in default language
	 */
	public Word(String defaultTranslation, String miwokTranslation)
	{
		mMiwokTranslation = "";
		mMiwokTranslation = miwokTranslation;
		mDefaultTranslation = defaultTranslation;
	}

	/**
	 * Returns Miwok translation of Word
	 *
	 * @return Miwok translation of Word
	 */
	public String getMiwokTranslation()
	{
		return mMiwokTranslation;
	}

	/**
	 * Returns DefaultTranslation
	 *
	 * @return default translation of Word
	 */
	public String getDefaultTranslation()
	{
		return mDefaultTranslation;
	}
}
