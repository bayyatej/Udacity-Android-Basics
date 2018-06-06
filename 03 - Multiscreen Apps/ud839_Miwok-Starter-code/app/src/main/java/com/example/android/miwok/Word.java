package com.example.android.miwok;

/**
 * {@link Word} Represents a vocabulary word the user wants to learn.
 * Contains a default translation and a Miwok translation for that word
 */
public class Word
{
	private String mMiwokTranslation;
	private String mDefaultTranslation;
	private int mImageId;
	private int mAudioId;

	/**
	 * Constructs a word object using given parameters
	 *
	 * @param miwokTranslation:  String translation of word in Miwok
	 * @param defaultTranslation String translation of word in default language
	 */
	public Word(String defaultTranslation, String miwokTranslation, int audioId)
	{
		mMiwokTranslation = miwokTranslation;
		mDefaultTranslation = defaultTranslation;
		mAudioId = audioId;
	}

	/**
	 * Constructs a Word object with an image
	 *
	 * @param defaultTranslation: String translation of word in Miwok
	 * @param miwokTranslation:   String translation of word in default language
	 * @param imageId:            Integer Id of image to associate with Word
	 */
	public Word(String defaultTranslation, String miwokTranslation, int imageId, int audioId)
	{
		mMiwokTranslation = miwokTranslation;
		mDefaultTranslation = defaultTranslation;
		mImageId = imageId;
		mAudioId = audioId;
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

	/**
	 * Returns image resource id
	 *
	 * @return image resource id
	 */
	public int getImageResourceId()
	{
		return mImageId;
	}

	/**
	 * returns audio resource id
	 *
	 * @return: audio resource id
	 */
	public int getAudioResourceId()
	{
		return mAudioId;
	}

	/**
	 * toString method generated by android studio
	 *
	 * @return: string representation of Word object
	 */
	@Override
	public String toString()
	{
		return "Word{" +
				"mMiwokTranslation='" + mMiwokTranslation + '\'' +
				", mDefaultTranslation='" + mDefaultTranslation + '\'' +
				", mImageId=" + mImageId +
				", mAudioId=" + mAudioId +
				'}';
	}
}
