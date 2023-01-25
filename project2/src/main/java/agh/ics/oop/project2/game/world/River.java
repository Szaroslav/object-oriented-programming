package agh.ics.oop.project2.game.world;

import agh.ics.oop.project2.utils.Random;
import agh.ics.oop.project2.utils.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class River {
    public final int MIN_X;
    public final int MAX_X;
    private final City city;
    private Vector2d[] coordinates;

    public River(City city) {
        MIN_X = 4;
        MAX_X = city.WIDTH - (4 + 1);
        this.city = city;
        coordinates = new Vector2d[0];

        final Vector2d startPos = new Vector2d(Random.range(MIN_X, MAX_X + 1), city.HEIGHT - 1);
        final Vector2d turnPos =  new Vector2d(
            Random.rangeWithoutRep(MIN_X, MAX_X + 1, new int[] {startPos.x}),
            Random.range(1, city.HEIGHT - 1)
        );

        generateRiver(startPos, turnPos);
        generateBridges(startPos, turnPos);
    }

    public Vector2d[] getCoordinates() {
        return coordinates;
    }

    private void generateRiver(Vector2d startPos, Vector2d turnPos) {
        List<Vector2d> coords = new ArrayList<>();

        for (int y = 0; y <= turnPos.y; y++) addCoordsToList(new Vector2d(startPos.x, y), coords);
        if (turnPos.x > startPos.x) {
            for (int x = startPos.x + 1; x < turnPos.x; x++) addCoordsToList(new Vector2d(x, turnPos.y), coords);
        }
        else {
            for (int x = startPos.x - 1; x > turnPos.x; x--) addCoordsToList(new Vector2d(x, turnPos.y), coords);
        }
        for (int y = turnPos.y; y < city.HEIGHT; y++) addCoordsToList(new Vector2d(turnPos.x, y), coords);

        coordinates = coords.toArray(coordinates);
    }

    private void generateBridges(Vector2d startPos, Vector2d turnPos) {
        Vector2d[] excludedRiverPoints = new Vector2d[2 + city.BRIDGES_NUMBER];
        excludedRiverPoints[0] = new Vector2d(startPos.x, turnPos.y);
        excludedRiverPoints[1] = turnPos;

        for (int i = 0; i < city.BRIDGES_NUMBER; i++) {
            excludedRiverPoints[i + 2] = Random.randomPoint(coordinates, excludedRiverPoints);
            city.addObstacle(new Obstacle(excludedRiverPoints[i + 2], 0));
        }
    }

    private void addCoordsToList(Vector2d point, List<Vector2d> list) {
        list.add(point);
        city.addObstacle(new Obstacle(list.get(list.size() - 1), Integer.MAX_VALUE));
    }
}
