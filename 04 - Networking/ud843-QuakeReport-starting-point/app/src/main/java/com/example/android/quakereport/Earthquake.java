package com.example.android.quakereport;

public class Earthquake
{
	private float mMagnitude;
	private String mLocation;
	private String mDate;

	public Earthquake(float magnitude, String location, String date)
	{
		mMagnitude = magnitude;
		mLocation = location;
		mDate = date;
	}

	public float getMagnitude()
	{
		return mMagnitude;
	}

	public String getLocation()
	{
		return mLocation;
	}

	public String getDate()
	{
		return mDate;
	}
}
