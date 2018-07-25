package com.sophia1.emparejaapp;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    TextView t, p1, p2, p3, p4;
    EditText j;
    Button si, easy, hard, medium;
    Dialog registry, settings, scores;
    String nick1, nick2;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        registry = new Dialog(this);
        registry.setContentView(R.layout.dialog_names);


        settings = new Dialog(this);
        settings.setContentView(R.layout.dialog_settings);

        settings = new Dialog(this);
        settings.setContentView(R.layout.dialog_settings);
        ingresarNombres();
    }

int comprueba=0;
    public void ingresarNombres (){
        si = registry.findViewById(R.id.okey);
        t= registry.findViewById(R.id.texto);
        j= registry.findViewById(R.id.editText);
        si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (comprueba==0){
                    t= registry.findViewById(R.id.texto);
                    t.setText("1 JUGADOR");
                    nick1=  j.getText().toString();
                    t.setText("");
                    if (nick1.equalsIgnoreCase("")){
                        nick1= "USSER 1";
                    }
                    comprueba=1;
                    ingresarNombres();
                } else {
                    t= registry.findViewById(R.id.texto);
                    t.setText("2 JUGADOR");
                    nick2 = j.getText().toString();
                    if (nick2.equalsIgnoreCase("")){
                        nick2="USSER 2";
                    }
                    registry.dismiss();}
            }
        });
    registry.show();
    }


    public void menu (View v){
        Intent i;
        switch (v.getId()){
            case R.id.juego:
                i = new Intent(this, Partida.class);
                startActivity(i);
                break;
            case R.id.scoress:
                break;
        }
    }

    public void puntajes (){
        easy = settings.findViewById(R.id.easy);
        medium = settings.findViewById(R.id.medium);
        hard = settings.findViewById(R.id.hard);

     //   p1 =
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences recupera = PreferenceManager.getDefaultSharedPreferences(this);

        nick1= recupera.getString("nick1", "Jugador 1");
        nick2= recupera.getString("nick2", "Jugador 2");

    }

    @Override
    protected void onPause() {

        SharedPreferences datos = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = datos.edit();

        editor.putString("nick1", nick1);
        editor.putString("nick2", nick2);

        editor.apply();
        super.onPause();
    }


}
