package com.example.a8fdam08.buscaminas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements DialogoMenuYDificultad.RespuestaDialogoSexo {

    TableroYDificultad tabla;
    public static int iconoTipo = 0; //0 el icono <<Rosa>>, 1 el icono <<Azul>>, 2 el icono <<Verde>>

    int[][] tablaVirtual;
    boolean[][] botonesPulsadosSobreLaTabla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //crear el juego por defecto, facil e icono rosa.
        //crear el juego fisicamente y virtualmente
        tabla = new TableroYDificultad();
        tabla.setV(this);
        tabla.setGrid((GridLayout) findViewById(R.id.tablero));
        tabla.modoFacil();


    }

    //tabla virtual
    public void crearTabla() {
        tablaVirtual = new int[tabla.getTamaño()][tabla.getTamaño()];
        botonesPulsadosSobreLaTabla = new boolean[tabla.getTamaño()][tabla.getTamaño()];

        for (int i = 0; i < tabla.getTamaño(); i++) {
            for (int j = 0; j < tabla.getTamaño(); j++) {
                tablaVirtual[i][j] = 0;
                botonesPulsadosSobreLaTabla[i][j] = false;
            }
        }

    }

    public void ponerCristales() {
        int actualMinas = 0;
        int totalMinas;
        switch (tabla.getDificultad()) {
            case 1:
                totalMinas = 10;
                break;
            case 2:
                totalMinas = 30;
                break;
            case 3:
                totalMinas = 60;
                break;
            default:
                totalMinas = -1;
                break;
        }

        Random r = new Random();

        while (actualMinas < totalMinas) {
            //poner minas de forma aleatoria por el tablero virtual
            int i = r.nextInt(tabla.getTamaño());
            int j = r.nextInt(tabla.getTamaño());
            //comprobar que no se repita
            if (tablaVirtual[i][j] == 0) {
                tablaVirtual[i][j] = -1;
                actualMinas++;
            }
        }
    }

    public void hayCristalesCerca() {
        for (int x = 0; x < tabla.getTamaño(); x++) {
            for (int y = 0; y < tabla.getTamaño(); y++) {
                if (tablaVirtual[x][y] != -1) {
                    int contador = 0;
                    if ((x - 1 >= 0) && (y - 1 >= 0) && tablaVirtual[x - 1][y - 1] == -1)
                        contador++;
                    if ((x - 1 >= 0) && tablaVirtual[x - 1][y] == -1) contador++;
                    if ((x - 1 >= 0) && (y + 1 < tabla.getTamaño()) && tablaVirtual[x - 1][y + 1] == -1)
                        contador++;
                    if ((y - 1 >= 0) && tablaVirtual[x][y - 1] == -1) contador++;
                    if ((y + 1 < tabla.getTamaño()) && tablaVirtual[x][y + 1] == -1) contador++;
                    if ((x + 1 < tabla.getTamaño()) && (y - 1 >= 0) && tablaVirtual[x + 1][y - 1] == -1)
                        contador++;
                    if ((x + 1 < tabla.getTamaño()) && tablaVirtual[x + 1][y] == -1) contador++;
                    if ((x + 1 < tabla.getTamaño()) && (y + 1 < tabla.getTamaño()) && tablaVirtual[x + 1][y + 1] == -1)
                        contador++;
                    tablaVirtual[x][y] = contador;
                }
            }
        }
    }



    public void limpiarCasillasVaciasCercanas(View view, int x, int y) {
        // Recorremos los botones adyacentes y si también están a cero los despejamos
        for (int xt = -1; xt <= 1; xt++) {
            for (int yt = -1; yt <= 1; yt++) {
                if (xt != yt) {
                    if (compruebaCelda(x + xt, y + yt) == 0 && botonesPulsadosSobreLaTabla[x + xt][y + yt] != true) {
                        Button b = (Button) traerBoton(x + xt, y + yt);
                        b.setBackgroundColor(getResources().getColor(R.color.naranja));
                        b.setEnabled(false);
                        botonesPulsadosSobreLaTabla[x + xt][y + yt] = true;
                        String[] coordenadas = b.getText().toString().split(",");
                        limpiarCasillasVaciasCercanas(b, Integer.parseInt(coordenadas[0]), Integer.parseInt(coordenadas[1]));
                    }
                }
            }
        }

    }

    // Recorremos la matriz de botones hasta encontrar una coincidencia con las coordenadas que queremos
    public View traerBoton(int x, int y) {
        Button b = null;

        for (int i = 0; i < tabla.getTablaPrincipal().getChildCount(); i++) {
            TableRow tr = (TableRow) tabla.getTablaPrincipal().getChildAt(i);
            for (int j = 0; j < tr.getChildCount(); j++) {
                b = (Button) tr.getChildAt(j);
                if (b.getText().toString().equals(x + "," + y)) {
                    return b;
                }
            }
        }
        return null;
    }

    public int compruebaCelda(int x, int y) {
        if (x >= 0 && x < tabla.getTamaño() && y >= 0 && y < tabla.getTamaño()) {
            //sacar el valor de ese boton en el tablero virtual
            return tablaVirtual[x][y];
        } else {
            return -2; // Si no existen las coordenadas devolvemos un valor que lo indique.
        }
    }

    //se crea el menu inflándolo del layout personalizado
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.layout_menu, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater m = getMenuInflater();
        m.inflate(R.menu.layout_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    //Se llama a este método cuando se ha seleccionado una opción del menú de opciones
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        DialogoMenuYDificultad ds = new DialogoMenuYDificultad();
        InstruccionesDialogo insD = new InstruccionesDialogo();
        CambiarIconoDialogo cid = new CambiarIconoDialogo();
        switch (id) {
            case R.id.instucciones:
                insD.show(getFragmentManager(), "Mi diálogo");
                return true;
            case R.id.dificultad:
                ds.setTabla(tabla);
                ds.show(getFragmentManager(), "Mi diálogo");
                return true;
            case R.id.cambiarIcono:
                cid.show(getFragmentManager(), "cambiar icono");
                break;
            case R.id.nuevaPartida:
                tabla.crearTabla();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    //respuesta del selector de dificultad
    @Override
    public void onRespuesta(String s) {

    }
}
