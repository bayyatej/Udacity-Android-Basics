package com.example.android.quakereport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

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
		return super.getView(position, convertView, parent);
	}
}
