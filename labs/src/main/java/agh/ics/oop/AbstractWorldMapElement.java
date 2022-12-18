package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWorldMapElement implements IMapElement {
    protected Vector2d position;
    protected List<IPositionChangeObserver> positionObservers = new ArrayList<>();

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public boolean addPositionObserver(IPositionChangeObserver observer) {
        return positionObservers.add(observer);
    }

    public boolean removePositionObserver(IPositionChangeObserver observer) {
        return positionObservers.remove(observer);
    }

    protected void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        for (IPositionChangeObserver observer : positionObservers)
            observer.positionChanged(oldPosition, newPosition);
    }
}
