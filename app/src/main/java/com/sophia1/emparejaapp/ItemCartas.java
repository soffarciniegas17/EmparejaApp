package com.sophia1.emparejaapp;

public class ItemCartas {

    private int numero;
    private int layout_tapar;

    public ItemCartas(int numero, int layout_tapar) {
        this.numero = numero;
        this.layout_tapar = layout_tapar;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getLayout_tapar() {
        return layout_tapar;
    }

    public void setLayout_tapar(int layout_tapar) {
        this.layout_tapar = layout_tapar;
    }
}
