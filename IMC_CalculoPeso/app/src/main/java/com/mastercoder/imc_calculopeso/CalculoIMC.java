package com.mastercoder.imc_calculopeso;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mastercoder.imc_calculopeso.Clases.Calculadora;

public class CalculoIMC extends Activity {

    public static TextView resultados, datos;
    public int IMC = 0;
    public Button calcular;
    public ImageView figura;
    public EditText pesoUser, alturaUser, edadUser;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imc);
        //Asociamos los componentes de la vista
        resultados = (TextView) findViewById(R.id.txtResultado_calculos);
        datos = (TextView) findViewById(R.id.txtdDtos_usuario);
        calcular = (Button) findViewById(R.id.btnCalculadora);
        figura = (ImageView) findViewById(R.id.imgSilueta);
        pesoUser = (EditText) findViewById(R.id.txtPeso_usuario);
        alturaUser = (EditText) findViewById(R.id.txtAltura_usuario);
        edadUser = (EditText) findViewById(R.id.txtEdad_usuario);
        //Aviso de que la app solo es fiable con edades superiores a 19 a�os
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.importante);
        builder.setMessage(R.string.mensaje);
        builder.setPositiveButton("OK", null);
        builder.create();
        builder.show();
        //Ssociamos un anuncio broadcast a nuestro receptor de anuncios
        IntentFilter filtro = new IntentFilter(ReceptorCalculadora.ACTION_RESP);
        filtro.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(new ReceptorCalculadora(), filtro);
        //Instanciamos el bóton para ejecutar la acción
        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ponemos a 0 los datos
                datos.setText("");
                resultados.setText("");
                //comprobamos que ha escrito algo
                String peso_texto, altura_texto, edad_texto;
                peso_texto = pesoUser.getText().toString();
                altura_texto = alturaUser.getText().toString();
                edad_texto = edadUser.getText().toString();
                //Si no se ha rellenado alguno saldr� de la funcion
                if (peso_texto.trim().equals("") || altura_texto.trim().equals("") || edad_texto.trim().equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT).show();
                    return;
                }
                //Guardamos en variables los datos introducidos por el usuario
                int edad = Integer.parseInt(edadUser.getText().toString());
                int altura = Integer.parseInt(alturaUser.getText().toString());
                //Convertimos la altura a float
                float altura_m = (float) (altura / 100.00);
                float peso = Float.parseFloat(pesoUser.getText().toString());
                //Escondemos el teclado
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(calcular.getWindowToken(), 0);

                //Creamos una nueva intenci�n con nuestro contexto y la clase Calculadora
                Intent intent = new Intent(CalculoIMC.this, Calculadora.class);
                //incluimos los datos que enviaremos al servicio
                intent.putExtra("edad_usu", edad);
                intent.putExtra("altura_usu", altura_m);
                intent.putExtra("peso_usu", peso);
                //Arrancamos servicio
                startService(intent);

                //Borramos los datos introducidos
                limpiarCajasTexto();
                datos.append("Edad: " + edad + " años\n" + "Peso: " + peso + " kg.\n" + "Altura: " + altura_m + " m.\n");
            }
        });
    }
    private  void  limpiarCajasTexto()
    {
        pesoUser.setText("");
        alturaUser.setText("");
        edadUser.setText("");
    }
    //Una vez que el servicio ha concluido su trabajo queremos que avise a esta actividad
    // y le devolva el valor resultante. Lo haremos medio de un anuncio broadcast.
    public class ReceptorCalculadora extends BroadcastReceiver {
        public static final String ACTION_RESP = "com.mastercoder.imc_calculopeso.action.RESPUESTA_OPERACION";
        @Override
        public void onReceive(Context context, Intent intent) {
            //recibimos el resultado del c�lculo
            int imc_usuario = intent.getIntExtra("imc", 0);
            int resultado_usuario = intent.getIntExtra("resultado", 3);
            if (resultado_usuario == 0) {
                resultados.append("Su índice de masa corporal es: " + imc_usuario + ", y está dentro de los límites normales");
                figura.setVisibility(View.VISIBLE);
                figura.setImageResource(R.drawable.normal);
            } else if (resultado_usuario == 1) {
                resultados.append("Su índice de masa corporal es: " + imc_usuario + ", está por debajo de los límites normales. Necesita ganar peso");
                //activar boton que lo lleve a dietas hipercal�ricas
                figura.setVisibility(View.VISIBLE);
                figura.setImageResource(R.drawable.infrapeso);
            } else if (resultado_usuario == 2) {
                resultados.append("Su índice de masa corporal es: " + imc_usuario + ", está por encima de los límites normales. Necesita perder peso");
                //activar boton con dietas hipocal�ricas
                figura.setVisibility(View.VISIBLE);
                figura.setImageResource(R.drawable.sobrepeso);
            }
        }
    }
}