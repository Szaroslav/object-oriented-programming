package agh.ics.oop.project1.gui;

import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

public record ActionNode <T extends Region> (T region, Pane pane, Tooltip tooltip, String name) {
    public ActionNode(T region, Pane pane, Tooltip tooltip, String name) {
        this.region = region;
        this.pane = pane;
        this.name = name;
        this.tooltip = tooltip;
    }

    public ActionNode(T region, Pane pane, String name) {
        this(region, pane, null, name);
    }
}