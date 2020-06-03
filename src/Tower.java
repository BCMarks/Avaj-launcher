package src;

import java.util.List;
import java.util.ArrayList;

public abstract class Tower {
    private List<Flyable> observers = new ArrayList<Flyable>();

    public void register(Flyable flyable) {
        this.observers.add(flyable);
    }

    public void unregister(Flyable flyable) {
        this.observers.remove(flyable);
    }

    protected void conditionsChanged() {
        for (Flyable flyable : observers) {
            flyable.updateConditions();
        }
    }
}