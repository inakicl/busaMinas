package com.example.a8fdam08.buscaminas;

import android.view.Gravity;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

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
                //tiledBoton.setOnClickListener(this);
                // tiledBoton.setOnLongClickListener(this);
                tr.addView(botonEscondido);
            }
            tablaPrincipal.addView(tr);
        }
        //borrar lo que exista anteriormente
        grid.removeAllViews();
        grid.addView(tablaPrincipal);

        // if (!jugando) deshabilitaTablero(tableLayout);
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
}
