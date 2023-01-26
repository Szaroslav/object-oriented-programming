package agh.ics.oop.project2.game.world.elements;

import agh.ics.oop.project2.game.world.City;
import agh.ics.oop.project2.utils.Random;
import agh.ics.oop.project2.utils.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class River {
    public final int MIN_X;
    public final int MAX_X;
    private final City city;
    private final List<Vector2d> waterPositionsList = new ArrayList<>();
    private final List<Obstacle> waterList = new ArrayList<>();
    private final List<Vector2d> bridgePositionsList = new ArrayList<>();

    public River(City city) {
        MIN_X = 4;
        MAX_X = city.WIDTH - (4 + 1);
        this.city = city;

        final Vector2d startPos = new Vector2d(Random.range(MIN_X, MAX_X + 1), city.HEIGHT - 1);
        final Vector2d turnPos =  new Vector2d(
            Random.rangeWithoutRep(MIN_X, MAX_X + 1, new int[] {startPos.x}),
            Random.range(1, city.HEIGHT - 1)
        );

        pregenerateRiver(startPos, turnPos);
        generateBridges(startPos, turnPos);
        generateRiver();
    }

    public List<Vector2d> getWaterPositionsList() {
        return waterPositionsList;
    }

    public List<Vector2d> getBridgePositionsList() {
        return bridgePositionsList;
    }

    private void generateRiver() {
        for (Vector2d pos : bridgePositionsList)
            waterPositionsList.remove(pos);
        for (Vector2d pos : waterPositionsList) {
            Obstacle riverTile = new Obstacle(pos, WorldElements.RIVER, Integer.MAX_VALUE);
            waterList.add(riverTile);
            city.addObstacle(riverTile);
        }
    }

    private void pregenerateRiver(Vector2d startPos, Vector2d turnPos) {
        for (int y = 0; y <= turnPos.y; y++) waterPositionsList.add(new Vector2d(startPos.x, y));
        if (turnPos.x > startPos.x) {
            for (int x = startPos.x + 1; x < turnPos.x; x++) waterPositionsList.add(new Vector2d(x, turnPos.y));
        }
        else {
            for (int x = startPos.x - 1; x > turnPos.x; x--) waterPositionsList.add(new Vector2d(x, turnPos.y));
        }
        for (int y = turnPos.y; y < city.HEIGHT; y++) waterPositionsList.add(new Vector2d(turnPos.x, y));
    }

    private void generateBridges(Vector2d startPos, Vector2d turnPos) {
        List<Vector2d> excludedRiverPoints = new ArrayList<>();
        excludedRiverPoints.add(new Vector2d(startPos.x, turnPos.y));
        excludedRiverPoints.add(turnPos);

        for (int i = 0; i < city.BRIDGES_NUMBER; i++) {
            Vector2d bridgePosition = Random.randomPoint(waterPositionsList, excludedRiverPoints);
            excludedRiverPoints.add(bridgePosition);
            bridgePositionsList.add(bridgePosition);
        }
    }
}
