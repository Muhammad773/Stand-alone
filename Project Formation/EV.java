import java.util.Random;

class EV extends Entity {
    private static final Random rand = new Random();

    public EV(String id, double batteryCapacity, Transformer transformer) {
        super(id, batteryCapacity, null, transformer);
    }

    @Override
    public double getConsumptionAmount() { 
        return 6 * (0.2 + rand.nextDouble() * 0.4); // from 1.2 to 3.6 units.
    }

    @Override
    public double produceEnergy(double weatherFactor) {
        return 0; // EVs do not produce energy
    }
}