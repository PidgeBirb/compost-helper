package com.composthelper;

import com.composthelper.compostbin.CompostBin;
import com.composthelper.compostbin.CompostBinObject;
import com.composthelper.compostbin.CompostBinOverlay;
import com.google.inject.Provides;

import javax.inject.Inject;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameObjectDespawned;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(
        name = "Compost Helper"
)
public class CompostHelperPlugin extends Plugin {
    @Inject
    private Client client;

    @Inject
    private CompostHelperConfig config;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private CompostBinOverlay overlay;

    @Getter
    private CompostBin visibleCompostBin;


    @Override
    protected void startUp() throws Exception {
        overlayManager.add(overlay);
    }

    @Override
    protected void shutDown() throws Exception {
        overlayManager.remove(overlay);
        visibleCompostBin = null;
    }

    @Subscribe
    public void onGameObjectSpawned(GameObjectSpawned event) {
        GameObject object = event.getGameObject();
        for (CompostBinObject compostBin : CompostBinObject.values()) {
            if (compostBin.getObjectId() == object.getId()) {
                visibleCompostBin = new CompostBin(object, compostBin);
            }
        }
    }

    @Subscribe
    public void onGameObjectDespawned(GameObjectDespawned event) {
        GameObject object = event.getGameObject();
        if (visibleCompostBin != null && object.getId() == visibleCompostBin.getGameObject().getId()) {
            visibleCompostBin = null;
        }
    }

    @Subscribe
    public void onGameStateChanged(GameStateChanged event)
    {
        if (event.getGameState() == GameState.LOADING)
        {
            visibleCompostBin = null;
        }
    }

    @Provides
    CompostHelperConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(CompostHelperConfig.class);
    }
}
