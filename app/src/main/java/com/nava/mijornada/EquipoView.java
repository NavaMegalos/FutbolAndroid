package com.nava.mijornada;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EquipoView extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_equipos);
    }

    public void onClickAltas(View view) {
        Intent i = new Intent(EquipoView.this, AltasEquipo.class);
        startActivity(i);
    }
    public void onClickBajas(View view) {
        Intent i = new Intent(EquipoView.this, BajasEquipo.class);
        startActivity(i);
    }

    public void onClickCambios(View view) {
        Intent i = new Intent(EquipoView.this, CambiosEquipo.class);
        startActivity(i);
    }
    public void onClickRegresar(View view) {
        this.finish();
    }

}
