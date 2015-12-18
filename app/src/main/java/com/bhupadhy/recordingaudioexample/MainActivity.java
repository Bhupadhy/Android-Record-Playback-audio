package com.bhupadhy.recordingaudioexample;

import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";
    private static final int permissionRequestCode = 0;
    private String filename;

    public MediaRecorder recorder;
    private RecordButton record;

    private PlayButton play;
    public MediaPlayer player;

    private boolean permissionsGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUI();
    }

    public void onRecord(boolean start) {
        if(!checkPermissions()) return;
        if(start)
            startRecording();
        else
            stopRecording();
    }

    public void onPlay(boolean start) {
        if(!checkPermissions()) return;
        if(start)
            startPlaying();
        else
            stopPlaying();
    }

    private void startRecording(){
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE)){
            Toast.makeText(this,"This device doesn't have a mic!", Toast.LENGTH_LONG).show();
            return;
        }
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        filename = Environment.getExternalStorageDirectory().getAbsolutePath();
        filename += "/audiorecordtest.3gp";
        recorder.setOutputFile(filename);
        try{
            recorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "record prepare() failed");
        }
        recorder.start();
    }

    private void stopRecording(){
        recorder.stop();
        recorder.reset();
        recorder.release();
        recorder = null;
    }

    private void startPlaying(){
        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try{
            player.setDataSource(filename);
            player.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "play prepare() failed");
        }
        player.start();
    }

    private void stopPlaying(){
        player.release();
        player = null;
    }

    @Override
    public void onPause(){
        super.onPause();
        if(recorder != null){
            recorder.release();
            recorder = null;
        }

        if(player != null){
            player.release();
            player = null;
        }
    }

    private void setUI(){
        record = new RecordButton(this);

        play = new PlayButton(this);

    }

    private boolean checkPermissions(){
        if(permissionsGranted) return true;

        ArrayList<String> permissions = new ArrayList<>();
        // Write to Storage Permission
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            permissions.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        // Read from Storage Permission
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            permissions.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        // Record Audio Permission
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED){
            permissions.add(android.Manifest.permission.RECORD_AUDIO);
        }
        if(permissions.size() > 0) {
            ActivityCompat.requestPermissions(this,
                    permissions.toArray(new String[permissions.size()]), permissionRequestCode);
        } else{
            permissionsGranted = true;
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == permissionRequestCode){
            if(grantResults.length > 0){
                boolean granted = false;
                for(int result : grantResults){
                    if(result != PackageManager.PERMISSION_GRANTED){
                        granted = false;
                        break;
                    } else {
                        granted = true;
                    }
                }
                if(granted){
                    permissionsGranted = true;
                } else {
                    Toast.makeText(this,
                            "These permissions are required for the app to function",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}
