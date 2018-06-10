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
public class RestaurantFragment extends Fragment
{


	public RestaurantFragment()
	{
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.destination_list, container, false);
		ListView restaurantDestinationList = rootView.findViewById(R.id.destination_list);

		final ArrayList<Destination> restaurants = new ArrayList<Destination>();

		//populate restaurants with restaurants
		restaurants.add(new Destination("Turmerican", "2483053357"));
		restaurants.add(new Destination("Border Cantina", "2483477827"));
		restaurants.add(new Destination("China Cafe", "2484494888"));

		//Attach a DestinationAdapter to the ListView
		DestinationAdapter restaurantAdapter = new DestinationAdapter(getActivity(), restaurants, R.color.restaurants);
		restaurantDestinationList.setAdapter(restaurantAdapter);

		return rootView;
	}

}
