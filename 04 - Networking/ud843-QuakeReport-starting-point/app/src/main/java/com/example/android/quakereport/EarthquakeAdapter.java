package com.example.android.quakereport;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class EarthquakeAdapter extends ArrayAdapter
{
	public EarthquakeAdapter(Context context, ArrayList<Earthquake> earthquakes)
	{
		super(context, 0, earthquakes);
	}

	@NonNull
	@Override
	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
	{
		View earthQuakeListItemView = convertView;
		final Earthquake earthquake = (Earthquake) getItem(position);

		if (earthQuakeListItemView == null)
		{
			earthQuakeListItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item, parent, false);
		}

		earthQuakeListItemView = colorViewOnMagnitude(earthQuakeListItemView, earthquake);

		String rawLocation = earthquake.getLocation();
		String locationOffset = "";
		String location;
		String date = earthquake.getDate();

		int dateIndex = date.length() - 5;
		int endIndexLocationOffset = 2;
		int i = 0;

		String time = date.substring(dateIndex, date.length());
		date = date.substring(0, dateIndex);

		while (!rawLocation.substring(i, endIndexLocationOffset).equals("of") && endIndexLocationOffset < rawLocation.length())
		{
			i++;
			endIndexLocationOffset++;
		}
		if (endIndexLocationOffset < rawLocation.length())
		{
			locationOffset = rawLocation.substring(0, endIndexLocationOffset + 1);
			location = rawLocation.substring(endIndexLocationOffset + 1, rawLocation.length());
			//Populate location offset
			((TextView) earthQuakeListItemView.findViewById(R.id.location_offset)).setText(locationOffset);
		} else
		{
			location = rawLocation;
		}

		LinearLayout dateTimeView = earthQuakeListItemView.findViewById(R.id.dateTime);

		//Populate the magnitude view
		((TextView) earthQuakeListItemView.findViewById(R.id.magnitude)).setText(String.valueOf(earthquake.getMagnitude()));
		//Populate location view
		((TextView) earthQuakeListItemView.findViewById(R.id.location)).setText(location);
		//Populate date view
		((TextView) dateTimeView.findViewById(R.id.date)).setText(date);
		//Populate time view
		((TextView) dateTimeView.findViewById(R.id.time)).setText(time);

		return attachUrl(earthQuakeListItemView, earthquake);
	}

	private View colorViewOnMagnitude(View earthQuakeListItem, Earthquake earthquake)
	{
		int magnitude = (int) earthquake.getMagnitude();
		switch (magnitude)
		{
			case (10):
				earthQuakeListItem.setBackgroundResource(R.color.magnitude10plus);
				break;
			case (9):
				earthQuakeListItem.setBackgroundResource(R.color.magnitude9);
				break;
			case (8):
				earthQuakeListItem.setBackgroundResource(R.color.magnitude8);
				break;
			case (7):
				earthQuakeListItem.setBackgroundResource(R.color.magnitude7);
				break;
			case (6):
				earthQuakeListItem.setBackgroundResource(R.color.magnitude6);
				break;
			case (5):
				earthQuakeListItem.setBackgroundResource(R.color.magnitude5);
				break;
			case (4):
				earthQuakeListItem.setBackgroundResource(R.color.magnitude4);
				break;
			case (3):
				earthQuakeListItem.setBackgroundResource(R.color.magnitude3);
				break;
			case (2):
				earthQuakeListItem.setBackgroundResource(R.color.magnitude2);
				break;
			case (1):
				earthQuakeListItem.setBackgroundResource(R.color.magnitude1);
				break;
		}
		return earthQuakeListItem;
	}

	private View attachUrl(View earthQuakeListItemView, final Earthquake earthquake)
	{
		final Context context = earthQuakeListItemView.getContext();
		earthQuakeListItemView.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(earthquake.getUrl()));
				if (intent.resolveActivity(context.getPackageManager()) != null)
				{
					context.startActivity(intent);
				}
			}
		});
		return earthQuakeListItemView;
	}
}
