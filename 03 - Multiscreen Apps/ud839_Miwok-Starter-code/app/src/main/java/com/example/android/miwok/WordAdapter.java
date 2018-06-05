package com.example.android.miwok;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter
{
	public WordAdapter(Activity context, ArrayList<Word> words)
	{
		super(context, 0, words);
	}

	@NonNull
	@Override
	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
	{
		View listItemView = convertView;
		if (convertView == null)
		{
			listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,
					parent, false);
		}

		//sets the text for miwok_text_view
		((TextView) listItemView.findViewById(R.id.miwok_text_view)).setText((
				(Word) getItem(position)).getMiwokTranslation());
		//sets the text for default_text_view
		((TextView) listItemView.findViewById(R.id.default_text_view)).setText(
				((Word) getItem(position)).getDefaultTranslation());

		return listItemView;
	}
}
