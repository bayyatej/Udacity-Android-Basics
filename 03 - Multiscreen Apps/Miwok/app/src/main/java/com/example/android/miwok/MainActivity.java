/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// Set the content of the activity to use the activity_main.xml layout file
		setContentView(R.layout.activity_main);

		// Find the View that shows the numbers category
		TextView numbers = (TextView) findViewById(R.id.numbers);

		// Set a click listener on that View
		numbers.setOnClickListener(new View.OnClickListener()
		{
			// The code in this method will be executed when the numbers View is clicked on.
			@Override
			public void onClick(View view)
			{
				//number intent
				Intent numbersIntent = new Intent(MainActivity.this,
						NumbersActivity.class);
				startActivity(numbersIntent);
			}
		});

		//OnClick for remaining Views

		//Get remaining views
		TextView familyMembers = findViewById(R.id.family);
		TextView colors = findViewById(R.id.colors);
		TextView phrases = findViewById(R.id.phrases);

		//onClick for familyMembers
		familyMembers.setOnClickListener(new View.OnClickListener()
		{
			// The code in this method will be executed when the familyMembers View is clicked on.
			@Override
			public void onClick(View view)
			{
				//familyMember intent
				Intent familyMembersIntent = new Intent(MainActivity.this,
						FamilyActivity.class);
				startActivity(familyMembersIntent);
			}
		});

		//onClick for Colors
		colors.setOnClickListener(new View.OnClickListener()
		{
			// The code in this method will be executed when the colors View is clicked on.
			@Override
			public void onClick(View view)
			{
				//color intent
				Intent colorsIntent = new Intent(MainActivity.this,
						ColorsActivity.class);
				startActivity(colorsIntent);
			}
		});

		//onClick for phrases
		phrases.setOnClickListener(new View.OnClickListener()
		{
			// The code in this method will be executed when the phrases View is clicked on.
			@Override
			public void onClick(View view)
			{
				//phrase intent
				Intent phrasesIntent = new Intent(MainActivity.this,
						PhrasesActivity.class);
				startActivity(phrasesIntent);
			}
		});

	}

//	public void openNumbersList(View view)
//	{
//		Intent intent = new Intent(this, NumbersActivity.class);
//		startActivity(intent);
//	}
}
