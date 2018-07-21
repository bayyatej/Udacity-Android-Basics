package com.example.android.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
{
	private Intent newsListIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		newsListIntent = new Intent(getApplicationContext(), NewsActivity.class);
	}

	public void onClickSearch(View view)
	{
		String query = ((EditText) findViewById(R.id.search)).getText().toString();
		newsListIntent.putExtra("query", query);
		startActivity(newsListIntent);
	}
}
