package com.example.android.bookfinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends ArrayAdapter
{
	public BookAdapter(@NonNull Context context, @NonNull List<Book> books)
	{
		super(context, 0, books);
	}

	@NonNull
	@Override
	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
	{
		View bookListView = convertView;
		final Book book = (Book) getItem(position);
		String title = book.getmTitle();
		ArrayList<String> authors = (ArrayList<String>) book.getmAuthors();
		String authorsString = "";

		if (bookListView == null)
		{
			bookListView = LayoutInflater.from(getContext()).inflate(R.layout.book_list_item, parent, false);
		}

		for (String author : authors)
		{
			authorsString += author + ", ";
		}
		authorsString = authorsString.substring(0, authorsString.length() - 2);

		((TextView) bookListView.findViewById(R.id.book_title)).setText(title);
		((TextView) bookListView.findViewById(R.id.book_author)).setText(authorsString);

		return bookListView;
	}
}
