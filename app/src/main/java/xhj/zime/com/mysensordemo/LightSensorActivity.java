package xhj.zime.com.mysensordemo;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class LightSensorActivity extends AppCompatActivity implements SensorEventListener{
    private TextView mLight,mWeather;
    private Sensor mSensor;
    private SensorManager mSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_sensor);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mLight = (TextView) findViewById(R.id.light);
        mWeather = (TextView) findViewById(R.id.weather);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this,mSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int type = sensorEvent.sensor.getType();
        switch (type){
            case Sensor.TYPE_LIGHT:
                float values = sensorEvent.values[0];
                mLight.setText(values+"");
                judgeWeather(values);
                break;
        }
    }

    private void judgeWeather(float value) {
        if(value<=SensorManager.LIGHT_CLOUDY ){
            mWeather.setText("多云");
        }
        if(value>SensorManager.LIGHT_CLOUDY &&value<=SensorManager.LIGHT_SUNRISE ) {
            mWeather.setText("凌晨");
        }
        if(value>SensorManager.LIGHT_SUNRISE   && value<=SensorManager.LIGHT_OVERCAST  ){
            mWeather.setText("阴天");
        }
        if(value>SensorManager.LIGHT_OVERCAST  && value<=SensorManager.LIGHT_SHADE ){
            mWeather.setText("阴影");
        }
        if(value>SensorManager.LIGHT_SHADE  && value<=SensorManager.LIGHT_SUNLIGHT ) {
            mWeather.setText("晴天");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
