package xhj.zime.com.mysensordemo;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class GetAllSensorActivity extends AppCompatActivity {
    private SensorManager mSensorManager;
    private ListView mSensorList;
    private List<String> mList = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;
    private List<Sensor> sensorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_sensor);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorList = (ListView) findViewById(R.id.sensor_list_view);
        mAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,mList);
        mSensorList.setAdapter(mAdapter);
        initData();
        mSensorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                StringBuffer sb = new StringBuffer();
                sb.append("名称:"+sensorList.get(i).getName()+"\n");
                sb.append("最大范围:"+sensorList.get(i).getMaximumRange()+"\n");
                sb.append("最小延迟:"+sensorList.get(i).getMinDelay()+"\n");
                sb.append("功率:"+sensorList.get(i).getPower()+"\n");
                sb.append("分辨率:"+sensorList.get(i).getResolution()+"\n");
                sb.append("类型:"+sensorList.get(i).getType()+"\n");
                sb.append("供应商:"+sensorList.get(i).getVendor()+"\n");
                sb.append("版本:"+sensorList.get(i).getVersion());
                new AlertDialog.Builder(GetAllSensorActivity.this)
                        .setMessage(sb.toString())
                        .setPositiveButton(android.R.string.ok,null)
                        .create()
                        .show();
            }
        });
    }

    private void initData() {
        sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor: sensorList){
            mList.add(sensor.getName());
        }
        mAdapter.notifyDataSetChanged();
    }
}
