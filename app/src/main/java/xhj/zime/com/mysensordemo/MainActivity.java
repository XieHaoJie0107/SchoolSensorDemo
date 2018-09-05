package xhj.zime.com.mysensordemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Class<?>[] mclazzs = new Class[]{GetAllSensorActivity.class,LightSensorActivity.class,AccelerometerActivity.class,OrientationActivity.class};
    private String[] names = new String[]{"第一课 获取手机所有的传感器信息","第四课 光照传感器","第五课 加速度传感器","第六课 方向传感器"};
    private List<String> mList = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mListView.setAdapter(mAdapter);
        initData();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,mclazzs[i]);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        for (int i = 0; i < names.length; i++) {
            mList.add(names[i]);
        }
        mAdapter.notifyDataSetChanged();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.listView);
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mList);
    }
}
