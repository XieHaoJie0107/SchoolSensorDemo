package xhj.zime.com.mysensordemo;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class OrientationActivity extends AppCompatActivity implements SensorEventListener {
    private TextView mOrientation;
    // 定义显示指南针的图片
    ImageView znzImage;
    // 记录指南针图片转过的角度
    float currentDegree = 0f;
    // 定义Sensor管理器
    SensorManager mSensorManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orientation);
        // 获取界面中显示指南针的图片
        znzImage = (ImageView) findViewById(R.id.znzImage);
        // 获取传感器管理服务
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mOrientation = (TextView) findViewById(R.id.orientation);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 为系统的方向传感器注册监听器
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        // 取消注册
        mSensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    protected void onStop() {
        // 取消注册
        mSensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // 获取触发event的传感器类型
        int sensorType = event.sensor.getType();
        switch (sensorType) {
            case Sensor.TYPE_ORIENTATION:
                float[] values = event.values;
                String result = "当前方向传感器的值为: \n"
                        +"绕z轴转过的角度: "+values[0]+"\n"
                        +"绕x轴转过的角度: "+values[1]+"\n"
                        +"绕y轴转过的角度: "+values[2];
                mOrientation.setText(result);

                // 获取绕Z轴转过的角度
                float degree = event.values[0];
                // 创建旋转动画（反向转过degree度）
                RotateAnimation ra = new RotateAnimation(currentDegree,
                        -degree, Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                // 设置动画的持续时间
                ra.setDuration(200);
                // 运行动画
                znzImage.startAnimation(ra);
                currentDegree = -degree;
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}

