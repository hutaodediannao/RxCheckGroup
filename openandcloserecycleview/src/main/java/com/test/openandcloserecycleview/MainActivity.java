package com.test.openandcloserecycleview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Model> modelList;
    private ModelAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        modelList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Model m = new Model("model" + i, R.mipmap.ic_launcher, "content" + i, true);
            modelList.add(m);
        }
        adapter = new ModelAdapter(modelList, this);
        recyclerView.setAdapter(adapter);
        adapter.setCliclkListener(new ModelAdapter.CliclkListener() {
            @Override
            public void clickCheckBox(int position, boolean isOpen) {
                Toast.makeText(MainActivity.this, ""+position, Toast.LENGTH_SHORT).show();
            }
        });


    }
}
