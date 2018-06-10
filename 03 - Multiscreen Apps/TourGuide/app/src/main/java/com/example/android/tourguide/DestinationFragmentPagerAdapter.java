package com.example.android.tourguide;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class DestinationFragmentPagerAdapter extends FragmentPagerAdapter
{
	private Context mContext;

	DestinationFragmentPagerAdapter(FragmentManager fm)
	{
		super(fm);
	}

	@Override
	public Fragment getItem(int position)
	{
		Log.d("getitem", "destinationPAgerAdapter");
		if (position == 0)
		{
			return new RestaurantFragment();
		} else if (position == 1)
		{
			return new CafeFragment();
		} else
		{
			return new ParkFragment();
		}
	}

	@Override
	public int getCount()
	{
		return 3;
	}

	@Nullable
	@Override
	public CharSequence getPageTitle(int position)
	{
		if (position == 1)
		{
			return mContext.getString(R.string.category_restaurants);
		} else if (position == 2)
		{
			return mContext.getString(R.string.category_cafes);
		} else
		{
			return mContext.getString(R.string.category_parks);
		}
	}

	public void setContext(Context context)
	{
		mContext = context;
	}
}
