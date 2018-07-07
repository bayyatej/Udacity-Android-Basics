package com.example.android.bookfinder;

import java.util.ArrayList;
import java.util.List;

public class Book
{
	private String mTitle;
	private List<String> mAuthors;

	public Book(String title, ArrayList<String> authors)
	{
		mTitle = title;
		mAuthors = authors;
	}

	public List<String> getmAuthors()
	{
		return mAuthors;
	}

	public String getmTitle()
	{
		return mTitle;
	}
}
