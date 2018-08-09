package com.example.android.inventory;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.android.inventory.data.InventoryContract.InventoryEntry;

import java.io.ByteArrayOutputStream;

public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>
{
	/*
	 * Uri of current item. Null when adding a new item, uri of item being edited when editing
	 */
	private Uri mCurrentUri = null;
	/*
	 * True when unsaved changes present, false otherwise
	 */
	private boolean mUnsavedChangesPresent = false;

	/*
	 * Widgets
	 */
	private EditText mNameEditText,
			mDescriptionEditText,
			mPriceEditText,
			mQuantityEditText;
	private ImageButton mTakePictureButton,
			mAddPhotoButton;
	private ImageView mImageView;

	class InventoryTextWatcher implements TextWatcher
	{
		String initialString;

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after)
		{
			initialString = s.toString().trim();
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count)
		{
			Log.i(s.toString(), "onTextChanged: ");
		}

		@Override
		public void afterTextChanged(Editable s)
		{
			if (!mUnsavedChangesPresent)
			{
				if (!initialString.equals(s.toString().trim()))
				{
					mUnsavedChangesPresent = true;
				}
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editor);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		try
		{
			mCurrentUri = getIntent().getData();
		} catch (NullPointerException e)
		{
			this.setTitle("Add Product");
		}
		setupWidgets();

		if (mCurrentUri != null)
		{
			this.setTitle("Edit Product");
			getLoaderManager().initLoader(0, null, this);
		}
	}

	/*
		Helper methods
	 */
	private void setupWidgets()
	{
		mNameEditText = findViewById(R.id.name_edit_text);
		mDescriptionEditText = findViewById(R.id.description_edit_text);
		mPriceEditText = findViewById(R.id.price_edit_text);
		mQuantityEditText = findViewById(R.id.quantity_edit_text);
		mTakePictureButton = findViewById(R.id.add_photo_camera);
		mAddPhotoButton = findViewById(R.id.add_photo);
		mImageView = findViewById(R.id.product_image);

		/*
			Set up listeners
		 */
		mTakePictureButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				if (takePictureIntent.resolveActivity(getPackageManager()) != null)
				{
					startActivityForResult(takePictureIntent, 1);
					//todo have camera close after taking picture
				}
			}
		});
		mAddPhotoButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				//todo add photo
			}
		});
		if (mCurrentUri == null)
		{
			mNameEditText.addTextChangedListener(new InventoryTextWatcher());
			mDescriptionEditText.addTextChangedListener(new InventoryTextWatcher());
			mPriceEditText.addTextChangedListener(new InventoryTextWatcher());
			mQuantityEditText.addTextChangedListener(new InventoryTextWatcher());
		}
	}

	/**
	 * @return: true if successful, false otherwise
	 */
	private boolean saveProduct()
	{
		Snackbar snackbar;
		ContentValues contentValues = new ContentValues();
		String name = mNameEditText.getText().toString().trim();
		String description = mDescriptionEditText.getText().toString().trim();
		String priceString = mPriceEditText.getText().toString().trim();
		int price = 0;
		String qtyString = mQuantityEditText.getText().toString().trim();
		int qty = 0;
		Bitmap bitmap = ((BitmapDrawable) mImageView.getDrawable()).getBitmap();
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
		byte[] imageByteArr = byteArrayOutputStream.toByteArray();

		if (TextUtils.isEmpty(name))
		{
			Snackbar.make(findViewById(R.id.name_edit_text), name + " is invalid", Snackbar.LENGTH_LONG);
		} else if (TextUtils.isEmpty(description))
		{
			Snackbar.make(findViewById(R.id.description_edit_text), description + " is invalid", Snackbar.LENGTH_LONG);
		} else if (TextUtils.isEmpty(priceString))
		{
			Snackbar.make(findViewById(R.id.price_edit_text), priceString + " is invalid", Snackbar.LENGTH_LONG);
		} else if (TextUtils.isEmpty(priceString))
		{
			Snackbar.make(findViewById(R.id.quantity_edit_text), qtyString + " is invalid", Snackbar.LENGTH_LONG);
		} else if (imageByteArr == null)
		{
			Snackbar.make(findViewById(R.id.product_image), "No image provided", Snackbar.LENGTH_LONG);
		} else
		{
			try
			{
				price = Integer.parseInt(priceString);
			} catch (NullPointerException e)
			{
				mPriceEditText.setText("0");
				price = 0;
			}
			if (price <= 0)
			{
				Snackbar.make(findViewById(R.id.price_edit_text), priceString + " must be greater than 0", Snackbar.LENGTH_LONG);
				return false;
			}
			try
			{
				qty = Integer.parseInt(qtyString);
			} catch (NullPointerException e)
			{
				mQuantityEditText.setText("0");
				qty = 0;
			}
			if (qty <= 0)
			{
				Snackbar.make(findViewById(R.id.price_edit_text), String.valueOf(qty) + " must be greater than 0", Snackbar.LENGTH_LONG);
				return false;
			}

			contentValues.put(InventoryEntry.COLUMN_PRODUCT_NAME, name);
			contentValues.put(InventoryEntry.COLUMN_PRODUCT_DESCRIPTION, description);
			contentValues.put(InventoryEntry.COLUMN_PRODUCT_PRICE, price);
			contentValues.put(InventoryEntry.COLUMN_PRODUCT_QUANTITY, qty);
			contentValues.put(InventoryEntry.COLUMN_PRODUCT_PICTURE, imageByteArr);

			if (mCurrentUri == null)
			{
				getContentResolver().insert(InventoryEntry.CONTENT_URI, contentValues);
			} else
			{
				getContentResolver().update(mCurrentUri, contentValues, null, null);
			}
			return true;
		}
		return false;
	}

	/*
		Option Menu Methods
	 */

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_editor_activity, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.save_product:
				if (saveProduct())
				{
					finish();
				}
				return true;
			case R.id.delete_product:
				//todo delete product
				return true;
			case android.R.id.home:
				//todo confirm unsaved changes
				NavUtils.navigateUpFromSameTask(this);
				return true;
		}
		return super.onOptionsItemSelected(item);

	}

	@Override
	public void onBackPressed()
	{
		if (mUnsavedChangesPresent)
		{
			//todo display alert dialog confirming onBackPressed
			//todo fix bug where mUnsavedChanges is true when no unsaved changes present
		} else
		{
			super.onBackPressed();
		}
	}

	/*
		Image Methods
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == 1 && resultCode == RESULT_OK)
		{
			Bundle extras = data.getExtras();
			Bitmap imageBitmap = (Bitmap) extras.get("data");
			mImageView.setImageBitmap(imageBitmap);
		}
	}

	/*
		Loader Callbacks
	 */
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args)
	{
		return new CursorLoader(this, mCurrentUri, null, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data)
	{
		if (data.getCount() > 0)
		{
			data.moveToFirst();
			mNameEditText.setText(data.getString(1));
			mDescriptionEditText.setText(data.getString(2));
			mPriceEditText.setText(String.valueOf(data.getInt(3)));
			mQuantityEditText.setText(String.valueOf(data.getInt(4)));
			Glide.with(this).load(data.getBlob(5)).into(mImageView);
			//set listeners for
			mNameEditText.addTextChangedListener(new InventoryTextWatcher());
			mDescriptionEditText.addTextChangedListener(new InventoryTextWatcher());
			mPriceEditText.addTextChangedListener(new InventoryTextWatcher());
			mQuantityEditText.addTextChangedListener(new InventoryTextWatcher());
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader)
	{
		mNameEditText.setText("");
		mDescriptionEditText.setText("");
		mPriceEditText.setText("");
		mQuantityEditText.setText("");
		mImageView.setImageResource(android.R.color.transparent);
	}
}
