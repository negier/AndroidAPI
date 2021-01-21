package com.xuebinduan.dynamicsortanim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 *  核心SortedList.Callback
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

        new Thread(() -> {
            while (true) {
                runOnUiThread(() -> {
                    Datum datum = new Datum((long) (Math.random() * System.currentTimeMillis()), ((int) (Math.random() * 100)) + "");

                    //关键
                    adapter.addItem(datum);

                    datumList.add(datum);
                    if (datumList.size()>20){
                        int index = (int) (Math.random() * datumList.size());
                        Datum rDatum = datumList.get(index);

                        //关键
                        adapter.removeItem(rDatum);

                        datumList.remove(index);
                    }
                });
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}