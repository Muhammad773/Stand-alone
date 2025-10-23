import java.util.Random;

class HydroEnergy extends EnergySource { 
    private static final Random rand = new Random();

    public HydroEnergy() { 
        super("Hydro"); 
    }
    
    @Override 
    public double produceEnergy(double weatherFactor) {
        double base = 7.0 * weatherFactor;
        return base * (0.95 + 0.1 * rand.nextDouble());
    }
}