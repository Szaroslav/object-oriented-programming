package agh.ics.oop.project2.game.world;

import agh.ics.oop.project2.utils.Vector2d;

import java.util.HashMap;
import java.util.Map;

public class City {
    public final int WIDTH;
    public final int HEIGHT;
    private final Map<Vector2d, Integer> slowsMap = new HashMap<>();

    public City(int width, int height) {
        WIDTH = Math.max(16, width);
        HEIGHT = Math.max(16, height);
    }

    public Map<Vector2d, Integer> getSlowsMap() {
        return slowsMap;
    }
}
