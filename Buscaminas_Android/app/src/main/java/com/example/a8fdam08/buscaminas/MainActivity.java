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

public class MainActivity extends AppCompatActivity implements DialogoMenuYDificultad.RespuestaDialogoSexo {
    TableroYDificultad tabla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabla = new TableroYDificultad();
        tabla.setV(this);
        tabla.setGrid((GridLayout) findViewById(R.id.tablero));
        tabla.modoFacil();


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
        switch (id) {
            case R.id.uno:
                //Toast.makeText(getApplicationContext(), "Se ha seleccionado Árbol Genialógico", Toast.LENGTH_LONG).show();
                insD.show(getFragmentManager(), "Mi diálogo");
                return true;
            case R.id.dos:
                ds.setTabla(tabla);
                ds.show(getFragmentManager(), "Mi diálogo");
               /* Toast.makeText(getApplicationContext(), "Se ha pulsado la opción de \"ajustes\"", Toast.LENGTH_LONG).show();*/
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

    //respuesta del selector de dificultad
    @Override
    public void onRespuesta(String s) {

    }
}
