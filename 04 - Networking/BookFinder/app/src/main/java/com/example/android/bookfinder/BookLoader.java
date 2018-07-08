package com.example.android.bookfinder;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<Book>>
{
	private String mURL;

	public BookLoader(Context context, String url)
	{
		super(context);
		mURL = url;
	}

	@Override
	public List<Book> loadInBackground()
	{
		if (mURL == null)
		{
			return null;
		} else
		{
			return QueryUtils.extractBooks(mURL);
		}
	}

	@Override
	protected void onStartLoading()
	{
		forceLoad();
	}
}
