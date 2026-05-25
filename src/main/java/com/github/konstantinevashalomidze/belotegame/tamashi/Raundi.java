package com.github.konstantinevashalomidze.belotegame.tamashi;

import java.util.*;

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

    private KombinaciisShedegi kombinaciisShedegi;

    private BelotisMayurebeli belotisMayurebeli;

    private DastisMomwodebeli dastisMomwodebeli;

    private final Map<Motamashe, List<Kombinacia>> nacxadebiKombinaciebi = new HashMap<>();
    private final Set<Motamashe> mzadVincaaEgMotamasheebi = new HashSet<>();

    private boolean maghaliKombinaciebisMqoneGundsArDaavicwydaKombinaciisChveneba = false;


    public Raundi(List<Motamashe> motamasheebi, Gundi mokozireGundi, Gundi mowinaaghmdegeGundi, int kartisDamrigeblisPozicia, DastisMomwodebeli dastisMomwodebeli) {
        this.motamasheebi = motamasheebi;
        this.mokozireGundi = mokozireGundi;
        this.mowinaaghmdegeGundi = mowinaaghmdegeGundi;
        this.kartisDamrigeblisPozicia = kartisDamrigeblisPozicia;
        this.dastisMomwodebeli = dastisMomwodebeli;
    }

    public void motamasheMzadaa(Motamashe motamashe) {
        sheamowmeSworFazashiTua(KOMBINACIIS_DEKLARACIA);
        mzadVincaaEgMotamasheebi.add(motamashe);

        if (mzadVincaaEgMotamasheebi.size() == 4) {
            kombinaciisShedegi = amoicaniNacxadebiKombinciebi();
            raundisFaza = KRUGEBI;
            axaliKrugisDawyeba();
        }

    }

    private KombinaciisShedegi amoicaniNacxadebiKombinciebi() {
        List<Kombinacia> mokozireGundisKombinaciebi = new ArrayList<>();
        List<Kombinacia> mowinaaghmdegeGundisKombinaciebi = new ArrayList<>();

        for (var entry : nacxadebiKombinaciebi.entrySet()) {
            Motamashe motamashe = entry.getKey();
            List<Kombinacia> kombinaciebi = entry.getValue();
            if (mokozireGundi.sheicavs(motamashe)) {
                mokozireGundisKombinaciebi.addAll(kombinaciebi);
            } else {
                mowinaaghmdegeGundisKombinaciebi.addAll(kombinaciebi);
            }
        }

        if (mokozireGundisKombinaciebi.isEmpty() && mowinaaghmdegeGundisKombinaciebi.isEmpty()) return null;
        if (mokozireGundisKombinaciebi.isEmpty()) return new KombinaciisShedegi(mowinaaghmdegeGundi, mowinaaghmdegeGundisKombinaciebi);
        if (mowinaaghmdegeGundisKombinaciebi.isEmpty()) return new KombinaciisShedegi(mokozireGundi, mokozireGundisKombinaciebi);

        KombinaciisShemdarebeli shemdarebeli = new KombinaciisShemdarebeli(koziriCveti);
        Kombinacia udzlieresiA = shemdarebeli.ipoveUdzlieresiKombinacia(mokozireGundisKombinaciebi);
        Kombinacia udzlieresiB = shemdarebeli.ipoveUdzlieresiKombinacia(mowinaaghmdegeGundisKombinaciebi);

        if (shemdarebeli.metia(udzlieresiA, udzlieresiB)) {
            return new KombinaciisShedegi(mokozireGundi, mokozireGundisKombinaciebi);
        } else {
            return new KombinaciisShedegi(mowinaaghmdegeGundi, mokozireGundisKombinaciebi);
        }

    }


    public void motamashemAcxadaKombinacia(Motamashe motamashe, List<Kombinacia> kombinaciebi) {
        sheamowmeSworFazashiTua(KOMBINACIIS_DEKLARACIA);

        KombinaciisAmomcnobi amomcnobi = new KombinaciisAmomcnobi(koziriCveti);
        List<Kombinacia> aghmocheniliKombinaciebi = amomcnobi.ipoveKombinaciebi(motamashe);

        for (Kombinacia nacxadebiKombinaciebi : kombinaciebi) {
            boolean validuri = aghmocheniliKombinaciebi.stream()
                    .anyMatch(aghmochenili ->
                            aghmochenili.tipi() == nacxadebiKombinaciebi.tipi() &&
                            aghmochenili.cveti() == nacxadebiKombinaciebi.cveti() &&
                            aghmochenili.yvelazeDidiRanki() == nacxadebiKombinaciebi.yvelazeDidiRanki() &&
                            aghmochenili.sigrdze() == nacxadebiKombinaciebi.sigrdze());
            if (!validuri) {
                throw new IllegalArgumentException(
                        "კომბინაცია არ ყავს მოთამაშეს"
                );
            }
        }

        nacxadebiKombinaciebi.put(motamashe, kombinaciebi);

    }

    public void achveneKombinaciebi(Motamashe motamashe) {
        sheamowmeSworFazashiTua(KRUGEBI);

        if (kombinaciisShedegi == null) {
            throw new IllegalStateException("კომბინაციები არ ყავს");
        }

        int meramdeneKrugia = dasrulebuliKrugebi.size();

        if (meramdeneKrugia == 1) {
            if (!kombinaciisShedegi.gundi().sheicavs(motamashe)) {
                throw new IllegalStateException("შენ ვერ ანახებ კომბინაციას");
            }
            maghaliKombinaciebisMqoneGundsArDaavicwydaKombinaciisChveneba = true;
        } else if (meramdeneKrugia == 2 && !maghaliKombinaciebisMqoneGundsArDaavicwydaKombinaciisChveneba) {
            Gundi dabaliKombinaciebisMqoneGundi = kombinaciisShedegi.gundi() == mokozireGundi
                    ? mowinaaghmdegeGundi
                    : mokozireGundi;
            if (!dabaliKombinaciebisMqoneGundi.sheicavs(motamashe)) {
                throw new IllegalStateException("წინა ხელზე რო დაგავიწყდა ნახუი ახლა");
            }
            List<Kombinacia> dabaliKombinaciisMqoneGundisKombinaciebi = nacxadebiKombinaciebi.entrySet().stream()
                    .filter(e -> dabaliKombinaciebisMqoneGundi.sheicavs(e.getKey()))
                    .flatMap(e -> e.getValue().stream())
                    .toList();
            kombinaciisShedegi = new KombinaciisShedegi(dabaliKombinaciebisMqoneGundi, dabaliKombinaciisMqoneGundisKombinaciebi);
            maghaliKombinaciebisMqoneGundsArDaavicwydaKombinaciisChveneba = true;
        } else {
            throw new IllegalStateException("კომბინაციების ჩვენება აღარ მოჟნა");
        }

    }


    public void daarigeKarti() {
        kartisDamrigebeli = new KartisDamrigebeli(motamasheebi, kartisDamrigeblisPozicia, dastisMomwodebeli.sheqmeniDasta());
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
        if (this.raundisFaza != raundisFaza) {
            throw new IllegalStateException("კაროჩე რაცხა პიზდეცი მოხდა და ვერ გეიგებ მაინც. 500 05 13 94 აქ დარეკე.");
        }
    }

    private void daasruleKoziroba() {
        koziriCveti = kozirobisMdgomareoba.koziriCveti();
        mokozire = kozirobisMdgomareoba.mokozire();
        kartisDamrigebeli.meoredDaarige(darcheniliDasta, mokozire, kozirobisMdgomareoba.amotrialebuliKarti());

        belotisMayurebeli = new BelotisMayurebeli(koziriCveti);

        raundisFaza = KOMBINACIIS_DEKLARACIA;
        mimdinareMotamashisPozicia = (kartisDamrigeblisPozicia + 1) % 4;
    }

    private void axaliKrugisDawyeba() {
        mimdinareKrugi = new Krugi(koziriCveti);
    }

    public void kartisTamashi(Motamashe motamashe, Karti karti, BelotisCxadeba belotisCxadeba) {
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

        belotisMayurebeli.belotiAnRebelotiCxadda(motamashe, karti, belotisCxadeba);

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
        RaundisQulisGamomtvleli raundisQulisGamomtvleli = new RaundisQulisGamomtvleli(koziriCveti, mokozireGundi, mowinaaghmdegeGundi);
        return raundisQulisGamomtvleli.gamotvale(dasrulebuliKrugebi, kombinaciisShedegi, belotisMayurebeli);
    }

    public List<Karti> romeliKartebisTamashiaDasashvebi(Motamashe motamashe) {
        sheamowmeSworFazashiTua(KRUGEBI);
        return new NatamashebiKartisShemmowmebeli(koziriCveti).romeliKartebisTamashiSheidzleba(motamashe, mimdinareKrugi);
    }

    public RaundisFaza raundisFaza() {
        return raundisFaza;
    }

    public KozirobisMdgomareoba kozirobisMdgomareoba() {
        return kozirobisMdgomareoba;
    }

    public Krugi mimdinareKrugi() {
        return mimdinareKrugi;
    }

    public int mimdinareMotamashisPozicia() {
        return mimdinareMotamashisPozicia;
    }

    public KombinaciisShedegi kombinaciisShedegi() {
        return kombinaciisShedegi;
    }

    public Gundi mokozireGundi() {
        return mokozireGundi;
    }

    public Cveti koziriCveti() {
        return koziriCveti;
    }
}
