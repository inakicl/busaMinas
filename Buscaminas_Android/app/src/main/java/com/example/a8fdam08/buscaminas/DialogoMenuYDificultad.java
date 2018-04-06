package com.example.a8fdam08.buscaminas;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by 8fdam10 on 26/03/2018.
 */

public class DialogoMenuYDificultad extends DialogFragment {

    TableroYDificultad tabla;
    RespuestaDialogoSexo respuesta;
    Integer selectorDeDificultad = 0; //iniciar para evital nullexception
    String[] dificultad = {"Fácil", "Normal", "Difícil"};
    //el modo que esta actualmente puesto
    int modoAlEntrarAlDialogo = 0;

    //Este método es llamado al hacer el show() de la clase DialogFragmet()
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //el modo que esta actualmente puesto
        modoAlEntrarAlDialogo = tabla.getDificultad() - 1;

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Elige la dificultad:").setSingleChoiceItems(dificultad, modoAlEntrarAlDialogo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //cada vez que se selecciona una opcion cambia el int i
                System.out.println("xdDDDDD  " + i);
                switch (i) {
                    case 0:
                        selectorDeDificultad = 1;
                        break;
                    case 1:
                        selectorDeDificultad = 2;
                        break;
                    case 2:
                        selectorDeDificultad = 3;
                        break;
                    default:
                        selectorDeDificultad = 0;
                        break;
                }

            }
        }).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Aceptar y se cambia la dificultad y el tablero con ello
                switch (selectorDeDificultad) {
                    case 1:
                        tabla.modoFacil();
                        break;
                    case 2:
                        tabla.modoNormal();
                        break;
                    case 3:
                        tabla.modoDificil();
                        break;
                    default:

                        break;
                }

            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //nada, se cancela y ya
            }
        });
        // Crear el AlertDialog y devolverlo
        return builder.create();
    }

    //Interfaz para la comunicación entre la Actividad y el Fragmento
    public interface RespuestaDialogoSexo {
        public void onRespuesta(String s);
    }

    //Se invoca cuando el fragmento se añade a la actividad
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        respuesta = (RespuestaDialogoSexo) activity;
    }

    public TableroYDificultad getTabla() {
        return tabla;
    }

    public void setTabla(TableroYDificultad tabla) {
        this.tabla = tabla;
    }
}
