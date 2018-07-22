package com.example.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

public class NewsLoader extends AsyncTaskLoader<ArrayList<News>>
{
	private String mQueryUrlString;

	public NewsLoader(Context context, String url)
	{
		super(context);
		mQueryUrlString = url;
	}

	@Override
	public ArrayList<News> loadInBackground()
	{
		if (mQueryUrlString == null)
		{
			return null;
		} else
		{
			return QueryUtils.extractNews(mQueryUrlString);
		}
	}

	@Override
	protected void onStartLoading()
	{
		forceLoad();
	}
}
