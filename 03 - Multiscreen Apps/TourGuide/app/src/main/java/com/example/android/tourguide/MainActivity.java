package com.example.android.tourguide;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ViewPager viewPager = findViewById(R.id.view_pager);
		DestinationFragmentPagerAdapter destinationFragmentPagerAdapter = new DestinationFragmentPagerAdapter(getSupportFragmentManager());
		destinationFragmentPagerAdapter.setContext(this);
		viewPager.setAdapter(destinationFragmentPagerAdapter);
		TabLayout tabLayout = findViewById(R.id.tab_layout);
		tabLayout.setupWithViewPager(viewPager);
	}
}
