package com.composthelper;

import lombok.Getter;

import java.awt.*;

public enum CompostType {
    NORMAL("Compost"),
    SUPER("Supercompost"),
    ULTRA("Ultracompost");

    @Getter
    private String text;

    CompostType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
