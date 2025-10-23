import java.util.List;
import java.util.ArrayList;

public class WeatherStation { // subject or Observable
    private final List<WeatherObserver> observers = new ArrayList<>();
    private double weatherFactor = 1.0;

    public void registerObserver(WeatherObserver o) { 
        observers.add(o); 
    }

    public void notifyObservers() { 
        for (WeatherObserver o : observers) 
            o.updateWeather(weatherFactor); 
    }

    public void setWeatherFactor(double wf) { 
        this.weatherFactor = wf; 
        notifyObservers(); 
    }
}
