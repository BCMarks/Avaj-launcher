package src;

public abstract class Aircraft{
    protected long id;
    protected String name;
    protected Coordinates coordinates;
    private static long idCounter = 0;

    protected Aircraft(String name, Coordinates coordinates) {
        this.id = idCounter;
        this.name = name;
        this.coordinates = coordinates;
        idCounter = this.nextId();
    }

    private long nextId() {
        return idCounter + 1;
    }
}