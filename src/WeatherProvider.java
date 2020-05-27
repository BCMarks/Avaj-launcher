package src;

public class WeatherProvider {
    private static WeatherProvider weatherProvider = new WeatherProvider();
    private static String[] weather = {"RAIN", "FOG", "SUN", "SNOW"};

    private WeatherProvider() {}

    public static WeatherProvider getProvider() {
        return weatherProvider;
    }

    public String getCurrentWeather(Coordinates coordinates) {
        return weather[Math.floorMod((coordinates.getLatitude() + coordinates.getLongitude()) / ((coordinates.getHeight() / 10) + 1), 4)];
    }
}