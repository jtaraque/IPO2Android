package com.empresa.alumnado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class DetallesAlumno extends AppCompatActivity {
    //Atributos privados a nivel de clase
    private EditText txtDniC;
    private EditText txtNombreC;
    private Spinner spinnerCurso;
    private EditText txtEmailC;
    private EditText txtCalificacionC;

    private Button btnGuardarC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_alumno);

        //Obtenemos las referencias a los elementos gráficos de la GUI
        txtNombreC=findViewById(R.id.txtNombreC);
        txtDniC =findViewById(R.id.txtDniC);
        spinnerCurso =findViewById(R.id.spinnerCurso);

        //Llenar de contenido el Spinner
        String []opciones={"Primero", "Segundo", "Tercero", "Cuarto", "Master"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, opciones);
        spinnerCurso.setAdapter(adapter);
        txtEmailC=findViewById(R.id.txtEmailC);
        txtCalificacionC =findViewById(R.id.txtCalificacionC);

        //Recoger los datos enviados por la primera actividad y mostrarlos en la GUI
        Bundle bundle=getIntent().getExtras();
        txtDniC.setText(bundle.getString("dni"));
        txtNombreC.setText(bundle.getString("nombre"));
        txtEmailC.setText(bundle.getString("email"));
        spinnerCurso.setSelection(bundle.getInt("curso"));
        txtCalificacionC.setText(String.valueOf(bundle.getDouble("calificacion")));


        btnGuardarC = findViewById(R.id.btnGuardarC);
        btnGuardarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txtDniC.getText().toString().isEmpty() && !txtNombreC.getText().toString().isEmpty()
                && !txtEmailC.getText().toString().isEmpty() && !txtCalificacionC.getText().toString().isEmpty()){
                    Intent nuevoAlumno = new Intent();
                    nuevoAlumno.putExtra("dni",txtDniC.getText().toString());
                    nuevoAlumno.putExtra("nombre", txtNombreC.getText().toString());
                    nuevoAlumno.putExtra("email", txtEmailC.getText().toString());
                    nuevoAlumno.putExtra("curso",spinnerCurso.getSelectedItemPosition());
                    nuevoAlumno.putExtra("calificacion", Double.parseDouble(txtCalificacionC.getText().toString()));
                    setResult(RESULT_OK, nuevoAlumno);
                    finish();
                }
                else {
                    Toast.makeText(getBaseContext(),"¡Rellena toda la información!",Toast.LENGTH_LONG).show();
                }


            }
        });


    }
}