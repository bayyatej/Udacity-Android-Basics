package com.example.android.bookfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
{
	private Intent bookListIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bookListIntent = new Intent(getApplicationContext(), BookActivity.class);
	}

	public void onSearch(View view)
	{
		String query = ((EditText) findViewById(R.id.search_box)).getText().toString();
		bookListIntent.putExtra("query", query);
		startActivity(bookListIntent);
	}
}
