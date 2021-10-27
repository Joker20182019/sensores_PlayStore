package com.example.felipe_canio;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView txtsensores;
    private TextView txtgravedad;
    private TextView txtacelerador;

    private SensorManager sensorManager;
    private List<Sensor> sensores;

    private Sensor sensorGRAVE, sensorACE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtsensores = (TextView) findViewById(R.id.txt_sensor);
        txtgravedad = (TextView) findViewById(R.id.txt_gravedad);
        txtacelerador = (TextView) findViewById(R.id.txt_acelerar);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensores = sensorManager.getSensorList(Sensor.TYPE_ALL);


        int contador = 1;
        for(Iterator<Sensor> tl = sensores.iterator(); tl.hasNext(); contador++){
            Sensor sensor = tl.next();
            txtsensores.append(String.format("%d: %s, %d, %s\n", contador,sensor.getName(), sensor.getType(), sensor.getVendor()));
        }

        sensorGRAVE = (Sensor) sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sensorManager.registerListener(this, sensorGRAVE, SensorManager.SENSOR_DELAY_NORMAL);

        sensorACE = (Sensor) sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensorACE, SensorManager.SENSOR_DELAY_NORMAL);
    }



    private void adormir(){
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void ACEPTAR(View v){
        Toast.makeText(this, "Ingresando", Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<=10; i++){
                    adormir();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "encontrado", Toast.LENGTH_SHORT).show();
                    }
                });

            }

        }).start();
        Toast.makeText(this, "Espere por favor", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch (sensorEvent.sensor.getType()) {
            case Sensor.TYPE_GRAVITY:
                txtgravedad.setText(String.format("%.0f", sensorEvent.values[0]));
                break;

            case Sensor.TYPE_ACCELEROMETER:
                txtacelerador.setText(String.format("%.0f",sensorEvent.values[0]));
                break;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}