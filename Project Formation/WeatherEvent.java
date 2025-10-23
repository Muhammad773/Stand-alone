import java.util.Random;

class WeatherEvent extends Event {
    private final SmartGridManager manager;
    private final WeatherStation ws;
    private final TimeManager timeManager;
    private static final Random rand = new Random();

    public WeatherEvent(int hour, SmartGridManager manager, WeatherStation ws, TimeManager timeManager) {
        super(hour, 0);
        this.manager = manager; 
        this.ws = ws; 
        this.timeManager = timeManager;
    }
    
    @Override
    public void process() {
        double newWeather = 0.5 + rand.nextDouble() * 1.2; //  0.5 and 1.7 
        ws.setWeatherFactor(newWeather);
        manager.setWeatherFactor(newWeather);
        System.out.println("=============================================");
        System.out.printf("%s Weather updated. New factor: %.2f\n", timeManager.getFormattedTime(), newWeather);
        System.out.println("=============================================");
    }
}