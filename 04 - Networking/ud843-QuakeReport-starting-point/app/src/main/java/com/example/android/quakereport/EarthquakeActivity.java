/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>>
{
	private final String mURL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=10";
	private EarthquakeAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.earthquake_list);
		getLoaderManager().initLoader(0, null, this).forceLoad();
		ListView listView = findViewById(R.id.earthquake_list);
		listView.setEmptyView(findViewById(R.id.empty));

		//check for network connectivity, dismissing loader and showing empty view if no internet
		ConnectivityManager cm =
				(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (!(activeNetwork != null && activeNetwork.isConnectedOrConnecting()))
		{
			((ProgressBar) findViewById(R.id.loading_spinner)).setVisibility(View.INVISIBLE);
			((TextView) findViewById(R.id.empty)).setText(R.string.no_internet);
		} else
		{
			//set text of empty view to display if no earthquakes found
			((TextView) findViewById(R.id.empty)).setText(R.string.empty_view);
		}
	}

	private void updateUi(List<Earthquake> earthquakes)
	{
		ListView earthquakeListView = findViewById(R.id.earthquake_list);
		mAdapter = new EarthquakeAdapter(this, (ArrayList<Earthquake>) earthquakes);
		earthquakeListView.setAdapter(mAdapter);
	}

	@Override
	public Loader<List<Earthquake>> onCreateLoader(int id, Bundle args)
	{
		return new EarthquakeLoader(this, mURL);
	}

	@Override
	public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakes)
	{
		//dismiss loading indicator
		((ProgressBar) findViewById(R.id.loading_spinner)).setVisibility(View.GONE);

		if (mAdapter != null)
		{
			mAdapter.clear();
		}

		if (earthquakes != null && !earthquakes.isEmpty())
		{
			updateUi(earthquakes);
		}
	}

	@Override
	public void onLoaderReset(Loader<List<Earthquake>> loader)
	{
		if (mAdapter != null)
		{
			mAdapter.clear();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			Intent settingsIntent = new Intent(this, SettingsActivity.class);
			startActivity(settingsIntent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
