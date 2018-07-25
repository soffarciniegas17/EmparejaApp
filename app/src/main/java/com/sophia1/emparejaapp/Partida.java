package com.sophia1.emparejaapp;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class Partida extends AppCompatActivity{

    private int[] numeros;
    private ArrayList<ItemCartas> cartas;
    private GridView gridView;
    private AdapterCartas adapter;
    private int capacidad;
    private final int VERDE_T=R.drawable.fondo_turno_verde, GRIS_T=R.drawable.fondo_turno_gris;

    private TextView viewJugador1, viewJugador2, viewScore1, viewScore2;
    private int puntos1, puntos2;

    private boolean turnoJugador;

    private Animation voltea, girar_desaparecer, aparecer;

    private Dialog mensajeFinal;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partida_activity);

        findViews();

        voltea= AnimationUtils.loadAnimation(this, R.anim.voltear);
        voltea.setFillAfter(true);

        girar_desaparecer=AnimationUtils.loadAnimation(this, R.anim.girar_desaparecer);
        girar_desaparecer.setFillAfter(true);

        aparecer=AnimationUtils.loadAnimation(this,R.anim.aparecer);
        aparecer.setFillAfter(true);


        mensajeFinal=new Dialog(this);
        mensajeFinal.setContentView(R.layout.mensaje_final);
        mensajeFinal.setCanceledOnTouchOutside(true);
        mensajeFinal.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        capacidad=8;
        gridView=findViewById(R.id.gridview);
        if(capacidad==8)gridView.setNumColumns(capacidad/2/2);
        else if(capacidad==12)gridView.setNumColumns(capacidad/2/2);



        int width=this.getResources().getDisplayMetrics().widthPixels;
        gridView.getLayoutParams().width=width/4*(capacidad/2/2);

        numeros=new int[capacidad];

        for(int i=0; i<numeros.length;i++){
            numeros[i]=-1;
        }

        rellenaAzar(capacidad/2);
        organiza();

        puntos1=0;
        puntos2=0;




    }
    public void findViews(){
        viewJugador1=findViewById(R.id.view_jugador1);
        viewJugador2=findViewById(R.id.view_jugador2);
        viewScore1=findViewById(R.id.view_scores1);
        viewScore2=findViewById(R.id.view_score2);
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
                cartas.add(new ItemCartas(numeros[i],R.drawable.fondo_tapar_tapar_carta));
            }



        }catch (Exception e){

        }
        adapter.notifyDataSetChanged();
        clicItem();
    }
    private boolean turno;
    private final int FONDO=android.R.color.transparent;
    private View view1, view2;
    private int position1, position2;

    public void clicItem(){

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(!turno){

                    if(view1!=null && view2!= null){
                        view1.clearAnimation();
                        view2.clearAnimation();
                    }

                    cartas.get(position).setLayout_tapar(FONDO);
                    position1=position;
                    view1=view;
                    turno=true;




                    view1.startAnimation(voltea);

                    adapter.notifyDataSetChanged();

                }else if(turno){

                    cartas.get(position).setLayout_tapar(FONDO);
                    position2=position;
                    view2=view;
                    turno=false;

                    view2.startAnimation(voltea);
                    view1.clearAnimation();

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

                    view1.startAnimation(girar_desaparecer);
                    view2.startAnimation(girar_desaparecer);

                    view1.setVisibility(View.INVISIBLE);
                    view2.setVisibility(View.INVISIBLE);




                    if(turnoJugador){

                        puntos2+=100;
                        turnoJugador=false;

                    }else{
                        puntos1+=100;
                        turnoJugador=true;

                    }
                    fin();


                }else{


                    cartas.get(position2).setLayout_tapar(R.drawable.fondo_tapar_tapar_carta);
                    cartas.get(position1).setLayout_tapar(R.drawable.fondo_tapar_tapar_carta);

                    view1.startAnimation(voltea);
                    view2.startAnimation(voltea);

                    if(turnoJugador){

                        puntos2-=1;
                        turnoJugador=false;

                    }else{
                        puntos1-=1;
                        turnoJugador=true;

                    }
                }
                adapter.notifyDataSetChanged();
                clicItem();
                cambiarEstadoTurno();

            }
        }.start();

    }
    public void cambiarEstadoTurno(){
        if(turnoJugador){
            viewJugador1.setBackgroundResource(GRIS_T);
            viewScore1.setBackgroundResource(GRIS_T);

            viewJugador2.setBackgroundResource(VERDE_T);
            viewScore2.setBackgroundResource(VERDE_T);


            viewScore1.setText(puntos1+"");
        }else{
            viewJugador1.setBackgroundResource(VERDE_T);
            viewScore1.setBackgroundResource(VERDE_T);

            viewJugador2.setBackgroundResource(GRIS_T);
            viewScore2.setBackgroundResource(GRIS_T);


            viewScore2.setText(puntos2+"");

        }

    }
    public void fin(){
        int ve=0;
        for(int i=0; i<cartas.size();i++){
            if(cartas.get(i).getLayout_tapar()==R.drawable.fondo_tapar_tapar_carta){
                ve=1;
            }
        }
        if(ve==0){
            LinearLayout layoutFinal=mensajeFinal.findViewById(R.id.layout_mensaje_final);
            layoutFinal.startAnimation(aparecer);
            mostrarFinal();
            mensajeFinal.show();
        }
    }
    public void mostrarFinal(){
        TextView nombreGanador, puntajeGanador;

        nombreGanador=mensajeFinal.findViewById(R.id.view_nombre_ganador);
        puntajeGanador=mensajeFinal.findViewById(R.id.view_puntaje_ganador);

        if(puntos1<puntos2){

            nombreGanador.setText(nomJ2);
            puntajeGanador.setText(puntos2+"");

        }else{
            nombreGanador.setText(nomJ1);
            puntajeGanador.setText(puntos1+"");

        }
    }
    public void botonesFinal(View v){

        Intent intent;
        switch (v.getId()){
            case R.id.bton_home:
                intent=new Intent(this, Home.class);
                startActivity(intent);
                mensajeFinal.dismiss();

                break;
            case R.id.bton_replay:
                intent=new Intent(this, Partida.class);
                startActivity(intent);
                mensajeFinal.dismiss();
                break;
            case R.id.bton_compartir:
                break;
        }
    }
    private String nomJ1, nomJ2;
    public void onResume(){
        super.onResume();
        SharedPreferences datos= PreferenceManager.getDefaultSharedPreferences(this);

        nomJ1=datos.getString("JUGADOR_1","JUDADOR 1");
        nomJ2=datos.getString("JUGADOR_2","JUDADOR 2");


        viewJugador1.setText(nomJ1);
        viewJugador2.setText(nomJ2);

        Random random=new Random();
        turnoJugador=random.nextBoolean();

        cambiarEstadoTurno();






    }
    public void onPause(){
        super.onPause();
        SharedPreferences datos=PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor guarda=datos.edit();




        guarda.apply();
    }
    private int tiempoPartida=0;
    private String mode="1";
    public void guardarDatos(){
        RegistrosBBDD db=new RegistrosBBDD(this);

        db.guardarDatos(nomJ1,puntos1,tiempoPartida,capacidad+"",mode);
        db.guardarDatos(nomJ2,puntos2,tiempoPartida,capacidad+"",mode);
    }
}
