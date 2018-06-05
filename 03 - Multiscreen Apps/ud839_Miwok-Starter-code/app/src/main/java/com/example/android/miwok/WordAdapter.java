package com.example.android.miwok;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter
{
	private int mColor;

	public WordAdapter(Activity context, ArrayList<Word> words, int color)
	{
		super(context, 0, words);
		mColor = color;
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

		//sets background color for linearLayout
		((LinearLayout) listItemView.findViewById(R.id.text_parent_linear_layout))
				.setBackgroundResource(mColor);
		//sets the text for miwok_text_view
		((TextView) listItemView.findViewById(R.id.miwok_text_view)).setText((
				(Word) getItem(position)).getMiwokTranslation());
		//sets the text for default_text_view
		((TextView) listItemView.findViewById(R.id.default_text_view)).setText(
				((Word) getItem(position)).getDefaultTranslation());
		//sets the image for image_view
		ImageView imageView = listItemView.findViewById(R.id.image);
		if (((Word) (getItem(position))).getImageResourceId() == 0)
		{
			imageView.setVisibility(View.GONE);
		} else
		{
			imageView.setImageResource(((Word) getItem(position)).getImageResourceId());
		}

		return listItemView;
	}
}
