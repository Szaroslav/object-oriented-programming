package agh.ics.oop.project2.game.world;

import agh.ics.oop.project2.utils.Random;
import agh.ics.oop.project2.utils.Vector2d;

public class River {
    public River(City city) {
        Vector2d currentPos = new Vector2d(Random.range(4, city.WIDTH - 4), city.HEIGHT - 1);
        while (currentPos.y < 0) {
            
        }
    }
}
