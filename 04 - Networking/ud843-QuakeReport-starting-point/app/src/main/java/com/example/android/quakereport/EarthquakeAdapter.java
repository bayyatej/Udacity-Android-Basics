package com.example.android.quakereport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
		Earthquake earthquake = (Earthquake) getItem(position);

		if (earthQuakeListItemView == null)
		{
			earthQuakeListItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item, parent, false);
		}

		//Populate the magnitude view
		((TextView) earthQuakeListItemView.findViewById(R.id.magnitude)).setText(String.valueOf(earthquake.getMagnitude()));
		//Populate location view
		((TextView) earthQuakeListItemView.findViewById(R.id.location)).setText(earthquake.getLocation());
		//Populate location view
		((TextView) earthQuakeListItemView.findViewById(R.id.date)).setText(earthquake.getDate());

		return earthQuakeListItemView;
	}
}
