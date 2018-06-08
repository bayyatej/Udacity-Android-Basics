package com.example.android.miwok;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter
{
	private int mColor;
	private static MediaPlayer mMediaPlayer;
	private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener()
	{
		@Override
		public void onCompletion(MediaPlayer mp)
		{
			releaseMediaPlayerHelper();
		}
	};
	private static AudioManager mAudioManager;
	private static AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener;

	public WordAdapter(Activity context, ArrayList<Word> words, int color)
	{
		super(context, 0, words);
		mColor = color;
		mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener()
		{
			@Override
			public void onAudioFocusChange(int focusChange)
			{
				if (focusChange == AudioManager.AUDIOFOCUS_GAIN)
				{
					if (mMediaPlayer != null)
					{
						mMediaPlayer.start();
					}
				} else if (focusChange == AudioManager.AUDIOFOCUS_LOSS)
				{
					if (mMediaPlayer != null)
					{
						mMediaPlayer.stop();
					}
					WordAdapter.releaseMediaPlayerHelper();
				} else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)
				{
					if (mMediaPlayer != null)
					{
						mMediaPlayer.pause();
						mMediaPlayer.seekTo(0);
					}
				}
			}
		};
	}

	@NonNull
	@Override
	public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent)
	{
		View listItemView = convertView;
		if (convertView == null)
		{
			listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,
					parent, false);
		}

		Word word = (Word) getItem(position);

		//sets background color for linearLayout
		(listItemView.findViewById(R.id.text_parent_linear_layout)).setBackgroundResource(mColor);
		//sets the text for miwok_text_view
		((TextView) listItemView.findViewById(R.id.miwok_text_view)).setText(word.getMiwokTranslation());

		//sets the text for default_text_view
		((TextView) listItemView.findViewById(R.id.default_text_view)).setText(word.getDefaultTranslation());

		//sets the image for image_view
		ImageView imageView = listItemView.findViewById(R.id.image);
		if (word.getImageResourceId() == 0)
		{
			imageView.setVisibility(View.GONE);
		} else
		{
			imageView.setImageResource(word.getImageResourceId());
		}

		//sets onClickListener for listView
		final Context playIconContext = listItemView.findViewById(R.id.play_icon_image_view).getContext();
		final int wordAudioId = word.getAudioResourceId();
		listItemView.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View view)
			{
				releaseMediaPlayerHelper();
				mMediaPlayer = MediaPlayer.create(playIconContext, wordAudioId);
				onPlay();
			}
		});

		return listItemView;
	}

	public static void releaseMediaPlayerHelper()
	{
		if (mMediaPlayer != null)
		{
			mMediaPlayer.release();
			mMediaPlayer = null;
			mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
		}
	}

	private void onPlay()
	{
		mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.AUDIOFOCUS_GAIN, AudioManager.STREAM_MUSIC);
		mMediaPlayer.start();
		mMediaPlayer.setOnCompletionListener(onCompletionListener);
	}
}
