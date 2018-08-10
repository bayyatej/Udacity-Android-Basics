package com.example.android.inventory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
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

	InventoryCursorAdapter(Context context, Cursor c, int flags)
	{
		super(context, c, flags);
		mContext = context;
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent)
	{
		return LayoutInflater.from(context).inflate(R.layout.inventory_list_item, parent, false);
	}

	@Override
	public void bindView(View view, Context context, final Cursor cursor)
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
		final int quantity = cursor.getInt(quantityIndex);
		int price = cursor.getInt(priceIndex);
		String qtyString = "Quantity: " + String.valueOf(quantity);
		String saleString = "Buy @ $" + String.valueOf(price);

		Glide.with(context).load(cursor.getBlob(imageIndex)).into(inventoryImageView);
		inventoryNameView.setText(cursor.getString(nameIndex));
		inventoryDetailView.setText(cursor.getString(detailIndex));
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

					ContentValues contentValues = new ContentValues();
					DatabaseUtils.cursorRowToContentValues(cursor, contentValues);
					contentValues.remove(InventoryEntry.COLUMN_PRODUCT_QUANTITY);
					contentValues.put(InventoryEntry.COLUMN_PRODUCT_QUANTITY, newQuantity);
					Uri uri = Uri.withAppendedPath(InventoryEntry.CONTENT_URI, String.valueOf(id));
					mContext.getContentResolver().update(uri, contentValues, null, null);
					notifyDataSetChanged();
				} else
				{
					Snackbar.make(v, "We are out of the item you tried to buy", Snackbar.LENGTH_LONG);
					//todo snackbar not showing
				}
			}
		});
	}
}
