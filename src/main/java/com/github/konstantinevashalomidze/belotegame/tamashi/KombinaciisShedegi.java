package com.github.konstantinevashalomidze.belotegame.tamashi;

import java.util.List;

public record KombinaciisShedegi(
        Gundi gundi, List<Kombinacia> kombinaciebi
) {
    public int qula() {
        return kombinaciebi.stream()
                .mapToInt(Kombinacia::qula).sum();
    }
}
