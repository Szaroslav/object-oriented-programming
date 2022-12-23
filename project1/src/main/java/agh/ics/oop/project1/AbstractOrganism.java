package agh.ics.oop.project1;

import agh.ics.oop.project1.utils.Vector2d;

public abstract class AbstractOrganism {
    protected Vector2d position;
    protected String guiImageName = null;

    public Vector2d getPosition() {
        return position;
    }

    public void setPosition(Vector2d newPosition) {
        position = newPosition;
    }

    public String getGuiImageName() {
        return guiImageName;
    }
}
