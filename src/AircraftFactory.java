package src;

public abstract class AircraftFactory {
    public static Flyable newAircraft(String type, String name, int longitude, int latitude, int height) {
        Coordinates coordinates = new Coordinates(longitude, latitude, height);
        switch(type){
            case "Helicopter":
                return new Helicopter(name, coordinates);
            case "JetPlane":
                return new JetPlane(name, coordinates);
            case "Baloon":
                return new Baloon(name, coordinates);
            default:
                System.out.println("Invalid input: Type "+type+" does not exist.");
                return null;
        }

    }
}