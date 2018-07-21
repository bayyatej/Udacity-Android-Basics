package com.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<News>>
{
	private Intent mIntent;
	private String mQuery;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_list);
		((ListView) findViewById(R.id.news_list_view)).setEmptyView(findViewById(R.id.empty_view));
		mIntent = getIntent();
		mQuery = prepareUrl();
		checkNetworkStatus();
	}

	private void checkNetworkStatus()
	{
		ConnectivityManager cm =
				(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (activeNetwork != null && activeNetwork.isConnected())
		{
			getLoaderManager().initLoader(1, null, this).forceLoad();

		} else
		{
			//set text of empty view to display if no books found
			findViewById(R.id.progress_spinner).setVisibility(View.GONE);
			((TextView) findViewById(R.id.empty_view)).setText(R.string.no_internet);
		}
	}

	private String prepareUrl()
	{
		try
		{
			mQuery = URLEncoder.encode(mIntent.getStringExtra("query"), "UTF-8");
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		String API_KEY = "&api-key=6c42e62f-815c-43a7-a498-9b7731152369";
		String API_ENDPOINT = "https://content.guardianapis.com/search?q=";
		return API_ENDPOINT + mQuery + API_KEY;
	}

	// Todo need to implement onCreateLoader before testing submit query
	@Override
	public Loader<ArrayList<News>> onCreateLoader(int id, Bundle args)
	{
		return null;
	}

	// Todo need to implement onLoadFinished before testing submit query
	@Override
	public void onLoadFinished(Loader<ArrayList<News>> loader, ArrayList<News> data)
	{

	}

	// Todo need to implement onLoaderReset before testing submit query
	@Override
	public void onLoaderReset(Loader<ArrayList<News>> loader)
	{

	}
}
