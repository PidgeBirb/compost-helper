package com.composthelper.compostbin;

import com.composthelper.CompostHelperConfig;
import com.composthelper.CompostHelperPlugin;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.components.TextComponent;

import javax.inject.Inject;
import java.awt.*;

public class CompostBinOverlay extends Overlay {

    private static final int MAX_DISTANCE = 3500;
    private static final int TEXT_HEIGHT = 11;

    private final Client client;
    private final CompostHelperPlugin plugin;
    private final CompostHelperConfig config;
    private final TextComponent typeTextComponent = new TextComponent();
    private final TextComponent countTextComponent = new TextComponent();

    @Inject
    private CompostBinOverlay(Client client, CompostHelperPlugin plugin, CompostHelperConfig config) {
        this.client = client;
        this.plugin = plugin;
        this.config = config;

        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.LOW);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        CompostBin compostBin = plugin.getVisibleCompostBin();

        if (compostBin == null) {
            return null;
        }

        GameObject gameObject = compostBin.getGameObject();
        LocalPoint objectMinLocation = LocalPoint.fromScene(
                gameObject.getSceneMinLocation().getX(),
                gameObject.getSceneMinLocation().getY());
        LocalPoint objectMaxLocation = LocalPoint.fromScene(
                gameObject.getSceneMaxLocation().getX(),
                gameObject.getSceneMaxLocation().getY());
        LocalPoint localLocation = client.getLocalPlayer().getLocalLocation();

        if (localLocation.distanceTo(objectMinLocation) <= MAX_DISTANCE) {
            CompostBinObject compostBinObject = compostBin.getCompostBinObject();
            int varbitValue = client.getVarbitValue(compostBinObject.getVarbit());
            CompostBinState state = compostBinObject.getState(varbitValue);

            if (state == null) {
                return null;
            }

            int line = 0;
            if (config.showCompostType()) {
                renderTypeTextComponent(graphics, state, line++, objectMinLocation, objectMaxLocation);
            }

            int capacity = compostBin.getCompostBinObject() == CompostBinObject.FARMING_GUILD ? 30 : 15;
            renderCountTextComponent(graphics, state, capacity, line, objectMinLocation, objectMaxLocation);
        }

        return null;
    }

    private void renderTypeTextComponent(Graphics2D graphics, CompostBinState state, int lineNumber, LocalPoint minLocation, LocalPoint maxLocation) {
        String typeText;
        if (state.getType() != null) {
            typeText = state.getType().toString();
        } else if (state.getAction() == CompostBinAction.EMPTY) {
            typeText = "Empty";
        } else {
            typeText = "Error";
        }
        Color textColor = state.getAction().getTextColor();

        java.awt.Point typeTextLocation = calculateTextLocation(graphics, typeText, lineNumber, minLocation, maxLocation);

        typeTextComponent.setText(typeText);
        typeTextComponent.setColor(textColor);
        typeTextComponent.setPosition(typeTextLocation);
        typeTextComponent.render(graphics);
    }

    private void renderCountTextComponent(Graphics2D graphics, CompostBinState state, int capacity, int lineNumber, LocalPoint minLocation, LocalPoint maxLocation) {
        String countText = Integer.toString(state.getContentCount());
        if (config.showMaxSize()) {
            countText = String.format("%s / %s", countText, capacity);
        }
        Color textColor = state.getAction().getTextColor();

        java.awt.Point countTextLocation = calculateTextLocation(graphics, countText, lineNumber, minLocation, maxLocation);

        countTextComponent.setText(countText);
        countTextComponent.setColor(textColor);
        countTextComponent.setPosition(countTextLocation);
        countTextComponent.render(graphics);
    }

    private java.awt.Point calculateTextLocation(Graphics2D graphics, String text, int lineNumber, LocalPoint... points) {
        int x = 0;
        int y = 0;

        for (LocalPoint point : points) {
            Point textLocation = Perspective.getCanvasTextLocation(client,
                    graphics,
                    point,
                    text,
                    200);
            x += textLocation.getX();
            y += textLocation.getY();
        }

        return new java.awt.Point(x / points.length, y / points.length + lineNumber * TEXT_HEIGHT);
    }
}
