package agh.ics.oop.project1.plant;

import agh.ics.oop.project1.AbstractOrganism;
import agh.ics.oop.project1.utils.Vector2d;

public class Plant extends AbstractOrganism {
    public Plant(Vector2d position) {
        this.position = position;
        this.guiImageName = "grass.png";
    }
}
