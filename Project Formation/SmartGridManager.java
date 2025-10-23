import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

class SmartGridManager {
    private final List<Entity> entities = new ArrayList<>();
    private final List<Transformer> transformers = new ArrayList<>();
    private double weatherFactor = 1.0;
    private double gridEnergy = 150.0;
    private double gridMoney = 1000.0;
    private static final double marketPrice = 0.5;
    private static final double MAX_BATTERY_CAPACITY = 200.0;

    public void addEntity(Entity entity) {
        if (entity != null) {
            if (entity.getBatteryCapacity() > MAX_BATTERY_CAPACITY) {
                throw new IllegalArgumentException("Battery capacity exceeds max allowed.");
            }
            entities.add(entity);
        }
    }

    public List<Entity> getEntities() { 
        return entities; 
    }

    public void addTransformer(Transformer transformer) { 
        if (transformer != null) 
            transformers.add(transformer);
        }


    public List<Transformer> getTransformers() { 
        return transformers; 
    }

    public double getWeatherFactor() { /////
        return weatherFactor; 
    }

    public void setWeatherFactor(double factor) { 
        this.weatherFactor = factor; 
    }

    public double getGridEnergy() { 
        return gridEnergy; 
    }

    public double getGridMoney() { 
        return gridMoney; 
    }

    public double getMarketPrice() { 
        return marketPrice; 
    }

    public void gridStoreEnergy(double amount) { 
        gridEnergy += amount; 
    }

    public boolean gridUseEnergy(double amount) {
        if (gridEnergy >= amount) {
            gridEnergy -= amount;
            return true;
        }
        return false;
    }

    public void gridPay(double money) { 
        gridMoney -= money; 
    }

    public void gridReceive(double money) { 
        gridMoney += money; 
    }

    public void reportFault(Transformer transformer) {
        System.out.printf("SmartGridManager notified: Transformer %s is offline.\n", transformer.getId());
    }

    public void reportRepair(Transformer transformer) {
        System.out.printf("SmartGridManager notified: Transformer %s is now operational.\n", transformer.getId());
    }
}
