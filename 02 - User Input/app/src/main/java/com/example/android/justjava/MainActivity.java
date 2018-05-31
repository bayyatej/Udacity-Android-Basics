package com.example.android.justjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity
{
	private int quantity = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	/**
	 * This method is called when the order button is clicked.
	 */
	public void submitOrder(View view)
	{
		int price = quantity * 5;
		String priceMessage = "Amount Due: " + NumberFormat.getCurrencyInstance().format(price);
		displayMessage(priceMessage);
	}

	/**
	 * This method increments the quantity
	 */
	public void increment(View view)
	{
		quantity++;
		display(quantity);
	}

	/**
	 * This method decrements the quantity
	 */
	public void decrement(View view)
	{
		if (quantity > 0)
		{
			quantity--;
		}
		display(quantity);
	}

	/**
	 * This method displays the given quantity value on the screen.
	 */
	private void display(int number)
	{
		TextView quantityTextView = findViewById(R.id.quantity_text_view);
		quantityTextView.setText("" + number);
	}

	/**
	 * This method displays the given price on the screen.
	 */
	private void displayPrice(int number)
	{
		TextView priceTextView = findViewById(R.id.price_text_view);
		priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
	}

	/**
	 * This method displays the given message
	 */
	private void displayMessage(String message)
	{
		message += "\nThank You!";
		TextView priceTextView = findViewById(R.id.price_text_view);
		priceTextView.setText(message);
	}
}
