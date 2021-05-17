package com.empresa.alumnado;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Alumno> alumnos;
    private RecyclerView lstAlumnos;
    private AdaptadorLista adaptador;
    private TextView lblSeleccionado;

    private int alumnoSeleccionado;


    private ConectorBD conectorBD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        lstAlumnos = findViewById(R.id.lstAlumnos);
        setSupportActionBar(toolbar);
        Intent i = new Intent(this, DetallesAlumno.class);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LogCat", "Pulsó la opción del menú contextual Ver Detalles");
                i.putExtra("dni", "");
                i.putExtra("nombre", "");
                i.putExtra("email", "");
                i.putExtra("curso", 0);
                i.putExtra("calificacion", 0);
                startActivityForResult(i, 1235);
            }
        });

        //Obtener una referencia a la lista gráfica
        lstAlumnos = findViewById(R.id.lstAlumnos);
        //Obtener una referencia a la etiqueta en la que se mostrará el ítem seleccionado
        lblSeleccionado = findViewById(R.id.lblSeleccionado);
        //Crear la lista de alumnos y añadir algunos datos de prueba
        alumnos = new ArrayList<Alumno>();
        //Método que rellena el array con datos de prueba

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        //RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,2);
        lstAlumnos.setLayoutManager(mLayoutManager);
        adaptador = new AdaptadorLista(alumnos);
        lstAlumnos.setAdapter(adaptador);
        adaptador.setItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onAlumnoSeleccionado(int posicion) {
                lblSeleccionado.setText("Alumno seleccionado: " + alumnos.get(posicion).getNombre());
            }

            @Override
            public void onMenuContextualAlumno(int posicion, MenuItem menu) {
                switch (menu.getItemId()) {
                    case R.id.borrarAlumno:
                        Log.d("LogCat", "Pulsó la opción de menú contextual Borrar alumno");

                        conectorBD = new ConectorBD(getApplicationContext());
                        conectorBD.abrir();
                        conectorBD.eliminarAlumno(alumnos.get(posicion).getDni());
                        conectorBD.cerrar();
                        Toast.makeText(getBaseContext(), "¡Se ha eliminado el alumno de la BD local!", Toast.LENGTH_SHORT).show();
                        alumnos.remove(alumnos.get(posicion));
                        adaptador.notifyDataSetChanged();
                        break;
                    case R.id.verDetalles:
                        Log.d("LogCat", "Pulsó la opción del menú contextual Ver Detalles");
                        i.putExtra("dni", alumnos.get(posicion).getDni());
                        i.putExtra("nombre", alumnos.get(posicion).getNombre());
                        i.putExtra("email", alumnos.get(posicion).getEmail());
                        i.putExtra("curso", alumnos.get(posicion).getCurso());
                        i.putExtra("calificacion", alumnos.get(posicion).getCalifacion());
                        alumnoSeleccionado = posicion;
                        startActivityForResult(i, 1234);
                        break;
                }
            }
        });
        lstAlumnos.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
//        rellenarDatosPrueba();
        cargarDatos();

    }


//    public void rellenarDatosPrueba() {
//        alumnos.add(new Alumno("00000001A", "María Rodríguez", "mariarodriguez@correo.com", 0, 8.5));
//        alumnos.add(new Alumno("00000001G", "José Pérez", "joseperez@correo.com", 1, 1));
//        alumnos.add(new Alumno("00000002E", "José Ruíz", "joseruiz@correo.com", 2, 4.9));
//        alumnos.add(new Alumno("00000003Y", "Carmen López", "carmenlopez@correo.com", 3, 0.5));
//        alumnos.add(new Alumno("00000004I", "María Pérez", "mariapezar@correo.com", 4, 10));
//        alumnos.add(new Alumno("00000005F", "José García", "josegarcia@correo.com", 1, 7.1));
//        alumnos.add(new Alumno("00000006E", "José Ruíz", "joseruiz@correo.com", 2, 3.2));
//        alumnos.add(new Alumno("00000007T", "Carmen Ruíz", "carmenrodriguez@correo.com", 4, 3.5));
//        alumnos.add(new Alumno("00000008U", "Carmen Rodríguez", "carmenrodriguez@correo.com", 0, 7.5));
//        alumnos.add(new Alumno("00000009P", "María García", "mariagarcia@correo.com", 1, 8.2));
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.acercaDe:
                Log.d("LogCat", "Pulsó la opción de menú Acerca de...");
                //Se muestra una ventana de diálogo
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Acerca de...");
                builder.setMessage("Aplicación creada por Juan Tomás Araque y Ángel Custodio Gamero");
                builder.setPositiveButton("OK", null);
                builder.create();
                builder.show();
                break;
            case R.id.actualizarLista:
                Log.d("LogCat", "Pulsó la opción de menú Refrescar lista");
                cargarDatos();
                break;
            case R.id.ordenarNombre:
                Log.d("LogCat", "Pulsó la opción de menú ordenar por nombre");
                Collections.sort(alumnos, new Comparator<Alumno>() {
                    @Override
                    public int compare(Alumno p1, Alumno p2) {
                        return p1.getNombre().compareToIgnoreCase(p2.getNombre());
                    }
                });
                lstAlumnos.getAdapter().notifyDataSetChanged();
                break;
            case R.id.ordenarCurso:
                Log.d("LogCat", "Pulsó la opción de menú ordenar por curso");
                Collections.sort(alumnos, new Comparator<Alumno>() {
                    @Override
                    public int compare(Alumno p1, Alumno p2) {
                        return new Integer(p1.getCurso()).compareTo(new Integer(p2.getCurso()));
                    }
                });
                lstAlumnos.getAdapter().notifyDataSetChanged();
                break;
            case R.id.ordenarCalificacion:
                Log.d("LogCat", "Pulsó la opción de menú ordenar por calificacion");
                Collections.sort(alumnos, new Comparator<Alumno>() {
                    @Override
                    public int compare(Alumno p1, Alumno p2) {
                        return Double.compare(p1.getCalifacion(),p2.getCalifacion());
                    }
                });
                lstAlumnos.getAdapter().notifyDataSetChanged();
                break;
        }
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent
            data) {
        if (data != null) {
            super.onActivityResult(requestCode, resultCode, data);
            Bundle b = data.getExtras();
            if (requestCode == 1234) {
                if (resultCode == RESULT_OK) {

                    conectorBD = new ConectorBD(this);
                    conectorBD.abrir();
                    conectorBD.actualizarAlumno(alumnos.get(alumnoSeleccionado).getDni(), b.getString("nombre"), b.getString("email"),
                            b.getInt("curso"), b.getDouble("calificacion"));

                    Toast.makeText(getBaseContext(), "¡Se ha actualizado el alumno en la BD local!", Toast.LENGTH_SHORT).show();

                    Alumno alumnoModificado = new
                            Alumno(b.getString("dni"), b.getString("nombre"), b.getString("email"),
                            b.getInt("curso"), b.getDouble("calificacion"));
                    alumnos.set(alumnoSeleccionado, alumnoModificado);
                    adaptador.notifyDataSetChanged();
                }
            } else if (requestCode == 1235) {
                if (resultCode == RESULT_OK) {

                    conectorBD = new ConectorBD(this);
                    conectorBD.abrir();
                    conectorBD.insertarAlumno(b.getString("dni"), b.getString("nombre"), b.getString("email"),
                            b.getInt("curso"), b.getDouble("calificacion"));
                    conectorBD.cerrar();

                    alumnos.add(new Alumno(b.getString("dni"), b.getString("nombre"), b.getString("email"),
                            b.getInt("curso"), b.getDouble("calificacion")));
                    adaptador.notifyDataSetChanged();
                    Toast.makeText(getBaseContext(), "¡Se ha añadido un nuevo alumno a la BD local!", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    public void cargarDatos(){
        if(alumnos.size()!=0){
            alumnos.removeAll(alumnos);
        }
        conectorBD = new ConectorBD(this);
        conectorBD.abrir();
        Cursor c = conectorBD.listarAlumnos();
        if (c.moveToFirst()) {
            do {
                Alumno a = new Alumno(null, null, null, 0, 0);
                a.setDni(c.getString(0));
                a.setNombre(c.getString(1));
                a.setEmail(c.getString(2));
                a.setCurso(c.getInt(3));
                a.setCalificacion(c.getDouble(4));
                alumnos.add(a);
            } while (c.moveToNext());
        }
        c.close();
        conectorBD.cerrar();
        if(alumnos.size() > 0){
            Toast.makeText(getBaseContext(), "Datos cargados desde la BD local", Toast.LENGTH_LONG).show();
            lstAlumnos.getAdapter().notifyDataSetChanged();
        }
    }
}

