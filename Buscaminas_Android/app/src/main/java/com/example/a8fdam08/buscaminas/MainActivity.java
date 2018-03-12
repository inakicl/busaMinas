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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* GridLayout gridLayout = new GridLayout(getApplicationContext());
        int total = 16;
        int column =  8;
        int row = 8;
        gridLayout.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        gridLayout.setColumnCount(column);
        gridLayout.setRowCount(row );
        Button b;
        for(int i =0, c = 0, r = 0; i < total; i++, c++)
        {
            if(c == column)
            {
                c = 0;
                r++;
            }
            b = new Button(getApplicationContext());
            b.setText("te");
            gridLayout.addView(b, i);
            b.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            GridLayout.LayoutParams param =new GridLayout.LayoutParams();
            param.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            param.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            param.rightMargin = 5;
            param.topMargin = 5;
            param.setGravity(Gravity.CENTER);
            param.columnSpec = GridLayout.spec(c);
            param.rowSpec = GridLayout.spec(r);
            b.setLayoutParams (param);
        }*/
        modoFacil();

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void modoFacil() {
        GridLayout g = (GridLayout) findViewById(R.id.tablero);

        Button b;
        for (int i = 0; i < 8 * 8; i++) {

            b = new Button(this);

            b.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

            b.setMaxWidth(10);
            b.setMaxHeight(10);
            b.setText("");
            b.setId(View.generateViewId());
            g.addView(b, i);
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
