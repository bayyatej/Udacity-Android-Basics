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

	private int calculatePrice()
	{
		return quantity * 5;
	}

	/**
	 * This method is called when the order button is clicked.
	 */
	public void submitOrder(View view)
	{
		int price = calculatePrice();
		String priceMessage = "Amount Due: " + NumberFormat.getCurrencyInstance().format(price);
		displayMessage(priceMessage);

	}

	/**
	 * This method increments the quantity
	 */
	public void increment(View view)
	{
		quantity++;
		displayQuantity(quantity);
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
		displayQuantity(quantity);
	}

	/**
	 * This method displays the given quantity value on the screen.
	 */
	private void displayQuantity(int number)
	{
		TextView quantityTextView = findViewById(R.id.quantity_text_view);
		quantityTextView.setText("" + number);
	}

	/**
	 * This method displays the given message
	 */
	private void displayMessage(String message)
	{
		message = createOrderSummary(calculatePrice());
		TextView orderTextView = findViewById(R.id.order_summary_text_view);
		orderTextView.setText(message);
	}

	/**
	 * Returns order summary
	 */
	private String createOrderSummary(int price)
	{
		String priceString = NumberFormat.getCurrencyInstance().format(price);
		return ("Name: Steve the Fiend \nQuantity: " + quantity + " \nTotal: " + priceString +
				" \nThank You!");
	}
}
