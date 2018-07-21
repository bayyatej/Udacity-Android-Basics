package com.example.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

public class NewsLoader extends AsyncTaskLoader<ArrayList<News>>
{
	private String queryUrlString;

	public NewsLoader(Context context, String url)
	{
		super(context);
		queryUrlString = url;
	}

	@Override
	public ArrayList<News> loadInBackground()
	{
		if (queryUrlString == null)
		{
			return null;
		} else
		{
			return QueryUtils.extractNews(queryUrlString);
		}
	}

	@Override
	protected void onStartLoading()
	{
		forceLoad();
	}
}
