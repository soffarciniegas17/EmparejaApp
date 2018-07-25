package com.sophia1.emparejaapp;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Random;

public class Partida extends AppCompatActivity{

    private int[] numeros;
    private ArrayList<ItemCartas> cartas;
    private GridView gridView;
    private AdapterCartas adapter;
    private int capacidad;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partida_activity);

        capacidad=8;
        gridView=findViewById(R.id.gridview);
        numeros=new int[capacidad];

        for(int i=0; i<numeros.length;i++){
            numeros[i]=-1;
        }

        rellenaAzar(capacidad/2);
        organiza();
        gridView.setNumColumns(GridView.AUTO_FIT);



    }
    int colocar=0;

    public int rellenaAzar(int parejas){

        int ve=0;
        for (int i=0; i<numeros.length; i++){
            if(numeros[i]==-1){
                ve=1;
            }
        }
        if(ve==0)return 0;

        Random random=new Random();
        int a=random.nextInt(numeros.length);


        if(numeros[a]==-1){
            numeros[a]=colocar;
            colocar++;
            if(colocar==parejas)colocar=0;
        }


        rellenaAzar(parejas);
        return 0;



    }
    public void organiza(){
        try{
            cartas=new ArrayList<>();
            adapter=new AdapterCartas(cartas,R.layout.item_carta,this);
            gridView.setAdapter(adapter);

            for(int i=0; i<capacidad;i++){
                cartas.add(new ItemCartas(numeros[i],android.R.color.black));
            }



        }catch (Exception e){

        }
        adapter.notifyDataSetChanged();
        clicItem();
    }
    private boolean turno=true;
    private final int FONDO=android.R.color.transparent;
    private View view1, view2;
    private int position1, position2;

    public void clicItem(){

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(turno && cartas.get(position).getLayout_tapar()!=FONDO){

                    cartas.get(position).setLayout_tapar(FONDO);
                    position1=position;
                    view1=view;
                    turno=false;

                    adapter.notifyDataSetChanged();

                }else if(!turno && cartas.get(position).getLayout_tapar()!=FONDO && tiempoComprueba!=null){

                    cartas.get(position).setLayout_tapar(FONDO);
                    position2=position;
                    view2=view;
                    turno=true;

                    adapter.notifyDataSetChanged();
                    tiempoComprueba();
                }

            }
        });
    }
    CountDownTimer tiempoComprueba;
    public void tiempoComprueba(){

        gridView.setOnItemClickListener(null);

        tiempoComprueba=new CountDownTimer(1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if(cartas.get(position1).getNumero()==cartas.get(position2).getNumero()){

                    view1.setVisibility(View.INVISIBLE);
                    view2.setVisibility(View.INVISIBLE);

                }else{
                    cartas.get(position2).setLayout_tapar(android.R.color.black);
                    cartas.get(position1).setLayout_tapar(android.R.color.black);
                }
                adapter.notifyDataSetChanged();

            }
        }.start();

    }
}
