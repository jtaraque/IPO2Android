package com.empresa.alumnado;
public class Alumno {
    private String dni;
    private String nombre;
    private int curso; //0:Primero; 1:Segundo; 2:Tercero; 3:Cuarto; 4:Master
    private String email;
    private double calificacion;
    public Alumno(String id, String nom, String em, int cur, double nota){
        dni = id;
        nombre= nom;
        curso=cur;
        email=em;
        calificacion = nota;
    }
    public String getDni(){
        return dni;
    }
    public String getNombre(){
        return nombre;
    }
    public double getCalifacion(){
        return calificacion;
    }
    public String getEmail(){
        return email;
    }
    public int getCurso(){ return curso; }

    public void setDni(String dni){ this.dni = dni;}
    public void setNombre(String nombre){ this.nombre = nombre;}
    public void setEmail(String email){ this.email = email;}
    public void setCalificacion(Double calificacion){this.calificacion = calificacion;}
    public void setCurso(int curso){this.curso = curso;}
}