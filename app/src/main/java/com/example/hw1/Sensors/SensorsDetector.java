package com.example.hw1.Sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class SensorsDetector {
    private Context context;
    private SensorManager sensorManager;
    private Sensor sensor;
    long timeStamp = 0;
    private final int responseTime = 300;
    public interface CallBack_PlaneView {
        void movePlaneWithSensor(int index);
        void changeSpeedWithSensor(int speed);
    }
    private CallBack_PlaneView callBack_planeView;
    public SensorsDetector(Context context,CallBack_PlaneView callBack_planeView) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.callBack_planeView = callBack_planeView;
    }

    private SensorEventListener sensorEventListenerX = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            calculateStepX(x);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private void calculateStepX(float x) {

        if (x > 3.0) {//left
            if (System.currentTimeMillis() - timeStamp > responseTime) {
                timeStamp = System.currentTimeMillis();
                callBack_planeView.movePlaneWithSensor(-1);
            }
        }
        if (x < -3.0) {//right
            if (System.currentTimeMillis() - timeStamp > responseTime) {
                timeStamp = System.currentTimeMillis();
                callBack_planeView.movePlaneWithSensor(1);
            }
        }
    }

    public void startX() {
        sensorManager.registerListener(sensorEventListenerX, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stopX() {
        sensorManager.unregisterListener(sensorEventListenerX);
    }

}
