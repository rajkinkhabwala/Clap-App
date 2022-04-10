package com.example.clap;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    Sensor mobileProximity;
    SensorManager sensorManager;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mobileProximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    @Override
    protected void onResume(){
        super.onResume();

        sensorManager.registerListener(proximityListener, mobileProximity, 3*1000*1000);

    }

    @Override
    protected void onStop() {

        super.onStop();

        if (mediaPlayer != null) {

            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onPause() {

        super.onPause();
        sensorManager.unregisterListener(proximityListener);
    }
    final private SensorEventListener proximityListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent s) {
            float val = s.values[0];

            if(val < 2f) {

                mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.clap);
                mediaPlayer.start();
            }
            else {

                if(mediaPlayer != null) {
                    mediaPlayer.stop();
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sr, int accurateRate) {
        }
    };

}