import java.util.Random;

class WindTurbine extends EnergySource { 
    private static final Random rand = new Random();

    public WindTurbine() { 
        super("Wind");
    }
    
    @Override 
    public double produceEnergy(double weatherFactor) {
        double base = 5.0 * weatherFactor;
        return base * (0.95 + 0.1 * rand.nextDouble());
    }
}