package com.nava.mijornada;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PartidoView extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_partidos);
    }

    public void onClickAltas(View view) {
        Intent i = new Intent(this, AltasPartido.class);
        startActivity(i);
    }

    public void onClickBajas(View view) {
        Intent i = new Intent(this, BajasPartido.class);
        startActivity(i);
    }

    public void onClickResultados(View view) {
        Intent i = new Intent(this, ResultadosPartido.class);
        startActivity(i);
    }

    public void onClickRegresar(View view) {
        this.finish();
    }
}
