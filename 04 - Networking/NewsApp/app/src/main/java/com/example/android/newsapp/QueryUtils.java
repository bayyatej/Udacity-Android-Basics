package com.example.android.newsapp;

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

public class QueryUtils
{
	private QueryUtils()
	{
	}

	/**
	 * Constructs newsList from json response
	 *
	 * @param queryUrlString: String representation of query url
	 * @return ArrayList<News> newsList
	 */
	public static ArrayList<News> extractNews(String queryUrlString)
	{
		ArrayList<News> newsList = new ArrayList<>();
		String jsonResponse = fetchNewsJSON(queryUrlString);

		if (!jsonResponse.equals(""))
		{
			try
			{
				JSONArray responseJSONArr = (new JSONObject(jsonResponse)).optJSONArray("results");

				for (int i = 0; i < responseJSONArr.length(); i++)
				{
					JSONObject newsJSONObj = (JSONObject) responseJSONArr.opt(i);
					newsList.add(new News(newsJSONObj.optString("webTitle"), newsJSONObj.optString("sectionName"), newsJSONObj.optString("webUrl")));
				}
			} catch (JSONException e)
			{
				e.printStackTrace();
			}
		}

		return newsList;
	}

	/**
	 * Retrieves news from API
	 *
	 * @param queryUrlString: String representation of query url
	 * @return String JSON reponse from server
	 */
	private static String fetchNewsJSON(String queryUrlString)
	{
		String jsonResponse = "";
		final String LOG_TAG = "Query_Utils";
		URL mQueryUrl = createURL(queryUrlString);

		try
		{
			if (mQueryUrl != null)
			{
				HttpURLConnection httpURLConnection = null;
				InputStream inputStream = null;
				try
				{
					httpURLConnection = (HttpURLConnection) mQueryUrl.openConnection();
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
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return jsonResponse;
	}

	/**
	 * Creates URL representation of queryUrlString
	 *
	 * @param queryUrlString: String representation of query url
	 * @return URL object representation of queryUrlString
	 */
	private static URL createURL(String queryUrlString)
	{
		URL queryUrl = null;
		try
		{
			queryUrl = new URL(queryUrlString);
		} catch (MalformedURLException e)
		{
			Log.e("MalformedURLException: ", queryUrlString);
		}
		return queryUrl;
	}

	/**
	 * Helper method to convert result of HttpRequest to String
	 *
	 * @param inputStream: stream to convert to string
	 * @return String representation of stream contents
	 * @throws IOException when InputStreamReader fucks up
	 */
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
