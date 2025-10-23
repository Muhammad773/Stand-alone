abstract class EnergySource {     
    private final String name; 

    public EnergySource(String name) { 
        this.name = name;
    }

    public String getName() { 
        return name; 
    }
    
    public abstract double produceEnergy(double weatherFactor) throws BatteryException;
}
