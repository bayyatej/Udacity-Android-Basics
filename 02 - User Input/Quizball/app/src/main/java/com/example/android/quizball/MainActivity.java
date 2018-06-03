package com.example.android.quizball;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{

	private int score = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	/**
	 * scores quiz and calls display score
	 *
	 * @param view: submit button
	 */
	public void scoreQuiz(View view)
	{
		//Check answers for question 1
		EditText question_1_editText = findViewById(R.id.edit_text_question_1);
		String ans_1 = question_1_editText.getText().toString().toLowerCase();
		boolean answer_correct = false;

		if (ans_1.equals("damascus"))
		{
			score += 15;
			answer_correct = true;
		}
		setColor(question_1_editText, answer_correct);

		//Score Question 2
		answer_correct = false;
		CheckBox dabo = findViewById(R.id.dabo);
		CheckBox nick = findViewById(R.id.nick);
		CheckBox urban = findViewById(R.id.urban);
		CheckBox jim = findViewById(R.id.jim);

		if (dabo.isChecked())
		{
			score += 5;
			answer_correct = true;
		}
		setColor(dabo, answer_correct);
		answer_correct = false;
		if (nick.isChecked())
		{
			score += 5;
			answer_correct = true;
		}
		setColor(nick, answer_correct);
		answer_correct = false;
		if (urban.isChecked())
		{
			score += 5;
			answer_correct = true;
		}
		setColor(urban, answer_correct);
		answer_correct = false;
		if (!jim.isChecked())
		{
			answer_correct = true;
		}
		setColor(jim, answer_correct);

		//Score Question 3
		RadioGroup group = findViewById(R.id.question_3_radio_group);
		int checkedButtonId = group.getCheckedRadioButtonId();
		RadioButton selectedButton = findViewById(checkedButtonId);
		answer_correct = false;

		if (getResources().getResourceEntryName(selectedButton.getId()).equals("ucf"))
		{
			score += 10;
			answer_correct = true;
			setColor(findViewById(R.id.ucf), answer_correct);
		}
		answer_correct = false;
		setColor(findViewById(R.id.fau), answer_correct);
		setColor(findViewById(R.id.uab), answer_correct);
		setColor(findViewById(R.id.idaho), answer_correct);

		//Check answers for question 4
		EditText question_4_editText = findViewById(R.id.edit_text_question_4);
		String ans_4 = question_4_editText.getText().toString().toLowerCase();

		if (ans_4.equals("23"))
		{
			score += 5;
			answer_correct = true;
		}
		setColor(question_4_editText, answer_correct);

		displayScore();
	}

	public void reset(View view)
	{
		score = 0;
		displayScore();
	}

	/*
	 * Helper Methods
	 */

	/**
	 * displays player's score
	 */
	private void displayScore()
	{
		TextView scoreView = findViewById(R.id.score);
		scoreView.setText(String.valueOf(score));
	}

	/**
	 * sets color of text view based on whether the answer is right
	 *
	 * @param answerRight: true if answer right, false otherwise
	 *                     view: view to change color of
	 */
	private void setColor(View view, boolean answerRight)
	{
		if (!answerRight)
		{
			view.setBackgroundColor(Color.parseColor("#e53935"));
		} else
		{
			view.setBackgroundColor(Color.parseColor("#43A047"));
		}
	}
}
