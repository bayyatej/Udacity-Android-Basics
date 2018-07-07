package com.example.android.bookfinder;

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
import java.util.List;

public class QueryUtils
{
	private QueryUtils()
	{

	}

	public static List<Book> extractBooks(String queryUrl)
	{
		ArrayList<Book> bookList = new ArrayList<>();
		String apiResponse = fetchBookJSON(queryUrl);

		if (!(apiResponse.equals("")))
		{
			try
			{
				JSONArray itemsArr = (new JSONObject(apiResponse)).optJSONArray("items");

				for (int i = 0; i < itemsArr.length(); i++)
				{
					String title;
					ArrayList<String> authors = new ArrayList<>();
					JSONObject volumeObj = itemsArr.optJSONObject(i).optJSONObject("volumeInfo");
					JSONArray authorsArr = volumeObj.optJSONArray("authors");

					title = volumeObj.optString("title");
					if (!(volumeObj.optString("subtitle")).equals(""))
					{
						title += ": " + volumeObj.optString("subtitle");
					}

					for (int j = 0; j < authorsArr.length(); j++)
					{
						authors.add(authorsArr.optString(j));
					}

					bookList.add(new Book(title, authors));
				}

			} catch (JSONException e)
			{
				Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
			}

		}

		return bookList;
	}

	private static String fetchBookJSON(String queryUrlString)
	{
		URL queryURL = createUrl(queryUrlString);
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
		final String LOG_TAG = "Query_Utils";
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
