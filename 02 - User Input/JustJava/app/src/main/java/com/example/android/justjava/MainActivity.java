package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
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
		EditText nameEdit = findViewById(R.id.name_edit_text);
		String name = nameEdit.getText().toString();
		CheckBox whipBox = findViewById(R.id.whipped_cream_box);
		boolean addWhip = whipBox.isChecked();
		CheckBox chocolateBox = findViewById(R.id.chocolate_box);
		boolean addChocolate = chocolateBox.isChecked();

		displayMessage(name, addWhip, addChocolate);
	}

	/**
	 * This method increments the quantity
	 */
	public void increment(View view)
	{
		if (quantity <= 99)
		{
			quantity++;
			displayQuantity(quantity);
		}
	}

	/**
	 * This method decrements the quantity
	 */
	public void decrement(View view)
	{
		if (quantity > 0)
		{
			quantity--;
			displayQuantity(quantity);
		}
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
	private void displayMessage(String name, boolean whip, boolean chocolate)
	{
		String message = createOrderSummary(name, calculatePrice(), whip, chocolate);
		Intent mailIntent = new Intent(Intent.ACTION_VIEW);

		mailIntent.setAction(Intent.ACTION_SENDTO);
		mailIntent.putExtra(Intent.EXTRA_SUBJECT, ("Just Java Order for " + name));
		mailIntent.putExtra(Intent.EXTRA_TEXT, message);
		mailIntent.setData(Uri.parse("mailto:"));
		
		if (mailIntent.resolveActivity(getPackageManager()) != null)
		{
			startActivity(mailIntent);
		}
	}

	/**
	 * Returns order summary
	 */
	private String createOrderSummary(String name, int price, boolean whip, boolean chocolate)
	{

		String whipString = "Whip";
		if (!whip)
		{
			whipString = "No " + whipString;
		} else
		{
			price += 1 * quantity;
		}
		String chocolateString = "Chocolate";
		if (!chocolate)
		{
			chocolateString = "No " + chocolateString;
		} else
		{
			price += 2 * quantity;
		}
		String priceString = NumberFormat.getCurrencyInstance().format(price);

		return ("Name: " + name + "\n" + whipString + "\n" + chocolateString + " \nQuantity: "
				+ quantity + " \nTotal: " + priceString + " \nThank You!");
	}
}
