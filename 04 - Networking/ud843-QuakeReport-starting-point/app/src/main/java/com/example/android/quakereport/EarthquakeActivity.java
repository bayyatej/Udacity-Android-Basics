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

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity
{
	private class EarthquakeAsyncTask extends AsyncTask<String, Void, List<Earthquake>>
	{
		@Override
		protected void onPostExecute(List<Earthquake> earthquakes)
		{
			if (earthquakes != null)
			{
				updateUi(earthquakes);
			}
		}

		@Override
		protected List<Earthquake> doInBackground(String... strings)
		{
			if (strings.length > 0 && strings[0] != null)
			{
				return QueryUtils.extractEarthquakes(strings[0]);
			} else
			{
				return null;
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.earthquake_list);

		new EarthquakeAsyncTask().execute("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=10");
	}

	private void updateUi(List<Earthquake> earthquakes)
	{
		ListView earthquakeListView = (ListView) findViewById(R.id.earthquake_list);
		EarthquakeAdapter earthquakeAdapter = new EarthquakeAdapter(this, (ArrayList<Earthquake>) earthquakes);
		earthquakeListView.setAdapter(earthquakeAdapter);
	}
}
