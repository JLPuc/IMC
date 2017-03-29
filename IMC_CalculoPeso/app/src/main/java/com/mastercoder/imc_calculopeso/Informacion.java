package com.mastercoder.imc_calculopeso;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Informacion extends AppCompatActivity {

    Button btnCalcular;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        //Redirige a la ventana de Calculo de IMC
        Button btnCalcular = (Button) findViewById(R.id.btnCalculo);
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CalculoIMC.class);
                startActivity(i);
            }
        });
    }
}
