package com.github.konstantinevashalomidze.belotegame.tamashi;

import java.util.ArrayList;
import java.util.List;

import static com.github.konstantinevashalomidze.belotegame.tamashi.RaundisFaza.*;

public class Raundi {

    private final List<Motamashe> motamasheebi;
    private final Gundi mokozireGundi;
    private final Gundi mowinaaghmdegeGundi;
    private final int kartisDamrigeblisPozicia;

    private KartisDamrigebeli kartisDamrigebeli;
    private KozirobisMdgomareoba kozirobisMdgomareoba;
    private KozirobisMmartveli kozirobisMmartveli;
    private Dasta darcheniliDasta;

    private Cveti koziriCveti;
    private Motamashe mokozire;

    private final List<Krugi> dasrulebuliKrugebi = new ArrayList<>();
    private Krugi mimdinareKrugi;
    private int mimdinareMotamashisPozicia;

    private RaundisFaza raundisFaza = KARTIS_DARIGEBA;


    public Raundi(List<Motamashe> motamasheebi, Gundi mokozireGundi, Gundi mowinaaghmdegeGundi, int kartisDamrigeblisPozicia) {
        this.motamasheebi = motamasheebi;
        this.mokozireGundi = mokozireGundi;
        this.mowinaaghmdegeGundi = mowinaaghmdegeGundi;
        this.kartisDamrigeblisPozicia = kartisDamrigeblisPozicia;
    }


    public void daarigeKarti() {
        kartisDamrigebeli = new KartisDamrigebeli(motamasheebi, kartisDamrigeblisPozicia);
        KartisDarigebisShedegi kartisDarigebisShedegi = kartisDamrigebeli.pirveladDaarige();
        darcheniliDasta = kartisDarigebisShedegi.darcheniliDasta();

        kozirobisMdgomareoba = new KozirobisMdgomareoba(
                kartisDarigebisShedegi.amotrialebuliKarti(), kartisDamrigeblisPozicia);
        kozirobisMmartveli = new KozirobisMmartveli(motamasheebi, kozirobisMdgomareoba);

        kozirobisMmartveli.sheamowmeAmotrialebuliKartiValetze();

        if (kozirobisMdgomareoba.dasrulebulia()) {
            daasruleKoziroba();
        } else {
            raundisFaza = KOZIROBA;
        }
    }

    public void motamashemWaighoPirvelive(Motamashe motamashe) {
        sheamowmeSworFazashiTua(KOZIROBA);
        kozirobisMmartveli.ikozira(motamashe);
        if (kozirobisMdgomareoba.dasrulebulia()) daasruleKoziroba();
    }

    public void motamashemPasiTqva(Motamashe motamashe) {
        sheamowmeSworFazashiTua(KOZIROBA);
        kozirobisMmartveli.motamashemTqvaPasi(motamashe);
        if (kozirobisMdgomareoba.dasrulebulia()) daasruleKoziroba();
    }

    public void motamashemAcxadaMeoreshi(Motamashe motamashe, Cveti koziriCveti) {
        sheamowmeSworFazashiTua(KOZIROBA);
        kozirobisMmartveli.ikozira(motamashe, koziriCveti);
        if (kozirobisMdgomareoba.dasrulebulia()) daasruleKoziroba();
    }

    public void motamashemVinujdenshiWaigho(Cveti cveti) {
        sheamowmeSworFazashiTua(KOZIROBA);
        kozirobisMmartveli.vinujdenmaIkozira(cveti);
        if (kozirobisMdgomareoba.dasrulebulia()) daasruleKoziroba();
    }

    private void sheamowmeSworFazashiTua(RaundisFaza raundisFaza) {

    }

    private void daasruleKoziroba() {
        koziriCveti = kozirobisMdgomareoba.koziriCveti();
        mokozire = kozirobisMdgomareoba.mokozire();
        kartisDamrigebeli.meoredDaarige(darcheniliDasta, mokozire, kozirobisMdgomareoba.amotrialebuliKarti());
        raundisFaza = KRUGEBI;

        mimdinareMotamashisPozicia = (kartisDamrigeblisPozicia + 1) % 4;
        axaliKrugisDawyeba();
    }

    private void axaliKrugisDawyeba() {
        mimdinareKrugi = new Krugi(koziriCveti);
    }

    public void kartisTamashi(Motamashe motamashe, Karti karti) {
        sheamowmeSworFazashiTua(KRUGEBI);
        if (motamasheebi.indexOf(motamashe) != mimdinareMotamashisPozicia) {
            throw new IllegalStateException("აუ ვინაა ეს ყლე, არაა შენი ჯერი არა!!");
        }

        NatamashebiKartisShemmowmebeli shemmowmebeli = new NatamashebiKartisShemmowmebeli(koziriCveti);
        List<Karti> romeliKartebisTamashiaDasashvebi = shemmowmebeli.romeliKartebisTamashiSheidzleba(
                motamashe,
                mimdinareKrugi
        );
        if (!romeliKartebisTamashiaDasashvebi.contains(karti)) {
            throw new IllegalArgumentException("ვაი ბიძია... რას გვატყუებ ამდენ ხალხს");
        }

        motamashe.xeli().moishore(karti);
        mimdinareKrugi.motamashemChamovidaKarti(motamashe, karti);

        if (mimdinareKrugi.sruliKrugia()) {
            dasrulebuliKrugebi.add(mimdinareKrugi);

            if (dasrulebuliKrugebi.size() == 8) {
                raundisFaza = QULEBIS_DATVLA;
            }else {
                NatamashebiKarti gamarjcebuli = mimdinareKrugi.daadgineGamarjvebuli();
                mimdinareMotamashisPozicia = motamasheebi.indexOf(gamarjcebuli.motamashe());
                axaliKrugisDawyeba();
            }
        } else {
            mimdinareMotamashisPozicia = (mimdinareMotamashisPozicia + 1) % 4;
        }
    }


    public RaundisShedegi qula() {
        sheamowmeSworFazashiTua(QULEBIS_DATVLA);
        KombinaciisMmartveli mmartveli = new KombinaciisMmartveli(koziriCveti);
        KombinaciisShedegi shedegi = mmartveli.visiKombinaciaGadis(mokozireGundi, mowinaaghmdegeGundi);
        RaundisQulisGamomtvleli gamomtvleli = new RaundisQulisGamomtvleli(koziriCveti, mokozireGundi, mowinaaghmdegeGundi);
        return gamomtvleli.gamotvale(dasrulebuliKrugebi, shedegi);

    }

}
