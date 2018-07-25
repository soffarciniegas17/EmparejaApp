package com.sophia1.emparejaapp;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class AdapterCartas extends BaseAdapter {

    private ArrayList<ItemCartas> cartas;
    private int layoutItem;
    private Context context;
    private final int[] FIGURAS={
            R.drawable.a1,
            R.drawable.a2,
            R.drawable.a3,
            R.drawable.a4,
            R.drawable.a5,
            R.drawable.a6,
            R.drawable.a7,
            R.drawable.a8

    };

    public AdapterCartas(ArrayList<ItemCartas> cartas, int layoutItem, Context context) {
        this.cartas = cartas;
        this.layoutItem = layoutItem;
        this.context = context;
    }

    @Override
    public int getCount() {
        return cartas.size();
    }

    @Override
    public Object getItem(int posicion) {
        return cartas.get(posicion);
    }

    @Override
    public long getItemId(int posicion) {
        return posicion;
    }

    @Override
    public View getView(int posicion, View view, ViewGroup viewGroup) {

        View row=view;
        Holder holder=new Holder();

        if(row==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(layoutItem,null);

            holder.figura=row.findViewById(R.id.view_figura);
            holder.fondo=row.findViewById(R.id.view_tapar);

            row.setTag(holder);



        }else{
            holder=(Holder)row.getTag();

        }

        ItemCartas carta=cartas.get(posicion);

        holder.figura.setImageResource(FIGURAS[carta.getNumero()]);
        holder.figura.setBackgroundResource(R.drawable.fondo_tapar_tapar_carta);
        holder.fondo.setBackgroundResource(carta.getLayout_tapar());

        return row;
    }
    public class Holder{
        ImageView figura, fondo;
    }
}
