package agh.ics.oop.project1.gui;

import javafx.scene.Node;

public record ActionNode <T extends Node> (T node, String name) {}