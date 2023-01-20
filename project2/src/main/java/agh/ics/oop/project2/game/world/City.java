package agh.ics.oop.project2.game.world;

import agh.ics.oop.project2.utils.Vector2d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class City {
    public final int WIDTH;
    public final int HEIGHT;
    public final int BRIDGES_NUMBER = 4;
    private final Vector2d heroesHeadquartersCoords; // Object
    private final Vector2d majorApartmentsCoords; // Object
    private final River river;
    private final Map<Vector2d, Integer> obstacleMap = new HashMap<>(); // Object
    private final List<Vector2d> obstacleList = new ArrayList<>(); // Object

    public City(int width, int height) {
        WIDTH = Math.max(16, width);
        HEIGHT = Math.max(16, height);
        river = new River(this);
    }

    public Map<Vector2d, Integer> getObstacleMap() {
        return obstacleMap;
    }

    public void setObstacleMapEl(Vector2d position, int slow) {
        if (obstacleMap.containsKey(position))
            obstacleMap.replace(position, slow);
        else
            obstacleMap.put(position, slow);
    }

    public List<Vector2d> getObstacleList() {
        return obstacleList;
    }

    public void addToObstacleList(Vector2d position) {
        obstacleList.add(position);
    }
}
