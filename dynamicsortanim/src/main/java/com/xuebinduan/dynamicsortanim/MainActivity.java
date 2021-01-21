package com.xuebinduan.dynamicsortanim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 *  核心SortedList.Callback
 *
 *  SortedList内部是会自动去重的。判断元素是否相同的代码在compare回调里由您实现。
 */
public class MainActivity extends AppCompatActivity {

    private DynamicSortAnimRecyclerAdapter adapter;
    private List<Datum> datumList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DynamicSortAnimRecyclerAdapter();
        recyclerView.setAdapter(adapter);

        //运行600*40毫秒 = 24秒，然后停止，执行下面的
        long lastTime = System.currentTimeMillis();
        new Thread(() -> {
            while (true) {
                if (System.currentTimeMillis() - lastTime < 40 * 600) {
                    runOnUiThread(() -> {
                        Datum datum = new Datum((long) (Math.random() * System.currentTimeMillis()), ((int) (Math.random() * 100)) + "");

                        //关键 添加数据
                        adapter.addItem(datum);

                        datumList.add(datum);
                        if (datumList.size() > 20) {
                            int index = (int) (Math.random() * datumList.size());
                            Datum rDatum = datumList.get(index);

                            //关键 删除数据
                            adapter.removeItem(rDatum);

                            datumList.remove(index);
                        }
                    });
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else{
                    runOnUiThread(()->{
                        Toast.makeText(this,"批量删除一些数据",Toast.LENGTH_LONG);

                        //关键 演示批量操作
                        adapter.batchOperateReplaceAllItem(datumList.subList(0,datumList.size()/2));
                    });
                    break;
                }
            }
        }).start();

        //去除名字相同的


    }
}