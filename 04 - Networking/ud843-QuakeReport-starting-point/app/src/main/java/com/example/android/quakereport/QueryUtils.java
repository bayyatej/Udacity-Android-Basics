package com.example.android.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils
{

	public static final String LOG_TAG = EarthquakeActivity.class.getName();

	/**
	 * Create a private constructor because no one should ever create a {@link QueryUtils} object.
	 * This class is only meant to hold static variables and methods, which can be accessed
	 * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
	 */

	private QueryUtils()
	{
	}

	/**
	 * Return a list of {@link Earthquake} objects that has been built up from
	 * parsing a JSON response.
	 *
	 * @param queryUrl
	 */
	public static ArrayList<Earthquake> extractEarthquakes(String queryUrl)
	{

		// Create an empty ArrayList that we can start adding earthquakes to
		ArrayList<Earthquake> earthquakes = new ArrayList<>();
		String urlResponse = requestEarthquakes(queryUrl);
		if (urlResponse != "")
		{
			// Try to parse the QUERY_URL. If there's a problem with the way the JSON
			// is formatted, a JSONException exception object will be thrown.
			// Catch the exception so the app doesn't crash, and print the error message to the logs.
			try
			{

				// TODO: Parse the response given by the QUERY_URL string and
				// build up a list of Earthquake objects with the corresponding data.
				JSONArray featuresArr = (new JSONObject(urlResponse)).optJSONArray("features");

				for (int i = 0; i < featuresArr.length(); i++)
				{
					//Will be used to construct earthquake objects
					double magnitude;
					String location;
					String url;
					long date;

					JSONObject propertiesObj = featuresArr.optJSONObject(i).optJSONObject("properties");

					magnitude = propertiesObj.optDouble("mag");
					location = propertiesObj.optString("place");
					date = propertiesObj.optLong("time");
					url = propertiesObj.optString("url");

					earthquakes.add(new Earthquake(magnitude, location, date, url));
				}

			} catch (JSONException e)
			{
				// If an error is thrown when executing any of the above statements in the "try" block,
				// catch the exception here, so the app doesn't crash. Print a log message
				// with the message from the exception.
				Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
			}
		} else
		{
		}

		// Return the list of earthquakes
		return earthquakes;
	}

	private static String requestEarthquakes(String queryURLString)
	{
		URL queryURL = createUrl(queryURLString);
		String jsonResponse = "";
		try
		{
			jsonResponse = makeHttpRequest(queryURL);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return jsonResponse;
	}

	private static URL createUrl(String urlString)
	{
		URL url = null;
		try
		{
			url = new URL(urlString);
		} catch (MalformedURLException e)
		{
			Log.e("MalformedURLException: ", urlString);
		}
		return url;
	}

	private static String makeHttpRequest(URL url) throws IOException
	{
		String jsonResponse = "";
		if (url != null)
		{
			HttpURLConnection httpURLConnection = null;
			InputStream inputStream = null;
			try
			{
				httpURLConnection = (HttpURLConnection) url.openConnection();
				httpURLConnection.setReadTimeout(10000 /* milliseconds */);
				httpURLConnection.setConnectTimeout(15000 /* milliseconds */);
				httpURLConnection.setRequestMethod("GET");
				httpURLConnection.connect();

				// If the request was successful (response code 200),
				// then read the input stream and parse the response.
				if (httpURLConnection.getResponseCode() == 200)
				{
					inputStream = httpURLConnection.getInputStream();
					jsonResponse = readFromStream(inputStream);
				} else
				{
					Log.e(LOG_TAG, "Error response code: " + httpURLConnection.getResponseCode());
				}
			} catch (IOException e)
			{
				Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
			} finally
			{
				if (httpURLConnection != null)
				{
					httpURLConnection.disconnect();
				}
				if (inputStream != null)
				{
					inputStream.close();
				}
			}
		}
		return jsonResponse;
	}

	private static String readFromStream(InputStream inputStream) throws IOException
	{
		StringBuilder output = new StringBuilder();
		if (inputStream != null)
		{
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
			BufferedReader reader = new BufferedReader(inputStreamReader);
			String line = reader.readLine();
			while (line != null)
			{
				output.append(line);
				line = reader.readLine();
			}
		}
		return output.toString();
	}
}
