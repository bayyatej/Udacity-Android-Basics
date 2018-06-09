package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumbersFragment extends Fragment
{
	private MediaPlayer mMediaPlayer;
	private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener()
	{
		@Override
		public void onCompletion(MediaPlayer mp)
		{
			releaseMediaPlayer();
		}
	};
	private AudioManager mAudioManager;
	private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener()
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
				releaseMediaPlayer();
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

	public NumbersFragment()
	{
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.word_list, container, false);
		ListView listView = rootView.findViewById(R.id.list);
		WordAdapter adapter;
		mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
		final ArrayList<Word> words = new ArrayList<>();
		words.add(new Word("one", "lutti", R.drawable.number_one, R.raw.number_one));
		words.add(new Word("two", "otiiko", R.drawable.number_two, R.raw.number_two));
		words.add(new Word("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
		words.add(new Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
		words.add(new Word("five", "massokka", R.drawable.number_five, R.raw.number_five));
		words.add(new Word("six", "temmokka", R.drawable.number_six, R.raw.number_six));
		words.add(new Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
		words.add(new Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
		words.add(new Word("nine", "wo’e", R.drawable.number_nine, R.raw.number_nine));
		words.add(new Word("ten", "na’aacha", R.drawable.number_ten, R.raw.number_ten));

		adapter = new WordAdapter(getActivity(), words, R.color.category_numbers);


		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				releaseMediaPlayer();
				Word word = words.get(position);
				int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

				if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
				{
					mMediaPlayer = MediaPlayer.create(getActivity(), word.getAudioResourceId());
					mMediaPlayer.start();
					mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
				}
			}
		});

		return rootView;
	}

	@Override
	public void onStop()
	{
		super.onStop();

		// When the activity is stopped, release the media player resources because we won't
		// be playing any more sounds.
		releaseMediaPlayer();
	}

	/**
	 * releases media player if it is not null and abandons audio focus to clear up resources
	 */
	public void releaseMediaPlayer()
	{
		if (mMediaPlayer != null)
		{
			mMediaPlayer.release();
			mMediaPlayer = null;
			mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
		}
	}
}
