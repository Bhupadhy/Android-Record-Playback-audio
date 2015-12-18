package com.bhupadhy.recordingaudioexample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

/**
 * Created by Bhavik on 12/17/15.
 */
public class RecordButton extends Button {
	boolean startRecording = true;

	OnClickListener clicker = new OnClickListener() {
		@Override
		public void onClick(View v) {
			((MainActivity)getContext()).onRecord(startRecording);
			if(startRecording){
				setText("Stop recording");
			} else {
				setText("Start recording");
			}
			startRecording = !startRecording;
		}
	};

	public RecordButton(Context context) {
		super(context);
		setText("Start recording");
		setOnClickListener(clicker);
	}
	public RecordButton(Context context, AttributeSet attrs) {
		super(context,attrs);
		setText("Start recording");
		setOnClickListener(clicker);
	}
	public RecordButton(Context context, AttributeSet attrs, int defStyle) {
		super(context,attrs,defStyle);
		setText("Start recording");
		setOnClickListener(clicker);
	}
}
