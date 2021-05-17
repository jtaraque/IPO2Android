package com.empresa.alumnado;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

    public class ConectorBD {
        static final String NOMBRE_BD = "BDAlumnado";
        private AlumnosSQLiteHelper dbHelper;
        private SQLiteDatabase db;


        /*Constructor*/
        public ConectorBD(Context ctx) {
            dbHelper = new AlumnosSQLiteHelper(ctx, NOMBRE_BD, null, 1);
        }

        /*Abre la conexión con la base de datos*/
        public ConectorBD abrir() throws SQLException {
            db = dbHelper.getWritableDatabase();
            return this;
        }

        /*Cierra la conexión con la base de datos*/
        public void cerrar() {
            if (db != null) db.close();
        }

        /*inserta un alumno en la BD*/
        public void insertarAlumno(String dni, String nombre, String email, int curso, double calificacion) {
            String consultaSQL = "INSERT INTO alumnos VALUES('" + dni + "', '" + nombre + "', '" + email + "', '" + curso + "', '" + calificacion + "') ";
            db.execSQL(consultaSQL);
        }

        public void actualizarAlumno(String dni, String nombre, String email, int curso, double calificacion) {
            String consultaSQL = "UPDATE alumnos SET nombre='" +nombre+ "',email='" +email+"',curso='" +curso+"',calificacion='" +calificacion+"' WHERE dni = '"+dni+"' ";
            db.execSQL(consultaSQL);
        }

        public void eliminarAlumno(String dni){
            String consultaSQL = "DELETE FROM alumnos WHERE dni= '"+dni+"' ";
            db.execSQL(consultaSQL);
        }

        /*devuelve todos los alumnos*/
        public Cursor listarAlumnos() {
            return db.rawQuery("SELECT * FROM alumnos", null);
        }
    }

