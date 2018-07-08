package com.example.android.bookfinder;

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
import java.util.List;

public class BookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>>
{
	private String mQuery;
	private Intent mIntent;
	private String mAPI_URL;
	private BookAdapter mBookAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_list);
		((ListView) findViewById(R.id.book_list)).setEmptyView(findViewById(R.id.empty_view));
		mIntent = getIntent();
		mAPI_URL = prepareUrl();
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
		return "https://www.googleapis.com/books/v1/volumes?q=" + mQuery + "&maxResults=15";
	}

	@Override
	public Loader<List<Book>> onCreateLoader(int id, Bundle args)
	{
		return new BookLoader(this, mAPI_URL);
	}

	@Override
	public void onLoadFinished(Loader<List<Book>> loader, List<Book> data)
	{
		findViewById(R.id.progress_spinner).setVisibility(View.INVISIBLE);

		if (mBookAdapter != null)
		{
			mBookAdapter.clear();
		}

		if (data != null && !data.isEmpty())
		{
			updateUi(data);
		}
	}

	@Override
	public void onLoaderReset(Loader<List<Book>> loader)
	{
		if (mBookAdapter != null)
		{
			mBookAdapter.clear();
		}
	}

	private void updateUi(List<Book> books)
	{
		ListView bookListView = findViewById(R.id.book_list);
		mBookAdapter = new BookAdapter(this, books);
		bookListView.setAdapter(mBookAdapter);
	}
}
