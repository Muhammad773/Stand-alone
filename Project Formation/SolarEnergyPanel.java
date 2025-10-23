import java.util.Random;

class SolarEnergyPanel extends EnergySource { 
    private static final Random rand = new Random(); 

    public SolarEnergyPanel() { 
        super("Solar");
    }
    
    @Override 
    public double produceEnergy(double weatherFactor) {
        double base = 8.0 * weatherFactor;
        return base * (0.95 + 0.1 * rand.nextDouble());
    }
}