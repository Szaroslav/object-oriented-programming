package agh.ics.oop.project2.gui;

import agh.ics.oop.project2.game.world.elements.AbstractWorldElement;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class MoveableWorldElementGUI extends WorldElementGUI {
    private final EventHandler<MouseEvent> handler = event -> {
        System.out.println("Am I moving?");
    };

    public MoveableWorldElementGUI(AbstractWorldElement element) {
        super(element);
        imageView.addEventFilter(MouseEvent.MOUSE_CLICKED, handler);
    }
}
