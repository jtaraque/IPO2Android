package com.empresa.alumnado;

import android.view.MenuItem;

public interface OnItemSelectedListener {
    void onAlumnoSeleccionado(int posicion);
    void onMenuContextualAlumno(int posicion, MenuItem menu);

}
