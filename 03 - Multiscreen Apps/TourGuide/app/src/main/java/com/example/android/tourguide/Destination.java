package com.example.android.tourguide;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Destination
{
	private String mName;
	private String mPhoneNumber;
	/**
	 * Query string for google maps url api
	 */
	private String mApiQueryUrl;
	private int mImageId = 0;
	private static final String API_URL = "https://www.google.com/maps/dir/?api=1&destination=";
	private static final String API_LOCATION = "%2C+Novi%2C+MI";
	private static final String BAD_API_URL = "BAD API URL";

	public Destination(String name, String phoneNumber)
	{
		mName = name;
		mPhoneNumber = phoneNumber;
		mImageId = 0;

		try
		{
			mApiQueryUrl = API_URL + URLEncoder.encode(mName, "utf-8") + API_LOCATION;
		} catch (UnsupportedEncodingException e)
		{
			mApiQueryUrl = BAD_API_URL;
		}
	}

	public Destination(String name, String phoneNumber, int imageId)
	{
		mName = name;
		mPhoneNumber = phoneNumber;
		mImageId = imageId;

		try
		{
			mApiQueryUrl = API_URL + URLEncoder.encode(mName, "utf-8") + API_LOCATION;
		} catch (UnsupportedEncodingException e)
		{
			mApiQueryUrl = BAD_API_URL;
		}
	}

	public String getName()
	{
		return mName;
	}

	public String getPhoneNumber()
	{
		return mPhoneNumber;
	}

	public String getApiQueryUrl()
	{
		return mApiQueryUrl;
	}

	public int getImageId()
	{
		return mImageId;
	}
}
