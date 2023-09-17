package com.composthelper.compostbin;

import lombok.Getter;

import java.awt.*;

public enum CompostBinAction {

    EMPTY(Color.red),
    FILLING(Color.yellow),
    EMPTYING(Color.green),
    ROTTING(Color.white);

    @Getter
    private Color textColor;

    CompostBinAction(Color textColor) {
        this.textColor = textColor;
    }
}
