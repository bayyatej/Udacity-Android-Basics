package com.example.android.newsapp;

public class News
{
	private String mTitle;
	private String mPillar;
	private String mStoryUrl;

	public News(String title, String pillar, String url)
	{
		mTitle = title;
		mPillar = pillar;
		mStoryUrl = url;
	}

	public String getmStoryUrl()
	{
		return mStoryUrl;
	}

	public String getmPillar()
	{
		return mPillar;
	}

	public String getmTitle()
	{
		return mTitle;
	}
}
