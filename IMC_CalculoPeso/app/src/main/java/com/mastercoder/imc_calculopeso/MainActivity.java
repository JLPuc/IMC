package com.mastercoder.imc_calculopeso;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnSalir;
    private Button btnQueEs;
    private Button btnCalcularIMC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Evento para ir a la descripción detallada del programa
        btnQueEs = (Button) findViewById(R.id.btnQueEs);
        btnQueEs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Informacion.class);
                startActivity(i);
            }
        });
        //Evento para calcular
        btnCalcularIMC = (Button) findViewById(R.id.btnCalcularIMC);
        btnCalcularIMC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CalculoIMC.class);
                startActivity(i);
            }
        });
        //Evento para salir del sistemas
        btnSalir = (Button) findViewById(R.id.btnSalir);
        btnSalir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
    }
}