package xhj.zime.com.mysensordemo;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AccelerometerActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private TextView mX, mY, mZ,mShakeCount;
    private long lastTime = System.currentTimeMillis();
    float lastX = 0, lastY = 0, lastZ = 0;
    int sum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mX = (TextView) findViewById(R.id.x);
        mY = (TextView) findViewById(R.id.y);
        mZ = (TextView) findViewById(R.id.z);
        mShakeCount = (TextView) findViewById(R.id.shake_count);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int type = sensorEvent.sensor.getType();
        switch (type) {
            case Sensor.TYPE_ACCELEROMETER:
                float[] values = sensorEvent.values;
                mX.setText(values[0] + "");
                mY.setText(values[1] + "");
                mZ.setText(values[2] + "");
                long currtime = System.currentTimeMillis();
                String str = "";
                if (currtime - lastTime > 30) {
                    float dx = Math.abs(values[0] - lastX);
                    float dy = Math.abs(values[1] - lastY);
                    float dz = Math.abs(values[2] - lastZ);
                    float max = getMax(dx, dy, dz);
                    if (max > 3){
                        sum++;
                        str += "\n手机发生了摇晃";
                    }
                }
                str += "\n手机摇晃次数: "+sum;
                mShakeCount.setText(str);
                lastTime = currtime;
                lastX = values[0];
                lastY = values[1];
                lastZ = values[2];
                break;
        }
    }

    private float getMax(float dx, float dy, float dz) {
        float max = Math.max(dx, Math.max(dy, dz));
        return max;
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
