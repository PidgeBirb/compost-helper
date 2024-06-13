package com.composthelper.compostbin;

import com.composthelper.CompostType;
import lombok.Getter;
import net.runelite.api.Client;
import net.runelite.api.Varbits;

import javax.inject.Inject;

public enum CompostBinObject {
    ARDOUNGE(7839, Varbits.FARMING_4775),
    CATHERBY(7837, Varbits.FARMING_4775),
    FALADOR(7836, Varbits.FARMING_4775),
    HOSIDIUS(27112, Varbits.FARMING_4775),
    MORYTANIA(7838, Varbits.FARMING_4775),
    PRIFDDINAS(34920, Varbits.FARMING_4774),
    FARMING_GUILD(34631, Varbits.FARMING_7912),
    CIVITAS(50694, Varbits.FARMING_4775);

    @Getter
    private int objectId;

    @Getter
    private int varbit;

    CompostBinObject(int objectId, int varbit) {
        this.objectId = objectId;
        this.varbit = varbit;
    }

    public CompostBinState getState(int value) {
        if (this == CompostBinObject.FARMING_GUILD) {
            if (value == 0) {
                return new CompostBinState(null, CompostBinAction.EMPTY, 0);
            }
            if (value >= 1 && value <= 15) {
                return new CompostBinState(CompostType.NORMAL, CompostBinAction.FILLING, value);
            }
            if (value >= 16 && value <= 30) {
                return new CompostBinState(CompostType.NORMAL, CompostBinAction.EMPTYING, value - 15);
            }
            if (value >= 33 && value <= 47) {
                return new CompostBinState(CompostType.SUPER, CompostBinAction.FILLING, value - 32);
            }
            if (value >= 48 && value <= 62) {
                return new CompostBinState(CompostType.SUPER, CompostBinAction.EMPTYING, value - 47);
            }
            if (value >= 63 && value <= 77) {
                return new CompostBinState(CompostType.NORMAL, CompostBinAction.FILLING, 15 + value - 62);
            }
            if (value >= 78 && value <= 92) {
                return new CompostBinState(CompostType.NORMAL, CompostBinAction.EMPTYING, 15 + value - 77);
            }
            if (value >= 97 && value <= 98) {
                return new CompostBinState(CompostType.SUPER, CompostBinAction.ROTTING, 30);
            }
            if (value == 99) {
                return new CompostBinState(CompostType.SUPER, CompostBinAction.EMPTYING, 30);
            }
            if (value >= 100 && value <= 114) {
                return new CompostBinState(CompostType.SUPER, CompostBinAction.EMPTYING, 15 + value - 99);
            }
            if (value >= 161 && value <= 175) {
                return new CompostBinState(CompostType.SUPER, CompostBinAction.FILLING, 15 + value - 160);
            }
            if (value >= 176 && value <= 205) {
                return new CompostBinState(CompostType.ULTRA, CompostBinAction.EMPTYING, value - 175);
            }
        } else {
            if (value == 0) {
                return new CompostBinState(null, CompostBinAction.EMPTY, 0);
            }
            if (value >= 1 && value <= 15) {
                return new CompostBinState(CompostType.NORMAL, CompostBinAction.FILLING, value);
            }
            if (value >= 16 && value <= 30) {
                return new CompostBinState(CompostType.NORMAL, CompostBinAction.EMPTYING, value - 15);
            }
            if (value == 31 || value == 32) {
                return new CompostBinState(CompostType.NORMAL, CompostBinAction.ROTTING, 15);
            }
            if (value >= 33 && value <= 47) {
                return new CompostBinState(CompostType.SUPER, CompostBinAction.FILLING, value - 32);
            }
            if (value >= 48 && value <= 62) {
                return new CompostBinState(CompostType.SUPER, CompostBinAction.EMPTYING, value - 47);
            }
            if (value == 94) {
                return new CompostBinState(CompostType.NORMAL, CompostBinAction.EMPTYING, 15);
            }
            if (value == 95 || value == 96) {
                return new CompostBinState(CompostType.SUPER, CompostBinAction.ROTTING, 15);
            }
            if (value == 126) {
                return new CompostBinState(CompostType.SUPER, CompostBinAction.EMPTYING, 15);
            }
            if (value >= 176 && value <= 190) {
                return new CompostBinState(CompostType.ULTRA, CompostBinAction.EMPTYING, value - 175);
            }
        }

        return null;
    }
}
