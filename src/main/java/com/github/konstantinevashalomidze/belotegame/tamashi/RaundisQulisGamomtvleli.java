package com.github.konstantinevashalomidze.belotegame.tamashi;

import java.util.ArrayList;
import java.util.List;

public class RaundisQulisGamomtvleli {
    private final Cveti koziriCveti;
    private final Gundi mokozireGundi;
    private final Gundi mowinaaghmdegeGundi;
    private final QulisGamomtvleli qulisGamomtvleli;

    public RaundisQulisGamomtvleli(
            Cveti koziriCveti,
            Gundi mokozireGundi,
            Gundi mowinaaghmdegeGundi
    ) {
        this.koziriCveti = koziriCveti;
        this.mokozireGundi = mokozireGundi;
        this.mowinaaghmdegeGundi = mowinaaghmdegeGundi;
        qulisGamomtvleli = new QulisGamomtvleli(koziriCveti);
    }


    public RaundisShedegi gamotvale(List<Krugi> krugebi, KombinaciisShedegi kombinaciisShedegi, BelotisMayurebeli belotisMayurebeli) {
        List<Krugi> mokoziresKrugebi = new ArrayList<>();
        List<Krugi> mowinaaghmdegesQulebi = new ArrayList<>();


        for (int i = 0; i < krugebi.size(); i++) {
            Krugi krugi = krugebi.get(i);
            Motamashe gamarjvebuls = krugi.daadgineGamarjvebuli().motamashe();
            if (mokozireGundi.sheicavs(gamarjvebuls)) {
                mokoziresKrugebi.add(krugi);
            } else {
                mowinaaghmdegesQulebi.add(krugi);
            }
        }

        if (mokoziresKrugebi.size() == 8) {
            return kapo(mokozireGundi, mowinaaghmdegeGundi, kombinaciisShedegi, belotisMayurebeli);
        }

        if (mowinaaghmdegesQulebi.size() == 8) {
            return kapo(mowinaaghmdegeGundi, mokozireGundi, kombinaciisShedegi, belotisMayurebeli);
        }



        boolean mokoziremWaighoBoloKarti = mokozireGundi.sheicavs(
                krugebi.get(7).daadgineGamarjvebuli().motamashe()
        );

        int mokozireGundisQulebi = qulisGamomtvleli.gamotvale(
                mokoziresKrugebi, mokoziremWaighoBoloKarti
        );

        int mowinaaghmdegisQulebi = qulisGamomtvleli.gamotvale(
                mowinaaghmdegesQulebi, !mokoziremWaighoBoloKarti
        );


        int mokozireGundisKombinaciisQulebi = 0;
        int mowinaaghmdegeGundisKombinaciisQulebi = 0;

        if (kombinaciisShedegi != null) {
            if (kombinaciisShedegi.gundi() == mokozireGundi) {
                mokozireGundisKombinaciisQulebi = kombinaciisShedegi.qula();
            } else {
                mowinaaghmdegeGundisKombinaciisQulebi = kombinaciisShedegi.qula();
            }
        }

        if (belotisMayurebeli.belotRebelotiArisNatamashebi()) {
            Motamashe belotiVisacYavs = belotisMayurebeli.belotiVisacYavs();
            if (mokozireGundi.sheicavs(belotiVisacYavs)) {
                mokozireGundisKombinaciisQulebi += belotisMayurebeli.belotisQula();
            } else {
                mowinaaghmdegeGundisKombinaciisQulebi += belotisMayurebeli.belotisQula();
            }
        }

        int mokozirisQulebisJami = mokozireGundisQulebi + mokozireGundisKombinaciisQulebi;
        int mowinaaghmdegisQulebisJami = mowinaaghmdegisQulebi + mowinaaghmdegeGundisKombinaciisQulebi;


        if (mokozirisQulebisJami < mowinaaghmdegisQulebisJami) {
            return chavarda(kombinaciisShedegi, belotisMayurebeli);
        }

        if (mokozirisQulebisJami == mowinaaghmdegisQulebisJami) {
            return RaundisShedegi.fre(mowinaaghmdegisQulebisJami);
        }

        return RaundisShedegi.chveulebrivi(
                daamrgvale(mokozirisQulebisJami),
                daamrgvale(mowinaaghmdegisQulebisJami)
        );

    }

    private int daamrgvale(int mokozirisQulebisJami) {
        int nashti = mokozirisQulebisJami % 10;
        if (nashti <= 5) {
            return mokozirisQulebisJami - nashti;
        } else {
            return mokozirisQulebisJami + (10 - nashti);
        }
    }

    private RaundisShedegi chavarda(KombinaciisShedegi kombinaciisShedegi, BelotisMayurebeli belotisMayurebeli) {
        int mowinaaghmdegeGundisQula = 160;
        if (kombinaciisShedegi != null && kombinaciisShedegi.gundi() == mokozireGundi) {
            mowinaaghmdegeGundisQula += kombinaciisShedegi.qula();
        }

        if (belotisMayurebeli.belotRebelotiArisNatamashebi()) {
            Motamashe belotiVisacYavs = belotisMayurebeli.belotiVisacYavs();
            if (mokozireGundi.sheicavs(belotiVisacYavs)) {
                mowinaaghmdegeGundisQula += belotisMayurebeli.belotisQula();
            }
        }
        return RaundisShedegi.chavardna(mowinaaghmdegeGundisQula);
    }

    private RaundisShedegi kapo(Gundi vincKapoGaaketa, Gundi vincChaijva, KombinaciisShedegi kombinaciisShedegi, BelotisMayurebeli belotisMayurebeli) {
        int vincChaijvaMagisQula = 0;
        if (kombinaciisShedegi != null && kombinaciisShedegi.gundi() == vincChaijva) {
            vincChaijvaMagisQula = kombinaciisShedegi.qula();
        }

        if (belotisMayurebeli.belotRebelotiArisNatamashebi()) {
            Motamashe belotiVisacYavs = belotisMayurebeli.belotiVisacYavs();
            if (mowinaaghmdegeGundi.sheicavs(belotiVisacYavs)) {
                vincChaijvaMagisQula += belotisMayurebeli.belotisQula();
            }
        }

        return RaundisShedegi.kapo(vincChaijvaMagisQula);
    }



}
