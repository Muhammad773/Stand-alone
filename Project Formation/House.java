import java.util.Random;

class House extends Entity {
    private static final Random rand = new Random();

    public House(String id, double batteryCapacity, Transformer transformer) {
        super(id, batteryCapacity, new SolarEnergyPanel(), transformer);
    }

    @Override
    public double getConsumptionAmount() { 
        return 6 * (0.5 + rand.nextDouble() * 1.0); // max = 8.994, min = 3
    }
}


