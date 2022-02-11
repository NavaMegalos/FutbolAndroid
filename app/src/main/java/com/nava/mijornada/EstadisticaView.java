package com.nava.mijornada;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import java.util.ArrayList;

public class EstadisticaView extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<TresItemsView> arrayList = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estadisticas);
        mRecyclerView = findViewById(R.id.item_list_estadisticas);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        arrayList.add(new TresItemsView("La Loma", "5", "15"));
        arrayList.add(new TresItemsView("El Atletico Alba", "2", "3"));
        arrayList.add(new TresItemsView("El Atletico Alba", "2", "3"));
        arrayList.add(new TresItemsView("La Torres", "5", "0"));
        arrayList.add(new TresItemsView("La Torres", "5", "0"));
        arrayList.add(new TresItemsView("La Torres", "5", "0"));
        mAdapter = new AdaptadorTresItems(arrayList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void onClickRegresar(View view) {
        this.finish();
    }
}
