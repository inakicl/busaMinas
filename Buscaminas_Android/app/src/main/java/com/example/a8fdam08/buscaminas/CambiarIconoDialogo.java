package com.example.a8fdam08.buscaminas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.a8fdam08.buscaminas.MainActivity.iconoTipo;


/**
 * Created by ikitess on 28/03/2018.
 */

public class CambiarIconoDialogo extends DialogFragment {


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    /*
        String[] coloresNombre = getResources().getStringArray(R.array.color);
        int imagenes[] = getResources().getIntArray(R.array.iconos);
    */
    String[] coloresNombre = {"Rosa", "Azul", "Verde"};
    int imagenes[] = {R.mipmap.cristal1, R.mipmap.cristal2, R.mipmap.cristal3};

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View vistaPrincipal = inflater.inflate(R.layout.spinner, null);

        builder.setView(vistaPrincipal)
                .setPositiveButton("Vale", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // se coja el icono seleccionado.
                    }
                });

        AdaptadorPersonalizado seleccionarAdaptador;
        seleccionarAdaptador = new AdaptadorPersonalizado(this.getActivity(), R.layout.support_simple_spinner_dropdown_item, coloresNombre);
        Spinner spinnerSelecPersonaje = (Spinner) vistaPrincipal.findViewById(R.id.spinner);
        spinnerSelecPersonaje.setAdapter(seleccionarAdaptador);
        spinnerSelecPersonaje.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                // Showing selected spinner item
                Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

                switch (i/*Posicion del item*/) {
                    case 0:
                        iconoTipo = 0;
                        break;
                    case 1:
                        iconoTipo = 1;
                        break;
                    case 2:
                        iconoTipo = 2;
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //se pone el icono que esta actualmente
        spinnerSelecPersonaje.setSelection(iconoTipo);

        return builder.create();


    }


    public class AdaptadorPersonalizado extends ArrayAdapter {
        public AdaptadorPersonalizado(Context ctx, int txtViewResourceId, String[] objects) {
            super(ctx, txtViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return crearFilaPersonalizada(position, convertView, parent);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return crearFilaPersonalizada(position, convertView, parent);
        }

        public View crearFilaPersonalizada(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View miFila = inflater.inflate(R.layout.spinner_celda, parent, false);

            TextView nombre = (TextView) miFila.findViewById(R.id.color);
            nombre.setText(coloresNombre[position]);

            ImageView imagen = (ImageView) miFila.findViewById(R.id.cristal);
            imagen.setImageResource(imagenes[position]);
            return miFila;
        }

    }
}
