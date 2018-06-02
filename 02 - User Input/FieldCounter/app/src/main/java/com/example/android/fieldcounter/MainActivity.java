package com.example.android.fieldcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
	private int scoreForA = 0;
	private int scoreForB = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		displayScore('A');
		displayScore('B');
	}

	private void displayScore(char teamName)
	{
		if (teamName == 'A')
		{
			TextView scoreView = findViewById(R.id.team_a_score);
			scoreView.setText(String.valueOf(scoreForA));
		} else
		{
			TextView scoreView = findViewById(R.id.team_b_score);
			scoreView.setText(String.valueOf(scoreForB));
		}
	}

	/**
	 * helper method to get char A or B of team name
	 *
	 * @param v: View to get team of
	 */
	private char getTeam(View v)
	{
		String viewId = getResources().getResourceEntryName(v.getId());
		return viewId.charAt(viewId.length() - 1);
	}

	public void addSixPoints(View v)
	{
		char viewTeam = getTeam(v);
		if (viewTeam == 'A')
		{
			scoreForA += 6;
		} else
		{
			scoreForB += 6;
		}
		displayScore(viewTeam);
	}

	public void addThreePoints(View v)
	{
		char viewTeam = getTeam(v);
		if (viewTeam == 'A')
		{
			scoreForA += 3;
		} else
		{
			scoreForB += 3;
		}
		displayScore(viewTeam);
	}

	public void addTwoPoints(View v)
	{
		char viewTeam = getTeam(v);
		if (viewTeam == 'A')
		{
			scoreForA += 2;
		} else
		{
			scoreForB += 2;
		}
		displayScore(viewTeam);
	}

	public void addOnePoint(View v)
	{
		char viewTeam = getTeam(v);
		if (viewTeam == 'A')
		{
			scoreForA += 1;
		} else
		{
			scoreForB += 1;
		}
		displayScore(viewTeam);
	}

	public void resetPoints(View v)
	{
		scoreForA = 0;
		scoreForB = 0;
		displayScore('A');
		displayScore('B');
	}
}
