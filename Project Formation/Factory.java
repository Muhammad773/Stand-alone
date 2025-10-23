import java.util.Random;

class Factory extends Entity {
    private static final Random rand = new Random();
    
    public Factory(String id, double batteryCapacity, Transformer transformer) {
        super(id, batteryCapacity, new HydroEnergy(), transformer);
    }

    @Override
    public double getConsumptionAmount() { 
        return 6 * (1.0 + rand.nextDouble() * 1.5); // max = 14.9 min = 6
    }
}
