package com.example.android.quakereport;

public class Earthquake
{
	private double mMagnitude;
	private String mLocation;
	private String mDate;

	public Earthquake(double magnitude, String location, String date)
	{
		mMagnitude = magnitude;
		mLocation = location;
		mDate = date;
	}

	public double getMagnitude()
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
