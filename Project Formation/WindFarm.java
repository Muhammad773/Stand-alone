import java.util.Random;

class WindFarm extends Entity {
    private static final Random rand = new Random();

    public WindFarm(String id, double batteryCapacity, Transformer transformer) {
        super(id, batteryCapacity, new WindTurbine(), transformer);
    }

    @Override
    public double getConsumptionAmount() { 
        return 0; // Wind farm does not consume energy
    }
}
