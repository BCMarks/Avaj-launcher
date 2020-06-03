package src;

public class JetPlane extends Aircraft implements Flyable{
    private WeatherTower weatherTower;

    JetPlane(String name, Coordinates coordinates) {
        super(name, coordinates);
        registerTower(new WeatherTower());
    }

    public void updateConditions() {
        if (this.weatherTower != null) {
            String weather = this.weatherTower.getWeather(this.coordinates);
            switch(weather){
                case "RAIN":
                    this.coordinates = new Coordinates(this.coordinates.getLongitude(), this.coordinates.getLatitude() + 5, this.coordinates.getHeight());
                    break;
                case "FOG":
                this.coordinates = new Coordinates(this.coordinates.getLongitude(), this.coordinates.getLatitude() + 1, this.coordinates.getHeight());
                    break;
                case "SUN":
                this.coordinates = new Coordinates(this.coordinates.getLongitude(), this.coordinates.getLatitude() + 10, this.coordinates.getHeight() + 2);
                    break;
                case "SNOW":
                this.coordinates = new Coordinates(this.coordinates.getLongitude(), this.coordinates.getLatitude(), this.coordinates.getHeight() - 7);
                    break;
                default:
            }
            Writer.newLine("JetPlane#"+this.name+"("+this.id+"): I hate the "+weather);
            if (this.coordinates.getHeight() <= 0) {
                Writer.newLine("JetPlane#"+this.name+"("+this.id+") has landed. Longitude: "+this.coordinates.getLongitude()+", Latitude: "+this.coordinates.getLatitude());
                this.weatherTower.unregister(this);
                this.weatherTower = null;
                Writer.newLine("JetPlane#"+this.name+"("+this.id+") unregistered from weather tower.");
            }
        }
    }

    public void registerTower(WeatherTower weatherTower) {
        this.weatherTower = weatherTower;
        this.weatherTower.register(this);
        Writer.newLine("JetPlane#"+this.name+"("+this.id+") registered to weather tower.");
    }
}