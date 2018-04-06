package com.example.a8fdam08.buscaminas;


import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import static com.example.a8fdam08.buscaminas.MainActivity.iconoTipo;

/**
 * Created by 8fdam10 on 26/03/2018.
 */

public class TableroYDificultad {
    private MainActivity v;
    private GridLayout grid;
    private TableLayout tablaPrincipal;
    private int dificultad = 0; //1 facil 8*8 y 10 minas; 2 normal 12*12 y 30 minas; 3 dificil 16*16 y 60 minas
    private int tamaño;


    public TableroYDificultad() {

    }

    public void modoFacil() {
        this.dificultad = 1;
        crearTabla();
    }

    public void modoNormal() {
        this.dificultad = 2;
        crearTabla();
    }

    public void modoDificil() {
        this.dificultad = 3;
        crearTabla();
    }


    public void crearTabla() {
        //Comprobar dificultad y saber cuantas celdas o botones se crearan
        comprobarDificultad();

        //crear un tableLayout que no da tantos problemas como con el grid a secas.
        tablaPrincipal = new TableLayout(v);
        //con esto se adapta al tamaño del padre el GRIDLAYOUT. AYUDA: https://developer.android.com/reference/android/widget/TableLayout.html
        tablaPrincipal.setStretchAllColumns(true);
        tablaPrincipal.setShrinkAllColumns(true);

        //se pegue a su padre
        tablaPrincipal.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
        tablaPrincipal.setWeightSum(this.tamaño);

        for (int i = 0; i < this.tamaño; i++) {
            TableRow tr = new TableRow(v);
            tr.setGravity(Gravity.CENTER);
            tr.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT, (float) 1.0));

            for (int j = 0; j < this.tamaño; j++) {
                Button botonEscondido = new Button(v);
                botonEscondido.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                botonEscondido.setId(i + 1000);
                botonEscondido.setText(i + "," + j);
                //esconder el texto como control.
                botonEscondido.setTextSize(0);
                botonEscondido.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // como le pusimos texto a cada boton en base a su coordenada, lo cogemos como referencia
                        int x = Integer.parseInt(((Button) view).getText().toString().split(",")[0]);
                        int y = Integer.parseInt(((Button) view).getText().toString().split(",")[1]);

                        //comprobacion de celda virtual segun el boton pulsado en la tabla
                        int valorBoton;
                        if (x >= 0 && x < tamaño && y >= 0 && y < tamaño) {
                            //sacar el valor de ese boton en el tablero virtual
                            valorBoton = v.tablaVirtual[x][y];
                            //((Button) view).setBackgroundResource(R.mipmap.cristal1);
                        } else {
                            valorBoton = -2; // Si no existen las coordenadas devolvemos un valor que lo indique.
                        }

                        //cuando hay mina en la casilla, Fin del juego
                        if (valorBoton == -1) {
                            Button b = (Button) view;

                            b.setEnabled(false);

                            Toast.makeText(v.getBaseContext(), "Has roto un cristal\n     Fin del juego", Toast.LENGTH_LONG).show();
                            switch (iconoTipo) {
                                case 0:
                                    b.setBackgroundResource(R.mipmap.cristal1);
                                    break;
                                case 1:
                                    b.setBackgroundResource(R.mipmap.cristal2);
                                    break;
                                case 2:
                                    b.setBackgroundResource(R.mipmap.cristal3);
                                    break;
                            }

                            //darle la vuelta por ahora
                            b.setScaleY(-1);
                            deshabilitaTablero(view);
                        }

                        //cuando en las 8 casillas adyacentes no tiene cristal
                        if (valorBoton == 0) {
                            Button b = (Button) view;
                            b.setEnabled(false);
                            b.setBackgroundColor(v.getResources().getColor(R.color.naranja));
                            v.limpiarCasillasVaciasCercanas(view, x, y);
                        }

                        //cuando en las 8 casillas adyacentes hay 1 o mas cristales
                        if (valorBoton > 0) {
                            Button b = (Button) view;
                            b.setText(String.valueOf(valorBoton));
                            b.setTextColor(Color.WHITE);
                            b.setBackgroundColor(v.getResources().getColor(R.color.naranja));
                            b.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                            view.setEnabled(false);
                            v.botonesPulsadosSobreLaTabla[x][y] = true;
                        }
                    }
                });
                botonEscondido.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {

                        // como le pusimos texto a cada boton en base a su coordenada, lo cogemos como referencia
                        int x = Integer.parseInt(((Button) view).getText().toString().split(",")[0]);
                        int y = Integer.parseInt(((Button) view).getText().toString().split(",")[1]);

                        //comprobacion de celda virtual segun el boton pulsado en la tabla
                        int valorBoton;
                        if (x >= 0 && x < tamaño && y >= 0 && y < tamaño) {
                            //sacar el valor de ese boton en el tablero virtual
                            valorBoton = v.tablaVirtual[x][y];
                            //((Button) view).setBackgroundResource(R.mipmap.cristal1);
                        } else {
                            valorBoton = -2; // Si no existen las coordenadas devolvemos un valor que lo indique.
                        }


                        //cuando hay mina en la casilla, Fin del juego
                        if (valorBoton == -1) {
                            Button b = (Button) view;
                            b.setEnabled(false);
                            b.setBackgroundResource(R.mipmap.cristal1);
                            Toast.makeText(v.getBaseContext(), "Has encontrado un cristal, quedan " + "x", Toast.LENGTH_LONG).show();


                        } else {
                            Toast.makeText(v.getBaseContext(), "Aqui no hay ningun cristal\n          Fin del juego", Toast.LENGTH_LONG).show();
                            deshabilitaTablero(view);
                        }
                        return true;
                    }
                });

                //añadir boton a la vista
                tr.addView(botonEscondido);
            }
            tablaPrincipal.addView(tr);
        }
        //borrar lo que exista anteriormente
        grid.removeAllViews();
        grid.addView(tablaPrincipal);

        //crear la tabla virtual del main
        v.crearTabla();
        v.ponerCristales();
        v.hayCristalesCerca();
        // if (!jugando) deshabilitaTablero(tableLayout);
    }


    private void deshabilitaTablero(View view) {
        // Recorremos la matriz de botones deshabilitando todos.
        for (int i = 0; i < tablaPrincipal.getChildCount(); i++) {
            TableRow tr = (TableRow) tablaPrincipal.getChildAt(i);
            for (int j = 0; j < tablaPrincipal.getChildCount(); j++) {
                Button b = (Button) tr.getChildAt(j);
                b.setEnabled(false);
            }
        }
    }

    private void comprobarDificultad() {
        switch (dificultad) {
            case 1:
                tamaño = 8;
                break;
            case 2:
                tamaño = 12;
                break;
            case 3:
                tamaño = 16;
                break;
            default:
                break;
        }
    }

    public GridLayout getGrid() {
        return grid;
    }

    public void setGrid(GridLayout grid) {
        this.grid = grid;
    }

    public TableLayout getTablaPrincipal() {
        return tablaPrincipal;
    }

    public void setTablaPrincipal(TableLayout tablaPrincipal) {
        this.tablaPrincipal = tablaPrincipal;
    }

    public Integer getDificultad() {
        return dificultad;
    }

    public void setDificultad(Integer dificultad) {
        this.dificultad = dificultad;
    }

    public MainActivity getV() {
        return v;
    }

    public void setV(MainActivity v) {
        this.v = v;
    }

    public int getTamaño() {
        return tamaño;
    }

    public void setTamaño(int tamaño) {
        this.tamaño = tamaño;
    }
}
