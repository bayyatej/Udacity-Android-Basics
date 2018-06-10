package com.example.android.tourguide;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CafeFragment extends Fragment
{


	public CafeFragment()
	{
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.destination_list, container, false);
		ListView cafeDestinationList = rootView.findViewById(R.id.destination_list);

		final ArrayList<Destination> cafes = new ArrayList<>();

		//populate cafes with cafes
		cafes.add(new Destination("No.VI Coffee and Tea", "2483082879"));
		cafes.add(new Destination("Starbucks", "2483803774"));
		cafes.add(new Destination("Panera Bread", "2483741701"));

		//Attach a DestinationAdapter to the ListView
		DestinationAdapter cafeAdapter = new DestinationAdapter(getActivity(), cafes, R.color.cafes);
		cafeDestinationList.setAdapter(cafeAdapter);
		rootView.setBackgroundResource(R.color.cafes);

		return rootView;
	}

}
