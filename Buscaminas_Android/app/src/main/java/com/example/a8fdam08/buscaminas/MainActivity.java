package com.example.a8fdam08.buscaminas;

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

public class MainActivity extends AppCompatActivity {

    GridLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main = (GridLayout) findViewById(R.id.tablero);
        //modoFacil();
        rellenaBotones(18);

    }

    TableLayout tableLayout;

    public void rellenaBotones(int dificultad) {
        tableLayout = new TableLayout(this);
        //con esto se adapta al tamaño de la pantalla
        tableLayout.setStretchAllColumns(true);
        tableLayout.setShrinkAllColumns(true);

        //se pegue a su padre
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
        tableLayout.setWeightSum(dificultad);

        for (int i = 0; i < dificultad; i++) {
            TableRow tr = new TableRow(this);
            tr.setGravity(Gravity.CENTER);
            tr.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT, (float) 1.0));

            for (int j = 0; j < dificultad; j++) {
                Button botonEscondido = new Button(this);
                botonEscondido.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                botonEscondido.setId(i + 1000);

                botonEscondido.setText(i + "," + j);
                //esconder el texto como control.
                botonEscondido.setTextSize(0);
                //tiledBoton.setOnClickListener(this);
                // tiledBoton.setOnLongClickListener(this);
                tr.addView(botonEscondido);
            }
            tableLayout.addView(tr);
        }
        // main.removeAllViews();
        main.addView(tableLayout);

        // if (!jugando) deshabilitaTablero(tableLayout);
    }


   /* public void modoFacil() {
        GridLayout g = (GridLayout) findViewById(R.id.tablero);

        Button b;
        LinearLayout linea = new LinearLayout(this);

        for (int j = 0; j < 8 * 8; j++) {
            linea.setGravity(Gravity.CENTER);
            linea.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, (float) 1.0));
            for (int i = 0; i < 8 * 8; i++) {

                b = new Button(this);

                b.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                b.setGravity(Gravity.CENTER);
                b.setText("");
                b.setId(i + 1000);
                linea.addView(b);
            }
            main.addView(linea);
        }
    }*/

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
        switch (id) {
            case R.id.uno:
                Toast.makeText(getApplicationContext(), "Se ha seleccionado Árbol Genialógico", Toast.LENGTH_LONG).show();
                return true;
            case R.id.dos:
                Toast.makeText(getApplicationContext(), "Se ha pulsado la opción de \"ajustes\"", Toast.LENGTH_LONG).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //Menú ActionMode
    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.layout_menu, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Se llama a este método cuando se ha pulsado en la lista de los lannisters
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            switch (item.getItemId()) {
                case R.id.uno:
                    //hay que crear un Aniquilar() para recorrer todos los elementos seleccionado (checked) en la listView
                    Toast.makeText(getApplicationContext(), "Hemos aniquilado a algún Lannister", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.dos:
                    Toast.makeText(getApplicationContext(), "Hemos encerrado a algún Lannister", Toast.LENGTH_LONG).show();
                    return true;
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionModeCallback = null;
        }
    };
}
