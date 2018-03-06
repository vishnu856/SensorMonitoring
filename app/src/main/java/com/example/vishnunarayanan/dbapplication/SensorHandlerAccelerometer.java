package com.example.vishnunarayanan.dbapplication;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.List;

public class SensorHandlerAccelerometer extends Service implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor mySensor;
    private static int noDataPoints=0;
    //private List<AccelerometerTuple> myDataPoints;

    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager= (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mySensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, mySensor, 100000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this, mySensor);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            noDataPoints++;
            AccelerometerTuple currAct=new AccelerometerTuple(System.currentTimeMillis(), sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
            DBHandler myDB=new DBHandler(this.getApplicationContext(), MainActivity.getTablename());
            myDB.addTuple(currAct);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
