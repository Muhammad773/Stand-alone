// --- Exception for battery overflow/underflow ---
class BatteryException extends Exception {
    public BatteryException(String message) { 
        super(message); 
    }
}

interface IEnergyProducer { 
    double produceEnergy(double weatherFactor) throws BatteryException; 
}

interface IEnergyConsumer { // interface inheritance 
    double consumeEnergy() throws BatteryException; 
}

interface ITradeable { 
    void tradeEnergy(SmartGridManager manager); 
}

interface WeatherObserver { 
    void updateWeather(double weatherFactor); 
}
