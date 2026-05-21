package com.github.konstantinevashalomidze.belotegame.tamashi;

public class KozirobisMdgomareoba {
    private KozirobisFaza kozirobisFaza = KozirobisFaza.PIRVELI_KRUGI;
    private final Karti amotrialebuliKarti;
    private int mimdinareMotamashisIndexi; // visi jeria svlis
    private final int kartisDamrigebelisIndexi;
    private Cveti koziriCveti;  // null sheileba
    private Motamashe mokozire; //  null sheileba

    public KozirobisMdgomareoba(Karti amotrialebuliKarti, int kartisDamrigebelisIndexi) {
        this.amotrialebuliKarti = amotrialebuliKarti;
        this.kartisDamrigebelisIndexi = kartisDamrigebelisIndexi;
        // koziroba dileris shemdegi motaashe aris pirveli
        // magisgan iwyeba koziroba
        this.mimdinareMotamashisIndexi = (kartisDamrigebelisIndexi + 1) % 4;
    }

    public KozirobisFaza kozirobisFaza() {
        return kozirobisFaza;
    }

    public Karti amotrialebuliKarti() {
        return amotrialebuliKarti;
    }

    public int mimdinareMotamashisIndexi() {
        return mimdinareMotamashisIndexi;
    }

    public Cveti koziriCveti() {
        return koziriCveti;
    }

    public Motamashe mokozire() {
        return mokozire;
    }

    public boolean dasrulebulia() {
        return kozirobisFaza == KozirobisFaza.DASRULEBULI;
    }

    public void ikozira(Motamashe motamashe, Cveti cveti) {
        koziriCveti = cveti;
        mokozire = motamashe;
        kozirobisFaza = KozirobisFaza.DASRULEBULI;
    }

    public void sityvaShemdegMotamashezeGadavida() {
        mimdinareMotamashisIndexi = (mimdinareMotamashisIndexi + 1) % 4;
    }

    public void shemdegiKrugiDaiwyo() {
        kozirobisFaza = KozirobisFaza.MEORE_KRUGI;
        mimdinareMotamashisIndexi = (kartisDamrigebelisIndexi + 1) % 4;
    }

    public boolean sityvaVinujdenzea() {
        return mimdinareMotamashisIndexi == kartisDamrigebelisIndexi;
    }

    public int kartisDamrigebelisIndexi() {
        return kartisDamrigebelisIndexi;
    }

}