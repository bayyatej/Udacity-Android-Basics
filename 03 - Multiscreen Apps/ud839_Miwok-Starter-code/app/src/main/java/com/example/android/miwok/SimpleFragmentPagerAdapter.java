package com.example.android.miwok;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter
{
	SimpleFragmentPagerAdapter(FragmentManager fm)
	{
		super(fm);
	}

	@Override
	public Fragment getItem(int position)
	{
		if (position == 0)
		{
			return new NumbersFragment();
		} else if (position == 1)
		{
			return new FamilyFragment();
		} else if (position == 2)
		{
			return new ColorsFragment();
		} else
		{
			return new PhrasesFragment();
		}
	}

	@Override
	public int getCount()
	{
		return 4;
	}

	@Nullable
	@Override
	public CharSequence getPageTitle(int position)
	{
		if (position == 0)
		{
			return "Numbers";
		} else if (position == 1)
		{
			return "Family";
		} else if (position == 2)
		{
			return "Colors";
		} else
		{
			return "Phrases";
		}
	}
}
