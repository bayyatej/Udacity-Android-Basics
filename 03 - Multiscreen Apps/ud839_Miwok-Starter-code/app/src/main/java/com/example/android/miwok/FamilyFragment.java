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
public class FamilyFragment extends Fragment
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


	public FamilyFragment()
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
		words.add(new Word("father", "әpә", R.drawable.family_father, R.raw.family_father));
		words.add(new Word("mother", "әṭa", R.drawable.family_mother, R.raw.family_mother));
		words.add(new Word("son", "angsi", R.drawable.family_son, R.raw.family_son));
		words.add(new Word("daughter", "tune",
				R.drawable.family_daughter, R.raw.family_daughter));
		words.add(new Word("older brother", "taachi",
				R.drawable.family_older_brother, R.raw.family_older_brother));
		words.add(new Word("younger brother", "chalitti",
				R.drawable.family_younger_brother, R.raw.family_younger_brother));
		words.add(new Word("older sister", "teṭe",
				R.drawable.family_older_sister, R.raw.family_older_sister));
		words.add(new Word("younger sister", "kolliti",
				R.drawable.family_younger_sister, R.raw.family_younger_sister));
		words.add(new Word("grandmother", "ama",
				R.drawable.family_grandmother, R.raw.family_grandmother));
		words.add(new Word("grandfather", "paapa",
				R.drawable.family_grandfather, R.raw.family_grandfather));

		adapter = new WordAdapter(getActivity(), words, R.color.category_family);


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
