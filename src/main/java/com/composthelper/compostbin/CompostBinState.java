package com.composthelper.compostbin;

import com.composthelper.CompostType;
import lombok.Getter;

public class CompostBinState {

    @Getter
    private CompostType type;

    @Getter
    private CompostBinAction action;

    @Getter
    private int contentCount;

    public CompostBinState(CompostType type, CompostBinAction action, int contentCount) {
        this.type = type;
        this.action = action;
        this.contentCount = contentCount;
    }
}
