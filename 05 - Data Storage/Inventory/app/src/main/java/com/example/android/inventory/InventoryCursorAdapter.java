package com.example.android.inventory;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.inventory.data.InventoryContract.InventoryEntry;

public class InventoryCursorAdapter extends CursorAdapter
{
	private Context mContext;
	private View mCoordinator;

	InventoryCursorAdapter(Context context, Cursor c, int flags)
	{
		super(context, c, flags);
		mContext = context;
		mCoordinator = ((Activity) context).getWindow().getDecorView().findViewById(R.id.parent);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent)
	{
		return LayoutInflater.from(context).inflate(R.layout.inventory_list_item, parent, false);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor)
	{
		ImageView inventoryImageView = view.findViewById(R.id.image);
		TextView inventoryNameView = view.findViewById(R.id.name);
		TextView inventoryDetailView = view.findViewById(R.id.details);
		TextView inventoryQuantityView = view.findViewById(R.id.quantity);
		Button inventorySaleBtn = view.findViewById(R.id.sale_btn);

		int idIndex = cursor.getColumnIndex(InventoryEntry._ID);
		int imageIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_PICTURE);
		int nameIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_NAME);
		int detailIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_DESCRIPTION);
		int quantityIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_QUANTITY);
		int priceIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_PRICE);

		final int id = cursor.getInt(idIndex);
		final String name = cursor.getString(nameIndex);
		final String details = cursor.getString(detailIndex);
		final int price = cursor.getInt(priceIndex);
		final int quantity = cursor.getInt(quantityIndex);
		final byte[] image = cursor.getBlob(imageIndex);


		String qtyString = "Quantity: " + String.valueOf(quantity);
		String saleString = "Buy @ $" + String.valueOf(price);

		Glide.with(context).load(image).into(inventoryImageView);
		inventoryNameView.setText(name);
		inventoryDetailView.setText(details);
		inventoryQuantityView.setText(qtyString);
		inventorySaleBtn.setText(saleString);

		inventorySaleBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (quantity > 0)
				{
					int newQuantity = quantity - 1;
					Uri uri = Uri.withAppendedPath(InventoryEntry.CONTENT_URI, String.valueOf(id));
					ContentValues contentValues = new ContentValues();

					contentValues.put(InventoryEntry.COLUMN_PRODUCT_NAME, name);
					contentValues.put(InventoryEntry.COLUMN_PRODUCT_DESCRIPTION, details);
					contentValues.put(InventoryEntry.COLUMN_PRODUCT_PRICE, price);
					contentValues.put(InventoryEntry.COLUMN_PRODUCT_QUANTITY, newQuantity);
					contentValues.put(InventoryEntry.COLUMN_PRODUCT_PICTURE, image);

					mContext.getContentResolver().update(uri, contentValues, null, null);
					notifyDataSetChanged();
				} else
				{
					Snackbar.make(mCoordinator, "We are out of the item you tried to buy", Snackbar.LENGTH_LONG).show();
					//todo snackbar not showing
				}
			}
		});
	}
}
