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
    private final AbstractWorldElement heroesHeadquarters; // Object
    private final AbstractWorldElement majorApartments; // Object
    private final River river;
    private final Map<Vector2d, AbstractWorldElement> cityElementsMap = new HashMap<>();
    private final Map<Vector2d, Obstacle> obstaclesMap = new HashMap<>();
    private final List<Obstacle> obstaclesList = new ArrayList<>();

    public City(int width, int height) {
        WIDTH = Math.max(16, width);
        HEIGHT = Math.max(16, height);
        river = new River(this);

        heroesHeadquarters = new Building(getNewCityElementPosition());
        addCityElement(heroesHeadquarters);
        majorApartments = new Building(getNewCityElementPosition());
        addCityElement(majorApartments);

        initObstacles(OBSTACLES_NUMBER / 2, 2);
        initObstacles(OBSTACLES_NUMBER / 2, Integer.MAX_VALUE);
    }

    public Map<Vector2d, Obstacle> getObstacleMap() {
        return obstaclesMap;
    }

    public void setObstaclesMapEl(Obstacle obstacle) {
        if (obstaclesMap.containsKey(obstacle.position))
            obstaclesMap.replace(obstacle.position, obstacle);
        else
            obstaclesMap.put(obstacle.position, obstacle);
    }

    public List<Obstacle> getObstaclesList() {
        return obstaclesList;
    }

    public void addToObstaclesList(Obstacle obstacle) {
        obstaclesList.add(obstacle);
    }

    public void addObstacle(Obstacle obstacle) {
        setObstaclesMapEl(obstacle);
        addToObstaclesList(obstacle);
        addCityElement(obstacle);
    }

    public void addCityElement(AbstractWorldElement element) {
        cityElementsMap.put(element.getPosition(), element);
    }

    private void initObstacles(int n, int slow) {
        for (int i = 0; i < n; i++) {
            addObstacle(new Obstacle(getNewCityElementPosition(), slow));
        }
    }

    private Vector2d getNewCityElementPosition() {
        Vector2d pos;
        do {
            pos = new Vector2d(Random.range(0, WIDTH), Random.range(0, HEIGHT));
        } while (cityElementsMap.containsKey(pos));

        return pos;
    }
}
