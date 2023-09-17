package com.composthelper.compostbin;

import lombok.Getter;
import net.runelite.api.GameObject;

public class CompostBin {

    @Getter
    private GameObject gameObject;

    @Getter
    private CompostBinObject compostBinObject;

    public CompostBin(GameObject gameObject, CompostBinObject compostBinObject) {
        this.gameObject = gameObject;
        this.compostBinObject = compostBinObject;
    }
}
