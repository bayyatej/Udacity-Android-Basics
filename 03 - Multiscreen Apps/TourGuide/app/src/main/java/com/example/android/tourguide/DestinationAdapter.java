package com.example.android.tourguide;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class DestinationAdapter extends ArrayAdapter
{
	private int mColorId;

	/**
	 * @param context
	 * @param destinations
	 * @param colorId
	 */
	public DestinationAdapter(Context context, ArrayList<Destination> destinations, int colorId)
	{
		super(context, 0, destinations);
		mColorId = colorId;
	}

	@NonNull
	@Override
	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
	{
		final Context context = getContext();
		final int color = ContextCompat.getColor(context, mColorId);
		View layoutListItemView = convertView;
		if (layoutListItemView == null)
		{
			layoutListItemView = LayoutInflater.from(context).inflate(R.layout.layout_list_item, parent, false);
		}

		final Destination destination = (Destination) getItem(position);

		//Set destination image
		ImageView destinationImageView = layoutListItemView.findViewById(R.id.destination_image);
		showHideImage(destination, destinationImageView);
		//Set destination name
		TextView destinationNameView = layoutListItemView.findViewById(R.id.destination_name);
		destinationNameView.setText(destination.getName());
		//Attach OnClickListener to Address Button
		Button addressButton = layoutListItemView.findViewById(R.id.address_button);
		addressButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(destination.getApiQueryUrl()));
				if (intent.resolveActivity(context.getPackageManager()) != null)
				{
					context.startActivity(intent);
				}
			}
		});
		//Attach OnClickListener to Call Button
		Button callButton = layoutListItemView.findViewById(R.id.call_button);
		callButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(Intent.ACTION_DIAL);
				intent.setData(Uri.parse("tel:" + destination.getPhoneNumber()));
				if (intent.resolveActivity(context.getPackageManager()) != null)
				{
					context.startActivity(intent);
				}

			}
		});
		//Set background of destination details view
		RelativeLayout destinationDetailsRelativeLayout = layoutListItemView.findViewById(R.id.destination_details_parent);
		destinationDetailsRelativeLayout.setBackgroundColor(color);

		return layoutListItemView;
	}

	/**
	 * Hides image view if there is no image attached to destination (IMAGE_ID == 0). Otherwise sets imageView's source to destination's imageId
	 *
	 * @param destination: Destination with imageId
	 * @param imageView:   Image view to hide/set resource
	 */
	private void showHideImage(Destination destination, ImageView imageView)
	{
		final int IMAGE_ID = destination.getImageId();

		if (IMAGE_ID == 0)
		{
			imageView.setVisibility(View.GONE);
		} else
		{
			imageView.setImageResource(IMAGE_ID);
		}
	}
}
