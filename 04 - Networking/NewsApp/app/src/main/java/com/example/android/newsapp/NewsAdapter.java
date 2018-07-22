package com.example.android.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends ArrayAdapter
{
	public NewsAdapter(@NonNull Context context, List<News> newsList)
	{
		super(context, 0, newsList);
	}

	@NonNull
	@Override
	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
	{
		View newsListItemView = convertView;
		final News newsItem = (News) getItem(position);

		if (newsListItemView == null)
		{
			newsListItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_list_item, parent, false);
		}

		assert newsItem != null;
		((TextView) newsListItemView.findViewById(R.id.pillar)).setText(newsItem.getmPillar());
		((TextView) newsListItemView.findViewById(R.id.article)).setText(newsItem.getmTitle());

		newsListItemView.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsItem.getmStoryUrl()));
				getContext().startActivity(webIntent);
			}
		});

		return newsListItemView;
	}
}
