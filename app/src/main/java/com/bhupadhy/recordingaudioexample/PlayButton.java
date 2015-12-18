package com.bhupadhy.recordingaudioexample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

/**
 * Created by Bhavik on 12/17/15.
 */
public class PlayButton extends Button {
	boolean startPlaying = true;

	OnClickListener clicker = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// Access onPlay in a non static way
			((MainActivity)getContext()).onPlay(startPlaying);
			if(startPlaying){
				setText("Stop playing");
			} else {
				setText("Start playing");
			}
			startPlaying = !startPlaying;
		}
	};

	public PlayButton(Context context) {
		super(context);
		setText("Start playing");
		setOnClickListener(clicker);
	}
	public PlayButton(Context context, AttributeSet attrs){
		super(context,attrs);
		setText("Start playing");
		setOnClickListener(clicker);
	}
	public PlayButton(Context context, AttributeSet attrs, int defStyle){
		super(context,attrs,defStyle);
		setText("Start playing");
		setOnClickListener(clicker);
	}
}
