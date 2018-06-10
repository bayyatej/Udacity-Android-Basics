package com.example.android.tourguide;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ParkFragment extends Fragment
{
	private final String PARKS_AND_RECREATION_PHONE_NUMBER = "2483470400";

	public ParkFragment()
	{
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.destination_list, container, false);
		ListView parkDestinationList = rootView.findViewById(R.id.destination_list);

		final ArrayList<Destination> parks = new ArrayList<>();

		//populate parks with parks
		parks.add(new Destination("Fuerst Park", PARKS_AND_RECREATION_PHONE_NUMBER));
		parks.add(new Destination("Lakeshore Park", PARKS_AND_RECREATION_PHONE_NUMBER));
		parks.add(new Destination("Maybury Park", PARKS_AND_RECREATION_PHONE_NUMBER));

		//Attach DestinationAdapter
		DestinationAdapter parkAdapter = new DestinationAdapter(getActivity(), parks, R.color.parks);
		parkDestinationList.setAdapter(parkAdapter);
		rootView.setBackgroundResource(R.color.parks);

		return rootView;
	}

}
