package com.github.konstantinevashalomidze.belotegame.tamashi;

public class KozirobisMdgomareoba {
    private KozirobisFaza kozirobisFaza = KozirobisFaza.PIRVELI_KRUGI;
    private final Karti amotrialebuliKarti;
    private int mimdinareMotamashisPozicia; // visi jeria svlis
    private final int kartisDamrigebelisPozicia;
    private Cveti koziriCveti;  // null sheileba
    private Motamashe mokozire; //  null sheileba

    public KozirobisMdgomareoba(Karti amotrialebuliKarti, int kartisDamrigebelisPozicia) {
        this.amotrialebuliKarti = amotrialebuliKarti;
        this.kartisDamrigebelisPozicia = kartisDamrigebelisPozicia;
        // koziroba dileris shemdegi motaashe aris pirveli
        // magisgan iwyeba koziroba
        this.mimdinareMotamashisPozicia = (kartisDamrigebelisPozicia + 1) % 4;
    }

    public KozirobisFaza kozirobisFaza() {
        return kozirobisFaza;
    }

    public Karti amotrialebuliKarti() {
        return amotrialebuliKarti;
    }

    public int mimdinareMotamashisPozicia() {
        return mimdinareMotamashisPozicia;
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
        mimdinareMotamashisPozicia = (mimdinareMotamashisPozicia + 1) % 4;
    }

    public void shemdegiKrugiDaiwyo() {
        kozirobisFaza = KozirobisFaza.MEORE_KRUGI;
        mimdinareMotamashisPozicia = (kartisDamrigebelisPozicia + 1) % 4;
    }

    public boolean sityvaVinujdenzea() {
        return mimdinareMotamashisPozicia == kartisDamrigebelisPozicia;
    }

    public int kartisDamrigebelisPozicia() {
        return kartisDamrigebelisPozicia;
    }

}