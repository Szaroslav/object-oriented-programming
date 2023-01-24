package agh.ics.oop.project2.game.world;

import agh.ics.oop.project2.utils.Random;
import agh.ics.oop.project2.utils.Vector2d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class City {
    public final int WIDTH;
    public final int HEIGHT;
    public final int OBSTACLES_NUMBER = 16;
    public final int BRIDGES_NUMBER = 4;
    private final AbstractWorldElement heroesHeadquartersCoords; // Object
    private final AbstractWorldElement majorApartmentsCoords; // Object
    private final River river;
    private final Map<Vector2d, Obstacle> obstacleMap = new HashMap<>(); // Object
    private final List<Obstacle> obstacleList = new ArrayList<>(); // Object

    public City(int width, int height) {
        WIDTH = Math.max(16, width);
        HEIGHT = Math.max(16, height);
        river = new River(this);

        initObstacles(OBSTACLES_NUMBER / 2, 2);
        initObstacles(OBSTACLES_NUMBER / 2, Integer.MAX_VALUE);
    }

    public Map<Vector2d, Obstacle> getObstacleMap() {
        return obstacleMap;
    }

    public void setObstacleMapEl(Obstacle obstacle) {
        if (obstacleMap.containsKey(obstacle.position))
            obstacleMap.replace(obstacle.position, obstacle);
        else
            obstacleMap.put(obstacle.position, obstacle);
    }

    public List<Obstacle> getObstacleList() {
        return obstacleList;
    }

    public void addToObstacleList(Obstacle obstacle) {
        obstacleList.add(obstacle);
    }

    public void addObstacle(Obstacle obstacle) {
        setObstacleMapEl(obstacle);
        addToObstacleList(obstacle);
    }

    private void initObstacles(int n, int slow) {
        for (int i = 0; i < n; i++) {
            Vector2d pos;
            do {
                pos = new Vector2d(Random.range(0, WIDTH), Random.range(0, HEIGHT));
            } while (obstacleMap.containsKey(pos));

            addObstacle(new Obstacle(pos, slow));
        }
    }
}
