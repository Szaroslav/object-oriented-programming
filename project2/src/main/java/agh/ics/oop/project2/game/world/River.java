package agh.ics.oop.project2.game.world;

import agh.ics.oop.project2.utils.Random;
import agh.ics.oop.project2.utils.Vector2d;

public class River {
    public final int MIN_X;
    public final int MAX_X;
    private final City city;

    public River(City city) {
        MIN_X = 4;
        MAX_X = city.WIDTH - (4 + 1);
        this.city = city;

        final Vector2d startPos = new Vector2d(Random.range(MIN_X, MAX_X + 1), city.HEIGHT - 1);
        final Vector2d turnPos =  new Vector2d(
            Random.rangeWithoutRep(MIN_X, MAX_X + 1, new int[] {startPos.x}),
            Random.range(1, city.HEIGHT - 1)
        );

        generateRiver(startPos, turnPos);
    }

    private void generateRiver(Vector2d startPos, Vector2d turnPos) {
        for (int y = 0; y <= turnPos.y; y++) city.setObstacleMapEl(new Vector2d(startPos.x, y), Integer.MAX_VALUE);
        if (turnPos.x > startPos.x) {
            for (int x = startPos.x + 1; x < turnPos.x; x++) city.setObstacleMapEl(new Vector2d(x, turnPos.y), Integer.MAX_VALUE);
        }
        else {
            for (int x = startPos.x - 1; x > turnPos.x; x--) city.setObstacleMapEl(new Vector2d(x, turnPos.y), Integer.MAX_VALUE);
        }
        for (int y = turnPos.y; y <= city.HEIGHT; y++) city.setObstacleMapEl(new Vector2d(startPos.x, y), Integer.MAX_VALUE);
    }
}
