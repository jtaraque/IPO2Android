package com.empresa.alumnado;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorLista extends
        RecyclerView.Adapter<AdaptadorLista.ViewHolder> {
    private ArrayList<Alumno> alumnos;
    private OnItemSelectedListener itemSelectedListener;

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView lblNombre;
        private TextView lblEmail;
        private TextView lblCalificacion;
        private ImageView imagAlumno;
        ViewHolder(View view) {
            super(view);
            lblNombre = view.findViewById(R.id.lblNombre);
            lblEmail = view.findViewById(R.id.lblEmail);
            lblCalificacion = view.findViewById(R.id.lblCalificacion);
            imagAlumno = view.findViewById(R.id.imagAlumno);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int posicion = getAdapterPosition();
                    if (itemSelectedListener != null) {
                        itemSelectedListener.onAlumnoSeleccionado(posicion);
                    }
                }
            });

            // Creación del menú popup
            PopupMenu popup = new PopupMenu(view.getContext(), view);
            popup.getMenuInflater().inflate(R.menu.menu_contextual, popup.getMenu());
            view.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                @Override
                public void onCreateContextMenu(ContextMenu menu, View v,
                                                ContextMenu.ContextMenuInfo menuInfo)
                {
                    popup.show();
                }
            });

            // Oyente de selección de opciones del menú popup
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener (){
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (itemSelectedListener != null) {
                        itemSelectedListener.onMenuContextualAlumno(getAdapterPosition(), item);
                    }
                    return true;
                }
            });

        }
    }
    public AdaptadorLista(ArrayList<Alumno> alumnos){
        this.alumnos = alumnos;
    }
    @Override
    public AdaptadorLista.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(AdaptadorLista.ViewHolder holder, int position) {
        Alumno alumno = alumnos.get(position);
        holder.lblNombre.setText(alumnos.get(position).getNombre());
        holder.lblEmail.setText(alumnos.get(position).getEmail());
        holder.lblCalificacion.setText("Nota media: "+alumnos.get(position).getCalifacion());
        switch (alumnos.get(position).getCurso())
        {
            case 0: //Cargar imagen de alumno de primer curso
                holder.imagAlumno.setImageResource(R.drawable.curso1);
                break;
            case 1: //Cargar imagen de alumno de segundo curso
                holder.imagAlumno.setImageResource(R.drawable.curso2);
                break;
            case 2: //Cargar imagen de alumno de tercer curso
                holder.imagAlumno.setImageResource(R.drawable.curso3);
                break;
            case 3: //Cargar imagen de alumno de cuarto curso
                holder.imagAlumno.setImageResource(R.drawable.curso4);
                break;
            case 4: //Cargar imagen de alumno de máster
                holder.imagAlumno.setImageResource(R.drawable.master);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return alumnos.size();
    }

    public void setItemSelectedListener(OnItemSelectedListener itemSelectedListener)
    {
        this.itemSelectedListener = itemSelectedListener;
    }
}
