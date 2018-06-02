package com.example.android.courtcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
	private int scoreTeamA = 0;
	private int scoreTeamB = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		displayForTeamA(scoreTeamA);
		displayForTeamB(scoreTeamB);
	}

	//Team A methods
	public void displayForTeamA(int score)
	{
		TextView scoreView = findViewById(R.id.team_a_score);
		scoreView.setText(String.valueOf(score));
	}

	public void addThreeForA(View view)
	{
		scoreTeamA += 3;
		displayForTeamA(scoreTeamA);
	}

	public void addTwoForA(View view)
	{
		scoreTeamA += 2;
		displayForTeamA(scoreTeamA);
	}

	public void addOneForA(View view)
	{
		scoreTeamA += 1;
		displayForTeamA(scoreTeamA);
	}

	//Team B methods
	public void displayForTeamB(int score)
	{
		TextView scoreView = findViewById(R.id.team_b_score);
		scoreView.setText(String.valueOf(score));
	}

	public void addThreeForB(View view)
	{
		scoreTeamB += 3;
		displayForTeamB(scoreTeamB);
	}

	public void addTwoForB(View view)
	{
		scoreTeamB += 2;
		displayForTeamB(scoreTeamB);
	}

	public void addOneForB(View view)
	{
		scoreTeamB += 1;
		displayForTeamB(scoreTeamB);
	}

	//common methods
	public void reset(View view)
	{
		scoreTeamA = 0;
		scoreTeamB = 0;

		displayForTeamA(scoreTeamA);
		displayForTeamB(scoreTeamB);
	}
}
