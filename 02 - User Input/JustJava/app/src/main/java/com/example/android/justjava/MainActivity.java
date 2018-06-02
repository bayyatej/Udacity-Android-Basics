package com.example.android.justjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
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
		CheckBox whipBox = findViewById(R.id.whipped_cream_box);
		boolean addWhip = whipBox.isChecked();
		CheckBox chocolateBox = findViewById(R.id.chocolate_box);
		boolean addChocolate = chocolateBox.isChecked();

		displayMessage(addWhip, addChocolate);
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
	private void displayMessage(boolean whip, boolean chocolate)
	{
		String message = createOrderSummary(calculatePrice(), whip, chocolate);
		TextView orderTextView = findViewById(R.id.order_summary_text_view);
		orderTextView.setText(message);
	}

	/**
	 * Returns order summary
	 */
	private String createOrderSummary(int price, boolean whip, boolean chocolate)
	{
		String priceString = NumberFormat.getCurrencyInstance().format(price);
		String whipString = "Whip";
		if (!whip)
		{
			whipString = "No " + whipString;
		}
		String chocolateString = "Chocolate";
		if (!chocolate)
		{
			chocolateString = "No " + chocolateString;
		}
		return ("Name: Steve the Fiend \n" + whipString + "\n" + chocolateString + " \nQuantity: "
				+ quantity + " \nTotal: " + priceString + " \nThank You!");
	}
}
