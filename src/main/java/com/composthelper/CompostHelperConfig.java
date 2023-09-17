package com.composthelper;

import net.runelite.client.config.*;

import java.awt.*;

@ConfigGroup("composthelper")
public interface CompostHelperConfig extends Config {
    @ConfigSection(
            name = "Compost Bin",
            description = "Settings for the compost bin overlay",
            position = 0
    )
    String compostBinSection = "compostBinSection";

    @ConfigItem(
            keyName = "showCompostType",
            name = "Show Compost Type",
            description = "Show which kind of compost is in the compost bin",
            position = 1,
            section =  "compostBinSection"
    )
    default boolean showCompostType() { return false; }

    @ConfigItem(
            keyName = "showMaxSize",
            name = "Show Maximum Capacity",
            description = "Show the maximum capacity of the compost bin",
            position = 2,
            section =  "compostBinSection"
    )
    default boolean showMaxSize() {
        return true;
    }
}
