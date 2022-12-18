package agh.ics.project1.world;

import agh.ics.project1.world.engine.WorldEngine;
import agh.ics.project1.world.maps.AbstractMap;
import agh.ics.project1.world.maps.Earth;

public class World {
    public static void main(String[] args) {
        AbstractMap map = new Earth();
        WorldEngine engine = new WorldEngine(map);
        engine.run();
    }
}
