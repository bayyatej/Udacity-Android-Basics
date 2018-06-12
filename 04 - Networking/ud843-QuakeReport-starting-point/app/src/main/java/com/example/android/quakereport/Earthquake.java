package com.example.android.quakereport;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Earthquake
{
	private double mMagnitude;
	private String mLocation;
	private String mDateString;
	private String mUrl;

	public Earthquake(double magnitude, String location, long date, String url)
	{
		mMagnitude = magnitude;
		mLocation = location;
		mUrl = url;

		Date dateObj = new Date(date);
		SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM DD, YYYY HH:mm");

		mDateString = dateFormatter.format(dateObj);
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
		return mDateString;
	}

	public String getUrl()
	{
		return mUrl;
	}
}
